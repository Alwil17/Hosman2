import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { calculateExactAge } from "src/app/core/helpers/age-calculator";

@Component({
  selector: "app-patient-form",
  templateUrl: "./patient-form.component.html",
  styleUrls: ["./patient-form.component.scss"],
})
export class PatientFormComponent implements OnInit {
  lastNameControl = new FormControl("", [Validators.required]);
  firstNameControl = new FormControl("", [Validators.required]);
  genderControl = new FormControl("", [Validators.required]);

  dateOfBirthControl = new FormControl("", [Validators.required]);
  ageControl = new FormControl("");
  birthPlaceControl = new FormControl("");

  patientTel1Control = new FormControl("");
  patientEmailControl = new FormControl("", [Validators.email]);
  idTypeControl = new FormControl("");
  idNumberControl = new FormControl("");

  homelandControl = new FormControl("", [Validators.required]);
  professionControl = new FormControl("");
  hasInsuranceControl = new FormControl("", [Validators.required]);

  insuranceControl = new FormControl({ value: "", disabled: true });
  insuranceTypeControl = new FormControl({ value: "", disabled: true });
  insuranceRateControl = new FormControl({ value: "", disabled: true });

  patientEmployerControl = new FormControl("");
  insuranceStartControl = new FormControl("");
  insuranceEndControl = new FormControl("");

  insuranceTel1Control = new FormControl("");
  insuranceTel2Control = new FormControl("");
  insuranceEmailControl = new FormControl("", [Validators.email]);

  patientAddressControl = new FormControl("");
  cityControl = new FormControl("");
  neighborhoodControl = new FormControl("");
  
  patientTel2Control = new FormControl("");
  personToContactControl = new FormControl("");

  generalInfoForm: FormGroup = new FormGroup({});
  insuranceInfoForm: FormGroup = new FormGroup({});
  otherInfoForm: FormGroup = new FormGroup({});

  constructor() {}

  ngOnInit(): void {
    this.generalInfoForm = new FormGroup({
      lastNameControl: this.lastNameControl,
      firstNameControl: this.firstNameControl,
      genderControl: this.genderControl,

      dateOfBirthControl: this.dateOfBirthControl,
      ageControl: this.ageControl,
      birthPlaceControl: this.birthPlaceControl,

      patientTel1Control: this.patientTel1Control,
      patientEmailControl: this.patientEmailControl,
      idTypeControl: this.idTypeControl,
      idNumberControl: this.idNumberControl,

      homelandControl: this.homelandControl,
      professionControl: this.professionControl,
      hasInsuranceControl: this.hasInsuranceControl,
    });

    this.insuranceInfoForm = new FormGroup({
      insuranceControl: this.insuranceControl,
      insuranceTypeControl: this.insuranceTypeControl,
      insuranceRateControl: this.insuranceRateControl,

      patientEmployerControl: this.patientEmployerControl,
      insuranceStartControl: this.insuranceStartControl,
      insuranceEndControl: this.insuranceEndControl,

      insuranceTel1Control: this.insuranceTel1Control,
      insuranceTel2Control: this.insuranceTel2Control,
      insuranceEmailControl: this.insuranceEmailControl,
    });

    this.otherInfoForm = new FormGroup({
      patientAddressControl: this.patientAddressControl,
      cityControl: this.cityControl,
      neighborhoodControl: this.neighborhoodControl,

      patientTel2Control: this.patientTel2Control,
      personToContactControl: this.personToContactControl,
    });

    this.onChanges();
  }

  onChanges() {
    this.dateOfBirthControl.valueChanges.subscribe((value) => {
      if (this.dateOfBirthControl.valid) {
        const dob = calculateExactAge(new Date(value));

        this.ageControl.setValue(dob);
      }
    });

    this.hasInsuranceControl.valueChanges.subscribe((value) => {
      const strValue = value as string;

      if (strValue.startsWith("Non")) {
        this.insuranceControl.clearValidators();
        this.insuranceControl.updateValueAndValidity();
        this.insuranceControl.disable();

        this.insuranceTypeControl.clearValidators();
        this.insuranceTypeControl.updateValueAndValidity();
        this.insuranceTypeControl.disable();

        this.insuranceRateControl.clearValidators();
        this.insuranceRateControl.updateValueAndValidity();
        this.insuranceRateControl.disable();
      } else {
        this.insuranceControl.addValidators([Validators.required]);
        this.insuranceControl.updateValueAndValidity();
        this.insuranceControl.enable();

        this.insuranceTypeControl.addValidators([Validators.required]);
        this.insuranceTypeControl.updateValueAndValidity();
        this.insuranceTypeControl.enable();

        this.insuranceRateControl.addValidators([
          Validators.required,
          Validators.min(0),
          Validators.max(100),
        ]);
        this.insuranceRateControl.updateValueAndValidity();
        this.insuranceRateControl.enable();

        // this.insuranceTag.disabled
      }
    });
  }

  registerPatient() {
    console.log();
  }

  emptyFields() {}
}
