import { Component, Input, OnInit } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { Invoice } from "src/app/models/secretariat/patients/invoice.model";

@Component({
  selector: "app-invoice-details-modal",
  templateUrl: "./invoice-details-modal.component.html",
  styleUrls: ["./invoice-details-modal.component.scss"],
})
export class InvoiceDetailsModalComponent implements OnInit {
  @Input()
  invoice!: Invoice;

  constructor(public modal: NgbActiveModal) {}

  ngOnInit(): void {}
}
