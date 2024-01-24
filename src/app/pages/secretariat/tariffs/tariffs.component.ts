import { Component, OnInit, QueryList, ViewChildren } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ActGroup } from "src/app/models/secretariat/shared/act-group.model";
import { Tariff } from "src/app/models/secretariat/shared/tariff.model";
import { ActGroupService } from "src/app/services/secretariat/shared/act-group.service";
import { TariffService } from "src/app/services/secretariat/shared/tariff.service";
import { TariffQuantityModalComponent } from "./tariff-quantity-modal/tariff-quantity-modal.component";
import { firstValueFrom } from "rxjs";
import { parseIntOrZero } from "src/app/helpers/parsers";
import { HAS_INSURANCES } from "src/app/data/secretariat/has-insurance.data";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import {
  ActsProForma,
  TariffProFormaRequest,
} from "src/app/models/secretariat/shared/requests/tariff-pro-forma-request.model";
import { PdfModalComponent } from "src/app/shared/modals/pdf-modal/pdf-modal.component";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { WarningMessages } from "src/app/helpers/messages";
import { InputComponent } from "src/app/shared/form-inputs/input/input.component";
import { SelectComponent } from "src/app/shared/form-inputs/select/select.component";

@Component({
  selector: "app-tariffs",
  templateUrl: "./tariffs.component.html",
  styleUrls: ["./tariffs.component.scss"],
})
export class TariffsComponent implements OnInit {
  @ViewChildren(InputComponent)
  inputFields!: QueryList<InputComponent>;

  @ViewChildren(SelectComponent)
  selectFields!: QueryList<SelectComponent>;

  // bread crumb items
  breadCrumbItems!: Array<{}>;

  searchTerm = "";

  actGroups!: ActGroup[];
  selectedGroupTariffs!: Tariff[];

  table1: any[] = [];

  table2: any[] = [];

  table1Page = 1;
  table1PageSize = 10;
  table1CollectionSize = this.table1.length;
  activities: any[] = [];

  table2Page = 1;
  table2PageSize = 10;
  table2CollectionSize = this.table2.length;
  activitiesSelect: any[] = [];

  isProFormaInitiated = false;

  patientFullnameControl = new FormControl(null, Validators.required);
  hasInsuranceControl = new FormControl(null, Validators.required);

  profFormaForm!: FormGroup;
  isProFormaFormSubmitted = false;

  hasInsurances = HAS_INSURANCES.map((hasInsurance) => ({
    id: hasInsurance.code,
    text: hasInsurance.text,
  }));

  table2Totals = {
    table2NoLocalTotal: 0,
    table2YesLocalTotal: 0,
    table2NoForeignerTotal: 0,
    table2YesForeignerTotal: 0,
  };

  constructor(
    private actGroupService: ActGroupService,
    private tariffService: TariffService,
    private modalService: NgbModal,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Renseignements" },
      { label: "Tarifs", active: true },
    ];

    this.actGroupService.getAll().subscribe({
      next: (data) => {
        this.actGroups = data;

        // this.selectedPrestationIndex = data[0].code;
        this.tariffService.getByGroupCode(data[0].code).subscribe({
          next: (data) => {
            this.selectedGroupTariffs = data;

            this.refreshTable1();
          },
          error: (e) => {
            console.log(e);
          },
        });
      },
      error: (e) => {
        console.log(e);
      },
    });

