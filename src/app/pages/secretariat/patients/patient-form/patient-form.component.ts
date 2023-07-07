import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { calculateExactAge } from "src/app/core/helpers/age-calculator";

@Component({
  selector: "app-patient-form",
  templateUrl: "./patient-form.component.html",
  styleUrls: ["./patient-form.component.scss"],
})
export class PatientFormComponent implements OnInit {
  titleControl = new FormControl("", [Validators.required]);
  lastNameControl = new FormControl("", [Validators.required]);
  firstNameControl = new FormControl("", [Validators.required]);
  genderControl = new FormControl("", [Validators.required]);
  dateOfBirthControl = new FormControl("", [Validators.required]);
  ageControl = new FormControl("");
  hasInsuranceControl = new FormControl("", [Validators.required]);
  insuranceControl = new FormControl({ value: "", disabled: true });
  insuranceRateControl = new FormControl({ value: "", disabled: true });
  employerControl = new FormControl("");
  homelandControl = new FormControl("", [Validators.required]);
  nationalityControl = new FormControl("");
  professionControl = new FormControl("");
  livingCountryControl = new FormControl("");
  cityControl = new FormControl("");
  neighborhoodControl = new FormControl("");
  phoneNumberControl = new FormControl("");
  personToContactControl = new FormControl("");

  patientForm: FormGroup = new FormGroup({});

  constructor() {}

  ngOnInit(): void {
    this.patientForm = new FormGroup({
      titleControl: this.titleControl,
      lastNameControl: this.lastNameControl,
      firstNameControl: this.firstNameControl,
      genderControl: this.genderControl,
      dateOfBirthControl: this.dateOfBirthControl,
      ageControl: this.ageControl,
      hasInsuranceControl: this.hasInsuranceControl,
      insuranceControl: this.insuranceControl,
      insuranceRateControl: this.insuranceRateControl,
      employerControl: this.employerControl,
      homelandControl: this.homelandControl,
      nationalityControl: this.nationalityControl,
      professionControl: this.professionControl,
      livingCountryControl: this.livingCountryControl,
      cityControl: this.cityControl,
      neighborhoodControl: this.neighborhoodControl,
      phoneNumberControl: this.phoneNumberControl,
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
      console.log(strValue);

      if (strValue.startsWith("Non")) {
        this.insuranceControl.clearValidators();
        this.insuranceControl.updateValueAndValidity();
        this.insuranceControl.disable();

        this.insuranceRateControl.clearValidators();
        this.insuranceRateControl.updateValueAndValidity();
        this.insuranceRateControl.disable();
        console.log("1");
      } else {
        this.insuranceControl.addValidators([Validators.required]);
        this.insuranceControl.updateValueAndValidity();
        this.insuranceControl.enable();

        this.insuranceRateControl.addValidators([
          Validators.required,
          Validators.min(0),
          Validators.max(100),
        ]);
        this.insuranceRateControl.updateValueAndValidity();
        this.insuranceRateControl.enable();
        console.log("2");

        // this.insuranceTag.disabled
      }
    });
  }

  registerPatient() {
    console.log(this.patientForm.value);
  }

  emptyFields() {}
}
