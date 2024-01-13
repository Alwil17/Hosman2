import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { InsuranceDebt } from "src/app/models/secretariat/activities/insurance-debt.model";
import { InsuranceDebtService } from "src/app/services/secretariat/activities/insurance-debt.service";
import { InsuranceService } from "src/app/services/secretariat/patients/insurance.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { PdfModalComponent } from "src/app/shared/modals/pdf-modal/pdf-modal.component";

type InsuranceDebtSwitch = InsuranceDebt & { switchBackgroundColor: boolean };

@Component({
  selector: "app-insurances-debts-modal",
  templateUrl: "./insurances-debts-modal.component.html",
  styleUrls: ["./insurances-debts-modal.component.scss"],
})
export class InsurancesDebtsModalComponent implements OnInit {
  insuranceTypeFirstOption = { id: -1, text: "Tous les types d'assurance" };
  insuranceFirstOption = { id: -1, text: "Toutes les assurances" };

  insuranceTypes: SelectOption[] = [
    this.insuranceTypeFirstOption,
    {
      id: "locale",
      text: "Locale",
    },
    {
      id: "etrangere",
      text: "Etrangère",
    },
  ];
  insurances: SelectOption[] = [];

  insurancesDebtsList: InsuranceDebtSwitch[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.insurancesDebtsList.length;
  insurancesDebtsListCut: InsuranceDebtSwitch[] = [];

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  startDateControl = new FormControl(this.today);
  endDateControl = new FormControl();
  insuranceTypeControl = new FormControl(this.insuranceTypeFirstOption);
  insuranceControl = new FormControl(this.insuranceFirstOption);

  searchTerm = "";

  actsAmountTotal = 0;
  paidAmountTotal = 0;
  scAmountTotal = 0;

  constructor(
    public modal: NgbActiveModal,
    private insuranceService: InsuranceService,
    private insuranceDebtService: InsuranceDebtService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.refreshInvoicesList();

    this.startDateControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshInvoicesList();
      }
    });

    this.endDateControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshInvoicesList();
      }
    });

    this.insuranceTypeControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshInvoicesList();
      }
    });

    this.insuranceControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshInvoicesList();
      }
    });

    this.insuranceService.getAll().subscribe({
      next: (data) => {
        this.insurances = [
          this.insuranceFirstOption,
          ...data.map((insurance) => ({
            id: insurance.slug,
            text: insurance.nom,
          })),
        ];
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  refreshInvoicesList() {
    const minDate = new Date(this.startDateControl.value);
    const maxDate = this.endDateControl.value
      ? new Date(this.endDateControl.value)
      : undefined;
    const insuranceSlug =
      this.insuranceControl.value.id == -1
        ? null
        : this.insuranceControl.value.id;
    const insuranceType =
      this.insuranceTypeControl.value.id == -1
        ? null
        : this.insuranceTypeControl.value.id;

    this.insuranceDebtService
      .searchBy({
        minDate: minDate,
        maxDate: maxDate,
        insuranceType: insuranceType,
        insuranceSlug: insuranceSlug,
      })
      .subscribe({
        next: (data) => {
          this.actsAmountTotal = 0;
          this.paidAmountTotal = 0;
          this.scAmountTotal = 0;

          let switchBG = false;

          this.insurancesDebtsList = data.map((value, index, array) => {
            this.actsAmountTotal += value.facture.total;
            this.paidAmountTotal +=
              value.facture.a_payer - value.facture.creance.montant;
            this.scAmountTotal += value.montant_pec;

            // To determine when to switch background (when invoices are different)
            if (index === 0) {
              switchBG = false;
              return { ...value, switchBackgroundColor: false };
            } else {
              if (
                array[index - 1].facture.reference !==
                array[index].facture.reference
              ) {
                switchBG = !switchBG;
              }
              return {
                ...value,
                switchBackgroundColor: switchBG,
              };
            }
          });

          // console.log(this.insurancesDebtsList);

          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
          // });

          // this.insurancesDebtsList.forEach((value) => {
          //   this.actsAmountTotal += value.facture.total;
          //   this.paidAmountTotal +=
          //     value.facture.a_payer - value.facture.creance.montant;
          //   this.scAmountTotal += value.montant_pec;
          // });

          this.refreshInvoices();
        },
        error: (error) => {
          console.error(error);
          this.toastService.show({
            messages: [
              "Une erreur s'est produite lors du rafraîchissment de la liste.",
            ],
            delay: 10000,
            type: ToastType.Error,
          });
        },
      });
  }

  refreshInvoices() {
    this.collectionSize = this.insurancesDebtsList.length;
    this.insurancesDebtsListCut = this.insurancesDebtsList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  printInsurancesDebt() {
    const minDate = new Date(this.startDateControl.value);
    const maxDate = this.endDateControl.value
      ? new Date(this.endDateControl.value)
      : undefined;
    const insuranceSlug =
      this.insuranceControl.value.id == -1
        ? null
        : this.insuranceControl.value.id;
    const insuranceType =
      this.insuranceTypeControl.value.id == -1
        ? null
        : this.insuranceTypeControl.value.id;

    this.insuranceDebtService
      .loadPdf({
        minDate: minDate,
        maxDate: maxDate,
        insuranceType: insuranceType,
        insuranceSlug: insuranceSlug,
      })
      .subscribe({
        next: (data) => {
          this.toastService.show({
            messages: ["Génération de la prise en charge."],
            type: ToastType.Success,
          });

          const pdfModalRef = this.modalService.open(PdfModalComponent, {
            size: "xl",
            centered: true,
            scrollable: true,
            backdrop: "static",
          });

          pdfModalRef.componentInstance.title = "Créances des assurances";
          pdfModalRef.componentInstance.pdfSrc = data;
        },
        error: (e) => {
          console.error(e);

          this.toastService.show({
            messages: ["Echec de la génération des Prises En Charge."],
            delay: 10000,
            type: ToastType.Error,
          });
        },
      });
  }
}
