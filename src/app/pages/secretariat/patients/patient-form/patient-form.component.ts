import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { NgbNavChangeEvent } from "@ng-bootstrap/ng-bootstrap";
import { calculateExactAge } from "src/app/helpers/age-calculator";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-patient-form",
  templateUrl: "./patient-form.component.html",
  styleUrls: ["./patient-form.component.scss"],
})
export class PatientFormComponent implements OnInit {
  // General information controls
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

  // Insurance informations control
  insuranceControl = new FormControl({ value: "", disabled: true });
  insuranceTypeControl = new FormControl({ value: "", disabled: true });
  insuranceRateControl = new FormControl({ value: "", disabled: true });

  patientEmployerControl = new FormControl("");
  insuranceStartControl = new FormControl("");
  insuranceEndControl = new FormControl("");

  insuranceTel1Control = new FormControl("");
  insuranceTel2Control = new FormControl("");
  insuranceEmailControl = new FormControl("", [Validators.email]);

  // Other informations controls
  patientAddressControl = new FormControl("");
  cityControl = new FormControl("");
  neighborhoodControl = new FormControl("");

  patientTel2Control = new FormControl("");
  personToContactControl = new FormControl("");

  // Form groups
  generalInfoForm: FormGroup = new FormGroup({});
  insuranceInfoForm: FormGroup = new FormGroup({});
  otherInfoForm: FormGroup = new FormGroup({});

  // Nav steps variables
  activeStep = 1;
  isGeneralInfoFormSubmitted = false;
  isInsuranceInfoFormSubmitted = false;
  isOtherInfoFormSubmitted = false;

  constructor(
    private datePipe: DatePipe,
    private patientService: PatientService,
    private router: Router
  ) {}

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

  onNavChange(changeEvent: NgbNavChangeEvent) {
    if (changeEvent.activeId === 1 && changeEvent.nextId === 2) {
      this.isGeneralInfoFormSubmitted = true;
      if (this.generalInfoForm.invalid) {
        changeEvent.preventDefault();
      } else {
        if (this.hasInsuranceControl.value.startsWith("Non")) {
          changeEvent.preventDefault();

          this.isInsuranceInfoFormSubmitted = true;

          this.activeStep = 3;
        }
      }
    } else if (changeEvent.activeId === 2 && changeEvent.nextId === 3) {
      this.isInsuranceInfoFormSubmitted = true;
      if (this.insuranceInfoForm.invalid) {
        changeEvent.preventDefault();
      }
    } else if (changeEvent.activeId === 3 && changeEvent.nextId === 4) {
      this.isOtherInfoFormSubmitted = true;
      if (this.otherInfoForm.invalid) {
        changeEvent.preventDefault();
      }

      this.generateSummary();
    }

    if (changeEvent.nextId - changeEvent.activeId > 1) {
      changeEvent.preventDefault();
    }

    // If user goes back, set previous submitted to false ?
  }

  stepNavigateFromTo(activeId: number, nextId: number) {
    if (activeId === 1 && nextId === 2) {
      this.isGeneralInfoFormSubmitted = true;
      if (this.generalInfoForm.valid) {
        if (this.hasInsuranceControl.value.startsWith("Non")) {
          this.isInsuranceInfoFormSubmitted = true;

          this.activeStep = 3;
        } else {
          this.activeStep = 2;
        }
      }
    } else if (activeId === 2 && nextId === 3) {
      this.isInsuranceInfoFormSubmitted = true;
      if (this.insuranceInfoForm.valid) {
        this.activeStep = 3;
      }
    } else if (activeId === 3 && nextId === 4) {
      this.isOtherInfoFormSubmitted = true;
      if (this.otherInfoForm.valid) {
        this.activeStep = 4;

        this.generateSummary();
      }
    }
  }

  summary = {
    title: "",
    fullName: "",
    birth: "",
    age: "",
    birthPlace: "",
    profession: "",
    nationality: "",
    insuranceRate: "",
    insurance: "",
    insuranceEnd: "",
    tel1: "",
    tel2: "",
    personToContact: "",
  };

  generateSummary() {
    this.summary = {
      title:
        this.genderControl.value === "Masculin" ? "Monsieur" : "Mademoiselle",
      fullName: this.lastNameControl.value + " " + this.firstNameControl.value,
      birth: this.datePipe.transform(
        this.dateOfBirthControl.value,
        "dd/MM/yyyy"
      )!,
      age: this.ageControl.value as string,
      birthPlace: this.birthPlaceControl.value
        ? this.birthPlaceControl.value
        : "",
      profession: this.professionControl.value
        ? this.professionControl.value
        : "",
      nationality: this.homelandControl.value ? this.homelandControl.value : "",
      insuranceRate: this.insuranceRateControl.value
        ? this.insuranceRateControl.value
        : "",
      insurance: this.insuranceControl.value ? this.insuranceControl.value : "",
      insuranceEnd: this.insuranceEndControl.value
        ? this.datePipe.transform(this.insuranceEndControl.value, "dd/MM/yyyy")!
        : "",

      tel1: this.patientTel1Control.value ? this.patientTel1Control.value : "",
      tel2: this.patientTel2Control.value ? this.patientTel2Control.value : "",

      personToContact: this.personToContactControl.value
        ? this.personToContactControl.value
        : "",
    };
  }

  async registerPatientAndLeave() {
    let patient = {
      id: 0,
      reference: "PAT1",
      nom: this.lastNameControl.value,
      prenoms: this.firstNameControl.value,
      date_naissance: new Date(this.dateOfBirthControl.value),
      sexe: this.genderControl.value,
      lieu_naissance: this.birthPlaceControl.value,
      is_assure: (this.hasInsuranceControl.value as string).startsWith("Non")
        ? false
        : true,
      tel1: this.patientTel1Control.value,
      tel2: this.patientTel1Control.value,
      personToContact: this.personToContactControl.value, // ???
      type_carte: this.idTypeControl.value,
      no_carte: this.idNumberControl.value,
      date_entre: new Date(),
      pays_origine: this.homelandControl.value,
      profession: this.professionControl.value,
      assurance: this.insuranceControl.value,
      employer: this.patientEmployerControl.value,
      adresse: this.patientAddressControl.value,
    };

    this.patientService.registerPatient(patient);

    await this.router.navigateByUrl("secretariat/patients/patient-list");
  }

  async registerPatientAndContinue() {
    let patient = {
      id: 1,
      reference: "PAT1",
      nom: this.lastNameControl.value,
      prenoms: this.firstNameControl.value,
      date_naissance: new Date(this.dateOfBirthControl.value),
      sexe: this.genderControl.value,
      lieu_naissance: this.birthPlaceControl.value,
      is_assure: (this.hasInsuranceControl.value as string).startsWith("Non")
        ? false
        : true,
      tel1: this.patientTel1Control.value,
      tel2: this.patientTel1Control.value,
      personToContact: this.personToContactControl.value, // ???
      type_carte: this.idTypeControl.value,
      no_carte: this.idNumberControl.value,
      date_entre: new Date(),
      pays_origine: this.homelandControl.value,
      profession: this.professionControl.value,
      assurance: this.insuranceControl.value,
      employer: this.patientEmployerControl.value,
      adresse: this.patientAddressControl.value,
    };

    this.patientService.registerPatient(patient);

    await this.router.navigateByUrl("secretariat/patients/patient-activity");
  }
}
