import {
  Component,
  ElementRef,
  HostListener,
  Input,
  OnInit,
  ViewChild,
} from "@angular/core";
import {
  NgbActiveModal,
  NgbModal,
  NgbPanelChangeEvent,
} from "@ng-bootstrap/ng-bootstrap";
import { IPrestationSelect } from "../patient-activity/activity.models";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { InvoiceService } from "src/app/services/secretariat/patients/invoice.service";
import { InvoiceRequest } from "src/app/models/secretariat/patients/requests/invoice-request.model";
import { MarkupRequest } from "src/app/models/secretariat/patients/requests/markup-request.model";
import { RemainderRequest } from "src/app/models/secretariat/patients/requests/remainder-request.model";
import { DiscountRequest } from "src/app/models/secretariat/patients/requests/discount-request.model";
import { DebtRequest } from "src/app/models/secretariat/patients/requests/debt-request.model";
import { parseIntOrZero } from "src/app/helpers/parsers";
import { PdfModalComponent } from "src/app/shared/modals/pdf-modal/pdf-modal.component";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { InvoiceResponse } from "src/app/models/secretariat/patients/responses/invoice-response.model";
import { StatusService } from "src/app/services/secretariat/patients/status.service";
import { PaymentModeService } from "src/app/services/secretariat/patients/payment-mode.service";
import { firstValueFrom, forkJoin, merge } from "rxjs";
import { PaymentModeCode } from "src/app/models/enums/payment-mode.enum";
import { StatusCode } from "src/app/models/enums/status.enum";
import { ConfirmModalComponent } from "src/app/shared/modals/confirm-modal/confirm-modal.component";
import { HasInsuranceCode } from "src/app/models/secretariat/patients/has-insurance.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";

@Component({
  selector: "app-patient-invoice-form",
  templateUrl: "./patient-invoice-form.component.html",
  styleUrls: ["./patient-invoice-form.component.scss"],
})
export class PatientInvoiceFormComponent implements OnInit {
  @Input()
  patientInfos!: Patient;

  @Input()
  patientActivities!: IPrestationSelect[];

  @Input()
  preInvoiceInfos!: InvoiceResponse;

  // @Input()
  // patientPrestationInfo!: Prestation;

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

  debtAmount!: number;

  remainderAmount!: number;

  totalAmountControl = new FormControl("0");
  insuranceRateControl = new FormControl("0");
  invoiceSurplusControl = new FormControl("0");

  rptpRadioControl = new FormControl("", [Validators.required]);

  insuranceShareControl = new FormControl("0");
  previousDebtsTotalControl = new FormControl("0");

  invoiceDateControl = new FormControl(this.today);
  paymentDateControl = new FormControl(this.today);

  discountRadioControl = new FormControl("");
  discountValueControl = new FormControl({ value: "0", disabled: true });
  discountPercentControl = new FormControl({ value: "0", disabled: true });
  discountReasonControl = new FormControl(null);

  markupRadioControl = new FormControl("");
  markupValueControl = new FormControl({ value: "0", disabled: true });
  markupPercentControl = new FormControl({ value: "0", disabled: true });
  markupReasonControl = new FormControl(null);

  // paymentRadioControl = new FormControl("");

  paymentCheckCashControl = new FormControl(false);
  paymentCheckCardControl = new FormControl(false);
  paymentCheckChequeControl = new FormControl(false);

  paymentCashControl = new FormControl({ value: "0", disabled: true });

  paymentCardControl = new FormControl({ value: "0", disabled: true });
  paymentCardReceiptNumberControl = new FormControl(null);

  paymentChequeControl = new FormControl({ value: "0", disabled: true });
  paymentChequeReceiptNumberControl = new FormControl(null);
  paymentChequeBankControl = new FormControl(null);

  invoiceForm!: FormGroup;

  isInvoiceFormSubmitted = false;

  @ViewChild("discountValueField", { read: ElementRef })
  discountValueField!: ElementRef;
  @ViewChild("discountPercentField", { read: ElementRef })
  discountPercentField!: ElementRef;
  @ViewChild("markupValueField", { read: ElementRef })
  markupValueField!: ElementRef;
  @ViewChild("markupPercentField", { read: ElementRef })
  markupPercentField!: ElementRef;
  @ViewChild("paymentCashField", { read: ElementRef })
  paymentCashField!: ElementRef;
  @ViewChild("paymentCardField", { read: ElementRef })
  paymentCardField!: ElementRef;
  @ViewChild("paymentChequeField", { read: ElementRef })
  paymentChequeField!: ElementRef;

