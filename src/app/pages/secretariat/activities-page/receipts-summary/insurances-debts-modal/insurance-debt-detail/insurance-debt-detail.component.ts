import { Component, Input, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceService } from '@services/secretariat/patients/invoice.service';
import { ToastService } from '@services/secretariat/shared/toast.service';
import { ToastType } from 'src/app/models/extras/toast-type.model';
import { Invoice } from 'src/app/models/secretariat/patients/invoice.model';

@Component({
  selector: 'app-insurance-debt-detail',
  templateUrl: './insurance-debt-detail.component.html',
  styleUrls: ['./insurance-debt-detail.component.scss']
})
export class InsuranceDebtDetailComponent implements OnInit {
  @Input()
  invoice!: Invoice;

  @Input()
  invoiceId!: Number;

  constructor(
    private invoiceService: InvoiceService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.loadInvoice()
  }

  loadInvoice() {
    this.invoiceService
      .get(this.invoiceId)
      .subscribe({
        next: (data) => {
          this.invoice = data;

          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
          // });

          
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

}