    this.profFormaForm = new FormGroup({
      patientFullnameControl: this.patientFullnameControl,
      hasInsuranceControl: this.hasInsuranceControl,
    });
  }

  updateTable(actGroup: ActGroup) {
    const code = actGroup.code;

    // this.selectedPrestationIndex = code;

    this.tariffService.getByGroupCode(code).subscribe({
      next: (data) => {
        this.selectedGroupTariffs = data;

        this.refreshTable1();
      },
      error: (e) => {
        console.log(e);
      },
    });
  }

  refreshTable1() {
    this.table1 = this.selectedGroupTariffs.map((item) => {
      // let patientPrice = 0;
      // if (this.patientService.getActivePatientType() == 0) {
      //   patientPrice = item.tarif_non_assure;
      // } else if (this.patientService.getActivePatientType() == 1) {
      //   patientPrice = item.tarif_etr_non_assure;
      // } else if (this.patientService.getActivePatientType() == 2) {
      //   patientPrice = item.tarif_assur_locale;
      // } else {
      //   patientPrice = item.tarif_assur_hors_zone;
      // }

      return {
        id: item.code,
        designation: item.libelle,
        no_local: item.tarif_non_assure,
        no_foreigner: item.tarif_etr_non_assure,
        yes_local: item.tarif_assur_locale,
        yes_foreigner: item.tarif_assur_hors_zone,
        description: item.description,
      };
    });

    this.table1Page = 1;
    this.table1CollectionSize = this.table1.length;

    this.refreshActivities();
  }

  refreshActivities() {
    this.activities = this.table1;
    // .slice(
    //   (this.table1Page - 1) * this.table1PageSize,
    //   (this.table1Page - 1) * this.table1PageSize + this.table1PageSize
    // );
  }

  refreshActivitiesSelect() {
    this.table2Totals = {
      table2NoLocalTotal: 0,
      table2YesLocalTotal: 0,
      table2NoForeignerTotal: 0,
      table2YesForeignerTotal: 0,
    };

    // Calculate second table totals
    this.table2.forEach((value) => {
      this.table2Totals.table2NoLocalTotal += value.no_local * value.quantity;
      this.table2Totals.table2YesLocalTotal +=
        value.no_foreigner * value.quantity;
      this.table2Totals.table2NoForeignerTotal +=
        value.yes_local * value.quantity;
      this.table2Totals.table2YesForeignerTotal +=
        value.yes_foreigner * value.quantity;
    });

    this.activitiesSelect = this.table2;
    // .slice(
    //   (this.table2Page - 1) * this.table2PageSize,
    //   (this.table2Page - 1) * this.table2PageSize + this.table2PageSize
    // );
  }

  searchActs() {
    const searchedActs = this.searchTerm
      ? this.table1.filter((act) => {
          return act.designation
            .toLowerCase()
            .includes(this.searchTerm.toLowerCase());
        })
      : this.table1;

    this.table1CollectionSize = searchedActs.length;

    this.activities = searchedActs;
    // .slice(
    //   (this.table1Page - 1) * this.table1PageSize,
    //   (this.table1Page - 1) * this.table1PageSize + this.table1PageSize
    // );
  }

  async add(item: any) {
    // Does not add if the user has not initiated the pro forma
    if (!this.isProFormaInitiated) {
      return;
    }

    console.log(item);

    // OPEN TARIFF QUANTITY MODAL
    const tariffQuantityModal = this.modalService.open(
      TariffQuantityModalComponent,
      {
        size: "sm",
        centered: true,
        keyboard: false,
        // scrollable: true,
      }
    );

    const tariffQuantity: number = await firstValueFrom(
      tariffQuantityModal.componentInstance.quantityData.asObservable()
    );

    // CLOSE TARIFF QUANTITY MODAL
    tariffQuantityModal.close();

    // CHECK IF USER CONFIRMED OR NOT
    if (!tariffQuantity) {
      return;
    }

    const item2: any = {
      id: item.id,
      designation: item.designation,
      quantity: tariffQuantity,
      no_local: item.no_local,
      no_foreigner: item.no_foreigner,
      yes_local: item.yes_local,
      yes_foreigner: item.yes_foreigner,
      description: item.description,
    };
    console.log(item2);

    var index = this.table2.findIndex((value) => value.id == item.id);

    if (index === -1) {
      this.table2 = [...this.table2, item2];
    }

    this.table2CollectionSize = this.table2.length;
    this.refreshActivitiesSelect();
  }

  remove(item: any) {
    console.log(item);

    this.table2 = [
      ...this.table2.filter((value) => {
        return value.id !== item.id;
      }),
    ];

    this.table2CollectionSize = this.table2.length;
    this.refreshActivitiesSelect();
  }

  initiateProForma() {
    this.isProFormaInitiated = true;
  }

  cancelProForma() {
    this.isProFormaInitiated = false;
  }

  getInvalidFields() {
    const invalidInputs: string[] = [];
    this.inputFields.forEach((input) => {
      if (input.control.invalid) {
        invalidInputs.push("- " + input.label);
      }
    });

    const invalidSelects: string[] = [];
    this.selectFields.forEach((select) => {
      if (select.control.invalid) {
        invalidSelects.push("- " + select.label);
      }
    });

    let notificationMessages: string[] = [];
    if (invalidInputs.length !== 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_INPUT_FIELDS,
        ...invalidInputs
      );
    }

    if (notificationMessages.length !== 0) {
      notificationMessages.push("");
    }

    if (invalidSelects.length !== 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_SELECT_FIELDS,
        ...invalidSelects
      );
    }

    return notificationMessages;
  }

  generateProForma() {
    this.isProFormaFormSubmitted = true;

    if (!this.profFormaForm.valid) {
      const notificationMessages = this.getInvalidFields();

      this.toastService.show({
        messages: notificationMessages,
        type: ToastType.Warning,
      });

      return;
    }

    if (this.table2.length === 0) {
      this.toastService.show({
        messages: [
          "Veuillez choisir au moins un élément dans le tableau de gauche.",
        ],
        type: ToastType.Warning,
      });

      return;
    }

    const tariffProForma = new TariffProFormaRequest({
      patient: this.patientFullnameControl.value,
      actes: this.table2.map((value) => {
        const actes: ActsProForma = {
          code: value.id,
          libelle: value.designation,
          tarif_non_assure: value.no_local,
          tarif_etr_non_assure: value.no_foreigner,
          tarif_assur_locale: value.yes_local,
          tarif_assur_hors_zone: value.yes_foreigner,
          qte: value.quantity,
        };
        return actes;
      }),
      is_assure: this.hasInsuranceControl.value.id,
    });

    console.log(JSON.stringify(tariffProForma, null, 2));

    this.tariffService.loadProFormaPdf(tariffProForma).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Génération du pro forma."],
          type: ToastType.Success,
        });

        const pdfModalRef = this.modalService.open(PdfModalComponent, {
          size: "xl",
          centered: true,
          // scrollable: true,
          backdrop: "static",
        });

        pdfModalRef.componentInstance.title = "Pro Forma";
        pdfModalRef.componentInstance.pdfSrc = data;
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Echec de la génération du pro forma."],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }
}
