import { Component, Input, OnInit } from "@angular/core";
import {
  NgbActiveModal,
  NgbPanelChangeEvent,
} from "@ng-bootstrap/ng-bootstrap";
import { IPrestationSelect } from "../patient-activity/activity.models";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";

@Component({
  selector: "app-patient-invoice-form",
  templateUrl: "./patient-invoice-form.component.html",
  styleUrls: ["./patient-invoice-form.component.scss"],
})
export class PatientInvoiceFormComponent implements OnInit {
  @Input()
  patientActivities: IPrestationSelect[] = [];

  // @Input()
  // patient: Patient;

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  private initialPatientShareAmount!: number;
  patientShareAmount!: number;

  totalAmount!: number;

  discountValue!: number;
  // discountPercent!: number;

  markupValue!: number;
  // markupPercent!: number;

  patientGivenAmount!: number;

  restAmount!: number;

  totalAmountControl = new FormControl("0");
  insuranceRateControl = new FormControl("0");

  rptpRadioControl = new FormControl("", [Validators.required]);

  insuranceShareControl = new FormControl("0");
  previousDebtsTotalControl = new FormControl("0");

  invoiceDateControl = new FormControl(this.today);
  paymentDateControl = new FormControl(this.today);

  discountRadioControl = new FormControl("");
  discountValueControl = new FormControl({ value: "0", disabled: true });
  discountPercentControl = new FormControl({ value: "0", disabled: true });

  markupRadioControl = new FormControl("");
  markupValueControl = new FormControl({ value: "0", disabled: true });
  markupPercentControl = new FormControl({ value: "0", disabled: true });

  paymentRadioControl = new FormControl("");
  paymentCashControl = new FormControl({ value: "0", disabled: true });
  paymentCardControl = new FormControl({ value: "0", disabled: true });
  paymentChequeControl = new FormControl({ value: "0", disabled: true });

  invoiceForm?: FormGroup;

  isInvoiceFormSubmitted = false;

  constructor(
    public modal: NgbActiveModal,
    private patientService: PatientService
  ) {}

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

    this.setInitialValues();

    this.onFormInputsChanges();

