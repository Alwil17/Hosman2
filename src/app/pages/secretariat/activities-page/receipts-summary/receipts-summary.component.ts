import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Invoice } from "src/app/models/secretariat/patients/invoice.model";
import { InvoiceService } from "src/app/services/secretariat/patients/invoice.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ActivitiesDetailComponent } from "../activities-detail/activities-detail.component";

@Component({
  selector: "app-receipts-summary",
  templateUrl: "./receipts-summary.component.html",
  styleUrls: ["./receipts-summary.component.scss"],
})
export class ReceiptsSummaryComponent implements OnInit {
  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  receiptsDateControl = new FormControl(this.today);

  searchTerm = "";

  invoicesList: Invoice[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.invoicesList.length;
  invoicesListCut: Invoice[] = [];

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
      .searchBy({ minDate: new Date(this.receiptsDateControl.value) })
      .subscribe({
        next: (data) => {
          this.invoicesList = data;

          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
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
}
