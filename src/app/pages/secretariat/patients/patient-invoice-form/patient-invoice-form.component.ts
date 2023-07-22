import { Component, Input, OnInit } from "@angular/core";
import {
  NgbActiveModal,
  NgbPanelChangeEvent,
} from "@ng-bootstrap/ng-bootstrap";
import { IActivitySelect } from "../patient-activity/activity.models";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";

@Component({
  selector: "app-patient-invoice-form",
  templateUrl: "./patient-invoice-form.component.html",
  styleUrls: ["./patient-invoice-form.component.scss"],
})
export class PatientInvoiceFormComponent implements OnInit {
  @Input()
  patientActivities: IActivitySelect[] = [];

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  invoiceForm?: FormGroup;

  totalAmountControl = new FormControl("200000000");
  insuranceRateControl = new FormControl("60");

  rptpRadioControl = new FormControl("", [Validators.required]);

  insuranceShareControl = new FormControl("10000");
  previousDebtsTotalControl = new FormControl("0");

  invoiceDateControl = new FormControl(this.today, [Validators.required]);
  paymentDateControl = new FormControl(this.today, [Validators.required]);

  discountRadioControl = new FormControl("");
  discountValueControl = new FormControl({ value: "", disabled: true });
  discountPercentControl = new FormControl({ value: "", disabled: true });

  markupRadioControl = new FormControl("");
  markupValueControl = new FormControl({ value: "", disabled: true });
  markupPercentControl = new FormControl({ value: "", disabled: true });

  paymentRadioControl = new FormControl("cash");
  paymentCashControl = new FormControl("");
  paymentCardControl = new FormControl({ value: "", disabled: true });
  paymentChequeControl = new FormControl({ value: "", disabled: true });

  constructor(public modal: NgbActiveModal, private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.invoiceForm = new FormGroup({
      totalAmountControl: this.totalAmountControl,
      insuranceRateControl: this.insuranceRateControl,

      rptpRadioControl: this.rptpRadioControl,

      insuranceShareControl: this.insuranceShareControl,
      previousDebtsTotalControl: this.previousDebtsTotalControl,

      invoiceDateControl: this.invoiceDateControl,
      paymentDateControl: this.paymentDateControl,

      discountRadioControl: this.discountRadioControl,
      discountValueControl: this.discountValueControl,
      discountPercentControl: this.discountPercentControl,

      markupRadioControl: this.markupRadioControl,
      markupValueControl: this.markupValueControl,
      markupPercentControl: this.markupPercentControl,

      paymentRadioControl: this.paymentRadioControl,
      paymentCashControl: this.paymentCashControl,
      paymentCardControl: this.paymentCardControl,
      paymentChequeControl: this.paymentChequeControl,
    });

    this.onDiscountRadioChanges();

    // this.invoiceForm = this.formBuilder.group({
    //   totalAmount: "",
    // insuranceRate: "",
    // insuranceShare: "",
    // previousDebtsTotal: "",
    // billingDate: "",
    // paymentDate: "",
    // });
  }

  onDiscountRadioChanges() {
    this.rptpRadioControl.valueChanges.subscribe((value) => {});

    this.discountRadioControl.valueChanges.subscribe((value) => {
      if (value === "value") {
        this.discountValueControl.enable();
        this.discountPercentControl.disable();
      } else if (value === "percent") {
        this.discountValueControl.disable();
        this.discountPercentControl.enable();
      }
    });

    this.markupRadioControl.valueChanges.subscribe((value) => {
      if (value === "value") {
        this.markupValueControl.enable();
        this.markupPercentControl.disable();
      } else if (value === "percent") {
        this.markupValueControl.disable();
        this.markupPercentControl.enable();
      }
    });

    this.paymentRadioControl.valueChanges.subscribe((value) => {
      if (value === "cash") {
        this.paymentCashControl.enable();
        this.paymentCardControl.disable();
        this.paymentChequeControl.disable();
      } else if (value === "card") {
        this.paymentCashControl.disable();
        this.paymentCardControl.enable();
        this.paymentChequeControl.disable();
      } else if (value == "cheque") {
        this.paymentCashControl.disable();
        this.paymentCardControl.disable();
        this.paymentChequeControl.enable();
      }
    });
  }

  beforePanelChange($event: NgbPanelChangeEvent) {
    if ($event.panelId === "payment") {
      $event.preventDefault();
    }
  }

  onSubmit() {}

  // openInvoiceModal(invoiceModal: any) {
  //   this.modalService.open(invoiceModal, { size: 'xl', centered: true });
  // }
}
