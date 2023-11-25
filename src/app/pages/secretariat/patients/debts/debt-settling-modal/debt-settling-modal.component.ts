import {
  Component,
  ElementRef,
  Input,
  OnInit,
  QueryList,
  ViewChild,
  ViewChildren,
} from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { WarningMessages } from "src/app/helpers/messages";
import { parseIntOrZero } from "src/app/helpers/parsers";
import { PaymentModeCode } from "src/app/models/enums/payment-mode.enum";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Debt } from "src/app/models/secretariat/patients/debt.model";
import { DebtSettlingRequest } from "src/app/models/secretariat/patients/requests/debt-request.model";
import { DebtService } from "src/app/services/secretariat/patients/debt.service";
import { PaymentModeService } from "src/app/services/secretariat/patients/payment-mode.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { InputComponent } from "src/app/shared/form-inputs/input/input.component";

@Component({
  selector: "app-debt-settling-modal",
  templateUrl: "./debt-settling-modal.component.html",
  styleUrls: ["./debt-settling-modal.component.scss"],
})
export class DebtSettlingModalComponent implements OnInit {
  @Input()
  debt!: Debt;

  @ViewChildren(InputComponent)
  inputFields!: QueryList<InputComponent>;

  @ViewChild("paymentCashField", { read: ElementRef })
  paymentCashField!: ElementRef;
  @ViewChild("paymentCardField", { read: ElementRef })
  paymentCardField!: ElementRef;
  @ViewChild("paymentChequeField", { read: ElementRef })
  paymentChequeField!: ElementRef;

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  debtSettlingForm!: FormGroup;

  isDebtSettlingFormSubmitted = false;

  // Person to contact controls
  settlingDate = new FormControl(this.today, [Validators.required]);

  paymentCheckCashControl = new FormControl(false);
  paymentCheckCardControl = new FormControl(false);
  paymentCheckChequeControl = new FormControl(false);

  paymentCashControl = new FormControl({ value: "0", disabled: true });
  paymentCardControl = new FormControl({ value: "0", disabled: true });
  paymentChequeControl = new FormControl({ value: "0", disabled: true });

  remainder = 0;
  total = 0;

  constructor(
    public modal: NgbActiveModal,
    private debtService: DebtService,
    private paymentModeService: PaymentModeService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.debtSettlingForm = new FormGroup({
      settlingDate: this.settlingDate,

      paymentCheckCashControl: this.paymentCheckCashControl,
      paymentCheckCardControl: this.paymentCheckCashControl,
      paymentCheckChequeControl: this.paymentCheckCashControl,

      paymentCashControl: this.paymentCashControl,
      paymentCardControl: this.paymentCardControl,
      paymentChequeControl: this.paymentChequeControl,
    });

    this.onFormInputsChanges();
  }

  onFormInputsChanges() {
    this.paymentCheckCashControl.valueChanges.subscribe((value) => {
      if (value == true) {
        this.paymentCashControl.enable();
        this.paymentCashControl.setValue("");
        console.log("HERE");

        setTimeout(() => {
          this.paymentCashField.nativeElement.querySelector("input").focus();
        });
      } else {
        this.paymentCashControl.disable();
        this.paymentCashControl.setValue("0");
      }
    });
    this.paymentCheckCardControl.valueChanges.subscribe((value) => {
      if (value == true) {
        this.paymentCardControl.enable();
        this.paymentCardControl.setValue("");

        setTimeout(() => {
          this.paymentCardField.nativeElement.querySelector("input").focus();
        });
      } else {
        this.paymentCardControl.disable();
        this.paymentCardControl.setValue("0");
      }
    });
    this.paymentCheckChequeControl.valueChanges.subscribe((value) => {
      if (value == true) {
        this.paymentChequeControl.enable();
        this.paymentChequeControl.setValue("");

        setTimeout(() => {
          this.paymentChequeField.nativeElement.querySelector("input").focus();
        });
      } else {
        this.paymentChequeControl.disable();
        this.paymentChequeControl.setValue("0");
      }
    });

    this.paymentCashControl.valueChanges.subscribe((value) => {
      this.calculate();
    });

    this.paymentCardControl.valueChanges.subscribe((value) => {
      this.calculate();
    });

    this.paymentChequeControl.valueChanges.subscribe((value) => {
      this.calculate();
    });
  }

