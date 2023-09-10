import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { parseIntOrZero } from "src/app/helpers/parsers";
import { CollectionRequest } from "src/app/models/secretariat/activities/requests/collection-request.model";
import { CollectionService } from "src/app/services/secretariat/activities/collection.service";

@Component({
  selector: "app-collections",
  templateUrl: "./collections.component.html",
  styleUrls: ["./collections.component.scss"],
})
export class CollectionsComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  collectionForm = new FormGroup({});

  isCollectionFormSubmitted = false;

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  dateOfCollectionControl = new FormControl(this.today, [Validators.required]);

  paymentCheckCashControl = new FormControl(false);
  paymentCheckCardControl = new FormControl(false);
  paymentCheckChequeControl = new FormControl(false);

  paymentCashControl = new FormControl({ value: "0", disabled: true });
  paymentCardControl = new FormControl({ value: "0", disabled: true });
  paymentChequeControl = new FormControl({ value: "0", disabled: true });

  constructor(private collectionService: CollectionService) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Activités" },
      { label: "Encaissements", active: true },
    ];

    this.collectionForm = new FormGroup({
      dateOfCollectionControl: this.dateOfCollectionControl,

      paymentCheckCashControl: this.paymentCheckCashControl,
      paymentCheckCardControl: this.paymentCheckCardControl,
      paymentCheckChequeControl: this.paymentCheckChequeControl,

      paymentCashControl: this.paymentCashControl,
      paymentCardControl: this.paymentCardControl,
      paymentChequeControl: this.paymentChequeControl,
    });

    this.onChanges();
  }

  onChanges() {
    this.paymentCheckCashControl.valueChanges.subscribe((value) => {
      if (value == true) {
        this.paymentCashControl.enable();
        this.paymentCashControl.setValue("");
      } else {
        this.paymentCashControl.disable();
        this.paymentCashControl.setValue("0");
      }
    });
    this.paymentCheckCardControl.valueChanges.subscribe((value) => {
      if (value == true) {
        this.paymentCardControl.enable();
        this.paymentCardControl.setValue("");
      } else {
        this.paymentCardControl.disable();
        this.paymentCardControl.setValue("0");
      }
    });
    this.paymentCheckChequeControl.valueChanges.subscribe((value) => {
      if (value == true) {
        this.paymentChequeControl.enable();
        this.paymentChequeControl.setValue("");
      } else {
        this.paymentChequeControl.disable();
        this.paymentChequeControl.setValue("0");
      }
    });
  }

  registerCollection() {
    const cashAmount = parseIntOrZero(this.paymentCashControl.value);
    const cardAmount = parseIntOrZero(this.paymentCardControl.value);
    const chequeAmount = parseIntOrZero(this.paymentChequeControl.value);

    const paymentModes = [];

    if (cashAmount !== 0) {
      paymentModes.push({
        mode_payement_id: 2,
        montant: cashAmount,
      });
    }
    if (cardAmount !== 0) {
      paymentModes.push({
        mode_payement_id: 3,
        montant: cardAmount,
      });
    }
    if (chequeAmount !== 0) {
      paymentModes.push({
        mode_payement_id: 4,
        montant: chequeAmount,
      });
    }

    const collection = new CollectionRequest({
      provenance: "Hospitalisation Comptabilité",
      date_encaissement: new Date(this.dateOfCollectionControl.value),
      mode_payements: paymentModes
      // [
      //   {
      //     mode_payement_id: 2,
      //     montant: parseIntOrZero(this.paymentCashControl.value),
      //   },
      //   {
      //     mode_payement_id: 3,
      //     montant: parseIntOrZero(this.paymentCardControl.value),
      //   },
      //   {
      //     mode_payement_id: 4,
      //     montant: parseIntOrZero(this.paymentChequeControl.value),
      //   },
      // ],
    });

    this.collectionService.create(collection).subscribe({
      next: (data) => {
        console.log(data);
      },
      error: (e) => console.error(e),
    });
  }
}
