import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { CheckoutService } from "src/app/services/secretariat/activities/checkout.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { PdfModalComponent } from "src/app/shared/modals/pdf-modal/pdf-modal.component";

@Component({
  selector: "app-report-search-criteria-modal",
  templateUrl: "./report-search-criteria-modal.component.html",
  styleUrls: ["./report-search-criteria-modal.component.scss"],
})
export class ReportSearchCriteriaModalComponent implements OnInit {
  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  startDateControl = new FormControl(this.today);
  endDateControl = new FormControl();

  constructor(
    private checkoutService: CheckoutService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {}

  displayReportPdf() {
    const minDate = new Date(this.startDateControl.value);
    const maxDate = this.endDateControl.value
      ? new Date(this.endDateControl.value)
      : undefined;

    this.checkoutService
      .loadPdfBy({ minDate: minDate, maxDate: maxDate })
      .subscribe({
        next: (data) => {
          this.toastService.show({
            messages: ["Génération de la fiche de compte."],
            type: ToastType.Success,
          });

          const pdfModalRef = this.modalService.open(PdfModalComponent, {
            size: "xl",
            centered: true,
            scrollable: true,
            backdrop: "static",
          });

          pdfModalRef.componentInstance.title = "Fiche de comptes";
          pdfModalRef.componentInstance.pdfSrc = data;
        },
        error: (e) => {
          console.error(e);

          this.toastService.show({
            messages: ["Echec de la génération de la fiche de comptes."],
            delay: 10000,
            type: ToastType.Error,
          });
        },
      });
  }
}