  calculate() {
    this.total =
      parseIntOrZero(this.paymentCashControl.value) +
      parseIntOrZero(this.paymentCardControl.value) +
      parseIntOrZero(this.paymentChequeControl.value);

    const baseRemainder = this.total - this.debt.montant;
    this.remainder = baseRemainder < 0 ? 0 : baseRemainder;
  }

  getInvalidFields() {
    const invalidInputs: string[] = [];
    this.inputFields.forEach((input) => {
      if (input.control.invalid) {
        invalidInputs.push("- " + input.label);
      }
    });

    // const invalidSelects: string[] = [];
    // this.selectFields.forEach((select) => {
    //   if (select.control.invalid) {
    //     invalidSelects.push("- " + select.label);
    //   }
    // });

    let notificationMessages: string[] = [];
    if (invalidInputs.length > 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_INPUT_FIELDS,
        ...invalidInputs
      );
    }

    // if (notificationMessages.length !== 0) {
    //   notificationMessages.push("");
    // }

    // if (invalidSelects.length !== 0) {
    //   notificationMessages.push(
    //     WarningMessages.MANDATORY_SELECT_FIELDS,
    //     ...invalidSelects
    //   );
    // }

    return notificationMessages;
  }

  submit() {
    this.isDebtSettlingFormSubmitted = true;

    if (this.debtSettlingForm.invalid) {
      const notificationMessages = this.getInvalidFields();

      this.toastService.show({
        messages: notificationMessages,
        type: ToastType.Warning,
      });

      return;
    }

    // PAYMENT REGISTRATION
    const cashAmount = parseIntOrZero(this.paymentCashControl.value);
    const cardAmount = parseIntOrZero(this.paymentCardControl.value);
    const chequeAmount = parseIntOrZero(this.paymentChequeControl.value);

    const paymentModes: {
      creance_id: number;
      mode_payement_id: number;
      montant: number;
      date_depot: Date;
    }[] = [];

    const settlingDate = new Date(this.settlingDate.value);

    this.paymentModeService.getAll().subscribe({
      next: (data) => {
        // Getting payment modes
        if (cashAmount !== 0) {
          paymentModes.push({
            creance_id: this.debt.id,
            mode_payement_id: data.find(
              (value) => PaymentModeCode.CASH == value.slug
            )!.id,
            montant: cashAmount,
            date_depot: settlingDate,
          });
        }
        if (cardAmount !== 0) {
          paymentModes.push({
            creance_id: this.debt.id,
            mode_payement_id: data.find(
              (value) => PaymentModeCode.CARD == value.slug
            )!.id,
            montant: cardAmount,
            date_depot: settlingDate,
          });
        }
        if (chequeAmount !== 0) {
          paymentModes.push({
            creance_id: this.debt.id,
            mode_payement_id: data.find(
              (value) => PaymentModeCode.CHEQUE == value.slug
            )!.id,
            montant: chequeAmount,
            date_depot: settlingDate,
          });
        }

        console.log(JSON.stringify(paymentModes, null, 2));

        const debtSettlingRequest = new DebtSettlingRequest({
          modes: paymentModes,
        });

        console.log(JSON.stringify(debtSettlingRequest, null, 2));

        this.debtService.settle(this.debt.id, debtSettlingRequest).subscribe({
          next: () => {
            this.toastService.show({
              messages: ["Paiement enregistré."],
              type: ToastType.Success,
            });

            this.modal.close();
          },
          error: (e) => {
            console.error(e);

            this.toastService.show({
              messages: ["Une erreur s'est produite."],
              delay: 10000,
              type: ToastType.Error,
            });
          },
        });
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Une erreur s'est produite."],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }
}
