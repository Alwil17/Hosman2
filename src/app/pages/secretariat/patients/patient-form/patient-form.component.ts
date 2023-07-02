import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";

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
  insuranceControl = new FormControl("", [Validators.required]);
  insuranceRateControl = new FormControl("", [
    Validators.required,
    Validators.min(0),
    Validators.max(100),
  ]);
  employerControl = new FormControl("");
  homelandControl = new FormControl("", [Validators.required]);
  nationalityControl = new FormControl("");
  professionControl = new FormControl("");
  livingCountryControl = new FormControl("");
  cityControl = new FormControl("");
  neighborhoodControl = new FormControl("");
  phoneNumberControl = new FormControl("");
  personToContactControl = new FormControl("");

  constructor() {}

  ngOnInit(): void {}

  patientForm = new FormGroup({
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

  registerPatient() {}
}
