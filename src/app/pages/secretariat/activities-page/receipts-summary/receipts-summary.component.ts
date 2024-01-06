import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Invoice } from "src/app/models/secretariat/patients/invoice.model";
import { InvoiceService } from "src/app/services/secretariat/patients/invoice.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ActivitiesDetailComponent } from "../activities-detail/activities-detail.component";
import { CheckoutService } from "src/app/services/secretariat/activities/checkout.service";
import { PdfModalComponent } from "src/app/shared/modals/pdf-modal/pdf-modal.component";
import { ReportSearchCriteriaModalComponent } from "./report-search-criteria-modal/report-search-criteria-modal.component";
import { SelectOption } from "src/app/models/extras/select.model";
import { InsurancesDebtsModalComponent } from "./insurances-debts-modal/insurances-debts-modal.component";

@Component({
  selector: "app-receipts-summary",
  templateUrl: "./receipts-summary.component.html",
  styleUrls: ["./receipts-summary.component.scss"],
})
export class ReceiptsSummaryComponent implements OnInit {
  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  receiptsDateControl = new FormControl(this.today);
  paymentModeCriterionControl = new FormControl({
    id: "all",
    text: "Tout",
  });

  searchTerm = "";

  paymentModeCriteria: SelectOption[] = [
    {
      id: "all",
      text: "Tout",
    },
    {
      id: "cash",
      text: "Espèces",
    },
    {
      id: "cheque",
      text: "Chèque",
    },
    {
      id: "card",
      text: "Visa",
    },
  ];

  invoicesList: Invoice[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.invoicesList.length;
  invoicesListCut: Invoice[] = [];

  cashTotal = 0;
  chequeTotal = 0;
  cardTotal = 0;
  scTotal = 0;
  generalTotal = 0;

  constructor(
    private invoiceService: InvoiceService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.refreshInvoicesList();

    this.receiptsDateControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshInvoicesList();
      }
    });
  }

  refreshInvoicesList() {
    this.invoiceService
      .searchBy({
        minDate: new Date(this.receiptsDateControl.value),
        patientName: this.searchTerm,
      })
      .subscribe({
        next: (data) => {
          this.invoicesList = data;

          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
          // });

          this.cashTotal = 0;
          this.chequeTotal = 0;
          this.cardTotal = 0;
          this.scTotal = 0;
          this.generalTotal = 0;

          this.invoicesList.forEach((value) => {
            this.cashTotal += value.a_payer - value.creance.montant;
            this.scTotal += value.montant_pec;
          });

          this.generalTotal =
            this.cashTotal + this.chequeTotal + this.cardTotal + this.scTotal;

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
    this.collectionSize = this.invoicesList.length;

    this.invoicesListCut = this.invoicesList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  openActivitiesDetailModal() {
    const invoiceModalRef = this.modalService.open(ActivitiesDetailComponent, {
      size: "xl",
      centered: true,
      // scrollable: true,
      backdrop: "static",
      keyboard: false,
    });
  }

  printInvoice(invoiceId: number) {
    this.invoiceService.loadPdf(invoiceId).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Génération du reçu."],
          type: ToastType.Success,
        });

        const pdfModalRef = this.modalService.open(PdfModalComponent, {
          size: "xl",
          centered: true,
          scrollable: true,
          backdrop: "static",
        });

        pdfModalRef.componentInstance.title = "Reçu";
        pdfModalRef.componentInstance.pdfSrc = data;
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Echec de la génération du reçu."],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  displayReportModal() {
    const reportModal = this.modalService.open(
      ReportSearchCriteriaModalComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        // backdrop: "static",
      }
    );
  }

  displayInsuranceRequestsModal() {
    const insuranceRequestsModal = this.modalService.open(
      InsurancesDebtsModalComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        // backdrop: "static",
      }
    );
  }
}