  patientActivitiesTotal = 0;

  constructor(
    public modal: NgbActiveModal,
    private invoiceService: InvoiceService,
    private modalService: NgbModal,
    private toastService: ToastService,
    private statusService: StatusService,
    private paymentModeService: PaymentModeService
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

      // paymentRadioControl: this.paymentRadioControl,
      paymentCheckCashControl: this.paymentCheckCashControl,
      paymentCheckCardControl: this.paymentCheckCardControl,
      paymentCheckChequeControl: this.paymentCheckChequeControl,

      paymentCashControl: this.paymentCashControl,
      paymentCardControl: this.paymentCardControl,
      paymentChequeControl: this.paymentChequeControl,
    });

    this.setInitialValues();

    this.onFormInputsChanges();
  }

  setInitialValues() {
    this.initialPatientShareAmount = 0;

    this.patientShareAmount = this.initialPatientShareAmount;

    this.totalAmount = this.preInvoiceInfos.total; // = 0;

    this.discountValue = 0;
    // this.discountPercent = 0;

    this.markupValue = 0;
    // this.markupPercent = 0;

    this.patientGivenAmount = 0;

    this.debtAmount = 0;

    this.remainderAmount = 0;

    // this.patientActivities.forEach(
    //   (pActivity) => (this.totalAmount += pActivity.total_price)
    // );
    this.totalAmountControl.setValue(this.totalAmount);

    this.insuranceRateControl.setValue(
      parseIntOrZero(this.patientInfos.taux_assurance)
    );

    this.invoiceSurplusControl.setValue(this.preInvoiceInfos.surplus);

    // this.setPatientShareToMaxAmount();

    this.insuranceShareControl.setValue(
      "0"
      // this.preInvoiceInfos.montant_pec
      // this.totalAmount - this.patientShareAmount
    );

    // console.log(new Date(this.preInvoiceInfos.date_facture).toLocaleDateString("fr-ca"));
    // console.log(new Date(this.preInvoiceInfos.date_reglement).toLocaleDateString("fr-ca"));

    this.invoiceDateControl.setValue(
      new Date(this.preInvoiceInfos.date_facture).toLocaleDateString("fr-ca")
    );

    this.paymentDateControl.setValue(
      new Date(this.preInvoiceInfos.date_reglement).toLocaleDateString("fr-ca")
    );

    this.patientActivities.forEach((value) => {
      this.patientActivitiesTotal += value.total_price;
    });
  }

  calculateDiscountAndMarkupAmount() {
    const discountAndMarkupAmount =
      this.initialPatientShareAmount - this.discountValue + this.markupValue;

    this.patientShareAmount =
      discountAndMarkupAmount < 0 ? 0 : discountAndMarkupAmount;

    this.calculateRemainder();
    this.calculateDebt();
  }

  onFormInputsChanges() {
    this.rptpRadioControl.valueChanges.subscribe((value) => {
      const totalAmount = parseIntOrZero(this.totalAmountControl.value);
      const insuranceRate = parseIntOrZero(this.insuranceRateControl.value);

      if (value === "rp") {
        // this.initialPatientShareAmount =
        //   totalAmount - (totalAmount * insuranceRate) / 100;

        this.initialPatientShareAmount = this.preInvoiceInfos.a_payer;

        this.patientShareAmount = this.initialPatientShareAmount;

        this.insuranceShareControl.setValue(this.preInvoiceInfos.montant_pec);
      } else if (value === "tp") {
        this.setPatientShareToMaxAmount();

        this.insuranceShareControl.setValue("0");
      }

      // this.insuranceShareControl.setValue(
      //   this.totalAmount - this.patientShareAmount
      // );

      // this.discountValueControl.setValue("0");
      // this.discountPercentControl.setValue("0");

      // this.markupValueControl.setValue("0");
      // this.markupPercentControl.setValue("0");

      // this.calculateRemainder();
      // this.calculateDebt();

      this.calculateDiscountAndMarkupAmount();
    });

    this.discountRadioControl.valueChanges.subscribe((value) => {
      if (value === "value") {
        this.discountValueControl.enable();
        this.discountValueControl.setValue("");

        this.discountPercentControl.disable();
        this.discountPercentControl.setValue("0");

        // this.markupRadioControl.setValue("");

        setTimeout(() => {
          this.discountValueField.nativeElement.querySelector("input").focus();
        });
      } else if (value === "percent") {
        this.discountPercentControl.enable();
        this.discountPercentControl.setValue("");

        this.discountValueControl.disable();
        this.discountValueControl.setValue("0");

        // this.markupRadioControl.setValue("");

        setTimeout(() => {
          this.discountPercentField.nativeElement
            .querySelector("input")
            .focus();
        });
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
      this.discountValue = parseIntOrZero(value);

      // const discountedAmount =
      //   this.initialPatientShareAmount - this.discountValue + this.markupValue;

      // this.patientShareAmount = discountedAmount < 0 ? 0 : discountedAmount;

      // this.calculateRemainder();
      // this.calculateDebt();

      this.calculateDiscountAndMarkupAmount();
    });

    this.discountPercentControl.valueChanges.subscribe((value) => {
      const discountPercentage = parseIntOrZero(value);

      this.discountValue =
        (this.initialPatientShareAmount * discountPercentage) / 100;

      // const discountedAmount =
      //   this.initialPatientShareAmount - this.discountValue + this.markupValue;

      // this.patientShareAmount = discountedAmount < 0 ? 0 : discountedAmount;

      // this.calculateRemainder();
      // this.calculateDebt();

      this.calculateDiscountAndMarkupAmount();
    });

    this.markupRadioControl.valueChanges.subscribe((value) => {
      if (value === "value") {
        this.markupValueControl.enable();
        this.markupValueControl.setValue("");

        this.markupPercentControl.disable();
        this.markupPercentControl.setValue("0");

        // this.discountRadioControl.setValue("");

        setTimeout(() => {
          this.markupValueField.nativeElement.querySelector("input").focus();
        });
      } else if (value === "percent") {
        this.markupPercentControl.enable();
        this.markupPercentControl.setValue("");

        this.markupValueControl.disable();
        this.markupValueControl.setValue("0");

        // this.discountRadioControl.setValue("");

        setTimeout(() => {
          this.markupPercentField.nativeElement.querySelector("input").focus();
        });
      } else {
        this.markupValueControl.disable();
        this.markupValueControl.setValue("0");

        this.markupPercentControl.disable();
        this.markupPercentControl.setValue("0");
      }

      // this.discountRadioControl.setValue("");
    });

    this.markupValueControl.valueChanges.subscribe((value) => {
      this.markupValue = parseIntOrZero(value);

      // const markupAmount =
      //   this.initialPatientShareAmount + this.markupValue - this.discountValue;

      // this.patientShareAmount = markupAmount < 0 ? 0 : markupAmount;

      // this.calculateRemainder();
      // this.calculateDebt();

      this.calculateDiscountAndMarkupAmount();
    });

    this.markupPercentControl.valueChanges.subscribe((value) => {
      const markupPercentage = parseIntOrZero(value);

      this.markupValue =
        (this.initialPatientShareAmount * markupPercentage) / 100;

      // const markupAmount =
      //   this.initialPatientShareAmount + this.markupValue - this.discountValue;

      // this.patientShareAmount = markupAmount < 0 ? 0 : markupAmount;

      // this.calculateRemainder();
      // this.calculateDebt();

      this.calculateDiscountAndMarkupAmount();
    });

    this.paymentCheckCashControl.valueChanges.subscribe((value) => {
      this.calculateDebt();

      if (value == true) {
        this.paymentCashControl.enable();
        this.paymentCashControl.setValue(this.debtAmount);

        setTimeout(() => {
          // this.paymentCashField.nativeElement.querySelector("input").focus();
          this.paymentCashField.nativeElement.querySelector("input").select();
        });
      } else {
        this.paymentCashControl.disable();
        this.paymentCashControl.setValue("0");
      }
    });
    this.paymentCheckCardControl.valueChanges.subscribe((value) => {
      this.calculateDebt();

      if (value == true) {
        this.paymentCardControl.enable();
        this.paymentCardControl.setValue(this.debtAmount);

        setTimeout(() => {
          // this.paymentCardField.nativeElement.querySelector("input").focus();
          this.paymentCardField.nativeElement.querySelector("input").select();
        });
      } else {
        this.paymentCardControl.disable();
        this.paymentCardControl.setValue("0");
      }
    });
    this.paymentCheckChequeControl.valueChanges.subscribe((value) => {
      this.calculateDebt();

      if (value == true) {
        this.paymentChequeControl.enable();
        this.paymentChequeControl.setValue(this.debtAmount);

        setTimeout(() => {
          this.paymentChequeField.nativeElement.querySelector("input").focus();
          this.paymentChequeField.nativeElement.querySelector("input").select();
        });
      } else {
        this.paymentChequeControl.disable();
        this.paymentChequeControl.setValue("0");
      }
    });

    this.paymentCashControl.valueChanges.subscribe((value) => {
      this.calculatePatientGivenAmount();
      // this.patientGivenAmount = parseIntOrZero(value);
      this.calculateRemainder();
      this.calculateDebt();
    });

    this.paymentCardControl.valueChanges.subscribe((value) => {
      this.calculatePatientGivenAmount();
      // this.patientGivenAmount = parseIntOrZero(value);
      this.calculateRemainder();
      this.calculateDebt();
    });

    this.paymentChequeControl.valueChanges.subscribe((value) => {
      this.calculatePatientGivenAmount();
      // this.patientGivenAmount = parseIntOrZero(value);
      this.calculateRemainder();
      this.calculateDebt();
    });
  }

  setPatientShareToMaxAmount() {
    this.initialPatientShareAmount = this.totalAmount;
    this.patientShareAmount = this.initialPatientShareAmount;
  }

  calculatePatientGivenAmount() {
    const total =
      parseIntOrZero(this.paymentCashControl.value) +
      parseIntOrZero(this.paymentCardControl.value) +
      parseIntOrZero(this.paymentChequeControl.value);

    this.patientGivenAmount = total;
  }

  calculateDebt() {
    const debt = this.patientShareAmount - this.patientGivenAmount;

    this.debtAmount = debt < 0 ? 0 : debt;
  }

  calculateRemainder() {
    const remainder = this.patientGivenAmount - this.patientShareAmount;

    this.remainderAmount = remainder < 0 ? 0 : remainder;
  }

  beforePanelChange($event: NgbPanelChangeEvent) {
    // if ($event.panelId === "payment") {
    //   $event.preventDefault();
    // }
  }

  // LISTENER TO HANDLE ENTER KEY UP
  @HostListener("window:keydown.enter", ["$event"])
  handleEnterKeyUp(event: KeyboardEvent) {
    event.preventDefault();

    this.validatePayment();
  }

  // @HostListener("window:beforeprint", ["$event"])
  // handlePrint(event: any) {
  //   console.log('beforeprint');
  // }

  // REGISTER PAYMENT
  async validatePayment() {
    const patient = this.patientInfos;

    // VALIDATE FORM FIELDS
    this.isInvoiceFormSubmitted = true;

    if (this.invoiceForm?.invalid) {
      const invalidFieldsData = this.rptpRadioControl.invalid
        ? ['Veuillez faire un choix entre "payer sa part" et "tout payer"']
        : [""];

      this.toastService.show({
        messages: invalidFieldsData,

        type: ToastType.Warning,
      });

      return;
    }

    // PAYMENT REGISTRATION
    const cashAmount = parseIntOrZero(this.paymentCashControl.value);
    const cardAmount = parseIntOrZero(this.paymentCardControl.value);
    const chequeAmount = parseIntOrZero(this.paymentChequeControl.value);

    // OPEN CONFIRMATION MODAL
    const confirmModalRef = this.modalService.open(ConfirmModalComponent, {
      size: "md",
      centered: true,
      keyboard: false,
      backdrop: "static",
    });

    const messageTotal = cashAmount + cardAmount + chequeAmount;
    const messageFullname = patient.nom + " " + patient.prenoms;
    let messageInsurance = "";
    if (patient.is_assure === HasInsuranceCode.NO_LOCAL) {
      messageInsurance = "non assuré (local)";
    } else if (patient.is_assure === HasInsuranceCode.NO_FOREIGNER) {
      messageInsurance = "non assuré (étranger)";
    } else if (patient.is_assure === HasInsuranceCode.YES_LOCAL) {
      messageInsurance =
        "assuré par " +
        patient.assurance?.nom +
        " (locale) à " +
        patient.taux_assurance +
        " %";
    } else if (patient.is_assure === HasInsuranceCode.YES_FOREIGNER) {
      messageInsurance =
        "assuré par " +
        patient.assurance?.nom +
        "(étrangère) à " +
        patient.taux_assurance +
        " %";
    }

    confirmModalRef.componentInstance.message =
      "Voulez-vous vraiment enregistrer le paiement de " +
      messageTotal +
      " FCFA du patient " +
      messageFullname +
      ", " +
      messageInsurance +
      " ?";

    const isConfirmed = await firstValueFrom(
      confirmModalRef.componentInstance.isConfirmed.asObservable()
    );

    // CLOSE CONFIRMATION MODAL
    confirmModalRef.close();

    // CHECK IF USER CONFIRMED OR NOT
    if (!isConfirmed) {
      return;
    }

    const paymentModes: {
      mode_payement_id: number;
      montant: number;
      no_transaction?: string;
      nom_service?: string;
    }[] = [];

    // if (cashAmount !== 0) {
    //   paymentModes.push({
    //     mode_payement_id: 2,
    //     montant: cashAmount,
    //   });
    // }
    // if (cardAmount !== 0) {
    //   paymentModes.push({
    //     mode_payement_id: 3,
    //     montant: cardAmount,
    //   });
    // }
    // if (chequeAmount !== 0) {
    //   paymentModes.push({
    //     mode_payement_id: 4,
    //     montant: chequeAmount,
    //   });
    // }

    const statuses$ = this.statusService.getAll();
    const paymentModes$ = this.paymentModeService.getAll();

    forkJoin({ statuses: statuses$, paymentModes: paymentModes$ }).subscribe({
      next: (data) => {
        // Getting payment modes
        if (cashAmount !== 0) {
          paymentModes.push({
            mode_payement_id: data.paymentModes.find(
              (value) => PaymentModeCode.CASH == value.slug
            )!.id,
            montant: cashAmount,
          });
        }
        if (cardAmount !== 0) {
          paymentModes.push({
            mode_payement_id: data.paymentModes.find(
              (value) => PaymentModeCode.CARD == value.slug
            )!.id,
            montant: cardAmount,
            no_transaction: this.paymentCardReceiptNumberControl.value,
          });
        }
        if (chequeAmount !== 0) {
          paymentModes.push({
            mode_payement_id: data.paymentModes.find(
              (value) => PaymentModeCode.CHEQUE == value.slug
            )!.id,
            montant: chequeAmount,
            no_transaction: this.paymentChequeReceiptNumberControl.value,
            nom_service: this.paymentChequeBankControl.value,
          });
        }

        console.log(JSON.stringify(paymentModes, null, 2));

        // Getting
        const invoice = new InvoiceRequest({
          total: this.totalAmount,
          montant_pec: parseIntOrZero(this.insuranceShareControl.value),
          reduction: new DiscountRequest({
            montant: this.discountValue,
            motif: this.discountReasonControl.value,
            date_operation: new Date(),
          }),
          majoration: new MarkupRequest({
            montant: this.markupValue,
            motif: this.markupReasonControl.value,
            date_operation: new Date(),
          }),

          a_payer: this.patientShareAmount,
          creance: new DebtRequest({
            montant: this.debtAmount,
            etat_id: data.statuses.find(
              (value) => StatusCode.PAID == value.indice
            )!.id, // this.debtAmount > 0 ? 1 : 2,
            date_operation: new Date(),
          }),
          reliquat: new RemainderRequest({
            montant: this.remainderAmount,
            etat_id: data.statuses.find(
              (value) => StatusCode.PAID == value.indice
            )!.id, // this.remainderAmount > 0 ? 1 : 2,
            date_operation: new Date(),
          }),

          date_facture: new Date(), // new Date(this.invoiceDateControl.value),
          date_reglement: new Date(), // new Date(this.paymentDateControl.value),
          patient_id: patient.id,
          prestation_id: this.preInvoiceInfos.prestation_id,
          etat_id: data.statuses.find(
            (value) => StatusCode.PAID == value.indice
          )!.id, // this.debtAmount > 0 ? 1 : 2,
          mode_payements: paymentModes,
        });

        console.log(JSON.stringify(invoice, null, 2));

        this.invoiceService.create(invoice).subscribe({
          next: (data) => {
            console.log(data);

            this.toastService.show({
              messages: ["Paiement enregistré."],
              type: ToastType.Success,
            });

            this.invoiceService.loadPdf(data).subscribe({
              next: (data) => {
                this.toastService.show({
                  messages: ["Génération du reçu."],
                  type: ToastType.Success,
                });

                const pdfModalRef = this.modalService.open(PdfModalComponent, {
                  size: "xl",
                  centered: true,
                  // scrollable: true,
                  backdrop: "static",
                });

                pdfModalRef.componentInstance.title = "Reçu";
                pdfModalRef.componentInstance.pdfSrc = data;

                merge(pdfModalRef.closed, pdfModalRef.dismissed).subscribe({
                  next: (value) => {
                    this.modal.close("Receipt pdf modal closed");
                  },
                });
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
          },
          error: (e) => {
            console.error(e);

            this.toastService.show({
              messages: ["Paiement non enregistré."],
              delay: 10000,
              type: ToastType.Error,
            });
          },
        });
      },
      error: (e) => {
        console.error(e);
      },
    });
  }
}