    // this.invoiceForm = this.formBuilder.group({
    //   totalAmount: "",
    // insuranceRate: "",
    // insuranceShare: "",
    // previousDebtsTotal: "",
    // billingDate: "",
    // paymentDate: "",
    // });
  }

  setInitialValues() {
    this.initialPatientShareAmount = 0;

    this.patientShareAmount = this.initialPatientShareAmount;

    this.totalAmount = 0;

    this.discountValue = 0;
    // this.discountPercent = 0;

    this.markupValue = 0;
    // this.markupPercent = 0;

    this.patientGivenAmount = 0;

    this.restAmount = 0;

    this.patientActivities.forEach(
      (pActivity) => (this.totalAmount += pActivity.total_price)
    );
    this.totalAmountControl.setValue(this.totalAmount);

    this.insuranceRateControl.setValue(
      this.patientService.getActivePatientRate()
    );

    this.setPatientShareToMaxAmount();

    this.insuranceShareControl.setValue(
      this.totalAmount - this.patientShareAmount
    );
  }

  onFormInputsChanges() {
    this.rptpRadioControl.valueChanges.subscribe((value) => {
      const totalAmount = this.parseIntOrZero(this.totalAmountControl.value);
      const insuranceRate = this.parseIntOrZero(
        this.insuranceRateControl.value
      );

      if (value === "rp") {
        this.initialPatientShareAmount =
          totalAmount - (totalAmount * insuranceRate) / 100;

        this.patientShareAmount = this.initialPatientShareAmount;
      } else if (value === "tp") {
        this.setPatientShareToMaxAmount();
      }

      this.insuranceShareControl.setValue(
        this.totalAmount - this.patientShareAmount
      );

      this.discountValueControl.setValue("0");
      this.discountPercentControl.setValue("0");

      this.markupValueControl.setValue("0");
      this.markupPercentControl.setValue("0");

      this.calculateRest();
    });

    this.discountRadioControl.valueChanges.subscribe((value) => {
      if (value === "value") {
        this.discountValueControl.enable();
        this.discountValueControl.setValue("");

        this.discountPercentControl.disable();
        this.discountPercentControl.setValue("0");

        this.markupRadioControl.setValue("");
      } else if (value === "percent") {
        this.discountPercentControl.enable();
        this.discountPercentControl.setValue("");

        this.discountValueControl.disable();
        this.discountValueControl.setValue("0");

        this.markupRadioControl.setValue("");
      } else {
        this.discountValueControl.disable();
        this.discountValueControl.setValue("0");

        this.discountPercentControl.disable();
        this.discountPercentControl.setValue("0");
      }

      // this.markupValueControl.setValue("0");
      // this.markupPercentControl.setValue("0");
      // this.markupRadioControl.setValue("");
    });

    this.discountValueControl.valueChanges.subscribe((value) => {
      this.discountValue = this.parseIntOrZero(value);

      const discountedAmount =
        this.initialPatientShareAmount - this.discountValue;

      this.patientShareAmount = discountedAmount < 0 ? 0 : discountedAmount;

      this.calculateRest();
    });

    this.discountPercentControl.valueChanges.subscribe((value) => {
      const discountPercentage = this.parseIntOrZero(value);

      this.discountValue =
        (this.initialPatientShareAmount * discountPercentage) / 100;

      const discountedAmount =
        this.initialPatientShareAmount - this.discountValue;

      this.patientShareAmount = discountedAmount < 0 ? 0 : discountedAmount;

      this.calculateRest();
    });

    this.markupRadioControl.valueChanges.subscribe((value) => {
      if (value === "value") {
        this.markupValueControl.enable();
        this.markupValueControl.setValue("");

        this.markupPercentControl.disable();
        this.markupPercentControl.setValue("0");

        this.discountRadioControl.setValue("");
      } else if (value === "percent") {
        this.markupPercentControl.enable();
        this.markupPercentControl.setValue("");

        this.markupValueControl.disable();
        this.markupValueControl.setValue("0");

        this.discountRadioControl.setValue("");
      } else {
        this.markupValueControl.disable();
        this.markupValueControl.setValue("0");

        this.markupPercentControl.disable();
        this.markupPercentControl.setValue("0");
      }

      // this.discountRadioControl.setValue("");
    });

    this.markupValueControl.valueChanges.subscribe((value) => {
      this.markupValue = this.parseIntOrZero(value);

      const markupAmount = this.initialPatientShareAmount + this.markupValue;

      this.patientShareAmount = markupAmount < 0 ? 0 : markupAmount;

      this.calculateRest();
    });

    this.markupPercentControl.valueChanges.subscribe((value) => {
      const markupPercentage = this.parseIntOrZero(value);

      this.markupValue =
        (this.initialPatientShareAmount * markupPercentage) / 100;

      const markupAmount = this.initialPatientShareAmount + this.markupValue;

      this.patientShareAmount = markupAmount < 0 ? 0 : markupAmount;

      this.calculateRest();
    });

    this.paymentRadioControl.valueChanges.subscribe((value) => {
      if (value === "cash") {
        this.paymentCashControl.enable();
        this.paymentCashControl.setValue("");

        this.paymentCardControl.disable();
        this.paymentCardControl.setValue("0");

        this.paymentChequeControl.disable();
        this.paymentChequeControl.setValue("0");
      } else if (value === "card") {
        this.paymentCashControl.disable();
        this.paymentCashControl.setValue("0");

        this.paymentCardControl.enable();
        this.paymentCardControl.setValue("");

        this.paymentChequeControl.disable();
        this.paymentChequeControl.setValue("0");
      } else if (value === "cheque") {
        this.paymentCashControl.disable();
        this.paymentCashControl.setValue("0");

        this.paymentCardControl.disable();
        this.paymentCardControl.setValue("0");

        this.paymentChequeControl.enable();
        this.paymentChequeControl.setValue("");
      }
    });

    this.paymentCashControl.valueChanges.subscribe((value) => {
      this.patientGivenAmount = this.parseIntOrZero(value);
      this.calculateRest();
    });

    this.paymentCardControl.valueChanges.subscribe((value) => {
      this.patientGivenAmount = this.parseIntOrZero(value);
      this.calculateRest();
    });

    this.paymentChequeControl.valueChanges.subscribe((value) => {
      this.patientGivenAmount = this.parseIntOrZero(value);
      this.calculateRest();
    });
  }

  setPatientShareToMaxAmount() {
    this.initialPatientShareAmount = this.totalAmount;
    this.patientShareAmount = this.initialPatientShareAmount;
  }

  parseIntOrZero(value: any) {
    return Number.isNaN(parseInt(value)) ? 0 : parseInt(value);
  }

  calculateRest() {
    const rest = this.patientGivenAmount - this.patientShareAmount;

    this.restAmount = rest < 0 ? 0 : rest;
  }

  beforePanelChange($event: NgbPanelChangeEvent) {
    if ($event.panelId === "payment") {
      $event.preventDefault();
    }
  }

  validatePayment() {
    this.isInvoiceFormSubmitted = true;
    console.log("Validate payment");
    
  }

  // openInvoiceModal(invoiceModal: any) {
  //   this.modalService.open(invoiceModal, { size: 'xl', centered: true });
  // }
}
