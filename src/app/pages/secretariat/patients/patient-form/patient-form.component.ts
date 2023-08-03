import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { NgbNavChangeEvent } from "@ng-bootstrap/ng-bootstrap";
import { calculateExactAge } from "src/app/helpers/age-calculator";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { Router } from "@angular/router";
import { COUNTRIES } from "src/app/data/secretariat/countries.data";
import { PROFESSIONS } from "src/app/data/secretariat/professions.data";
import { INSURANCES } from "src/app/data/secretariat/insurances.data";
import { EMPLOYERS } from "src/app/data/secretariat/employers.data";
import { ADDRESSES } from "src/app/data/secretariat/addresses.data";
import { CITIES } from "src/app/data/secretariat/cities.data";
import { NEIGHBORHOODS } from "src/app/data/secretariat/neighborhoods.data";
import { INSURANCE_TYPES } from "src/app/data/secretariat/insurance-types.data";
import { HAS_INSURANCES } from "src/app/data/secretariat/has-insurance.data";
import { IPatient } from "src/app/models/secretariat/patients/patient.model";
import { IPatientInsurance } from "src/app/models/secretariat/patients/patient-insurance.model";
import { IInsurance } from "src/app/models/secretariat/patients/insurance.model";
import { HasInsuranceCode } from "src/app/models/secretariat/patients/has-insurance.model";

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

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  // Nav steps variables
  activeStep = 1;
  isGeneralInfoFormSubmitted = false;
  isInsuranceInfoFormSubmitted = false;
  isOtherInfoFormSubmitted = false;

  // Form selects data
  genders = [
    { id: 1, text: "Masculin" },
    { id: 2, text: "Féminin" },
  ];
  idTypes = [
    { id: 1, text: "CNI" },
    { id: 2, text: "Passeport" },
    { id: 3, text: "Autres" },
  ];
  hasInsurances = HAS_INSURANCES.map((hasInsurance) => ({
    id: hasInsurance.id,
    text: hasInsurance.code,
  }));
  countries = COUNTRIES.map((country) => ({
    id: country.id,
    text: country.nom,
  }));
  professions = PROFESSIONS.map((profession) => ({
    id: profession.id,
    text: profession.nom,
  }));
  insurances = INSURANCES.map((insurance) => ({
    id: insurance.id,
    text: insurance.nom,
  }));
  insuranceTypes = INSURANCE_TYPES.map((insuranceType) => ({
    id: insuranceType.id,
    text: insuranceType.libelle,
  }));
  employers = EMPLOYERS.map((employer) => ({
    id: employer.id,
    text: employer.nom,
  }));
  cities = CITIES.map((city) => ({
    id: city.id,
    text: city.nom,
  }));
  neighborhoods = NEIGHBORHOODS.map((neighborhood) => ({
    id: neighborhood.id,
    text: neighborhood.nom,
  }));

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
      if (value < 3) {
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
        if (this.hasInsuranceControl.value < 3) {
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
        if (this.hasInsuranceControl.value < 3) {
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

  getPatientFormData() {
    console.log(this.insuranceControl.value);

    const computedProperties = {
      // id: this.patientService.allPatients.length + 1,
      // reference: "PAT" + (this.patientService.allPatients.length + 1),
      hasInsurance: this.hasInsuranceControl.value < 3 ? false : true,
    };

    const selectInfos = {
      gender: this.genders[this.genderControl.value - 1].text,
      idType: this.idTypes[this.idTypeControl.value - 1]
        ? this.idTypes[this.idTypeControl.value - 1].text
        : undefined,
      // hasInsurance: this.hasInsurances[this.hasInsuranceControl.value - 1].text,
      homeland: {
        id: this.homelandControl.value,
        nom: "",
        nationalite: "",
      },
      profession: {
        id: this.professionControl.value,
        nom: "",
      },
      // insurance: {
      //   id: this.insuranceControl.value,
      //   reference: "",
      //   nom: "",
      //   type_assurance: {
      //     id: this.insuranceTypeControl.value,
      //     libelle: "",
      //   },
      // },
      insuranceType: {
        id: this.insuranceTypeControl.value,
        libelle: "",
      },
      employer: {
        id: this.patientEmployerControl.value,
        nom: "",
      },
      city: {
        id: this.cityControl.value,
        nom: "",
      },
      neighborhood: {
        id: this.neighborhoodControl.value,
        nom: "",
      },
      hasInsurance: {
        id: this.hasInsuranceControl.value,
        code: HasInsuranceCode.NO_LOCAL,
      },
    };

    const patient: IPatient = {
      id: -1,
      reference: "",
      nom: this.lastNameControl.value,
      prenoms: this.firstNameControl.value,
      date_naissance: new Date(this.dateOfBirthControl.value),
      sexe: selectInfos.gender,
      lieu_naissance: this.birthPlaceControl.value,
      is_assure: computedProperties.hasInsurance,
      tel1: this.patientTel1Control.value,
      tel2: this.patientTel2Control.value,
      personne_a_prevenir: this.personToContactControl.value, // ???
      type_carte: selectInfos.idType,
      no_carte: this.idNumberControl.value,
      date_entre: new Date(),
      pays_origine: selectInfos.homeland,
      profession: selectInfos.profession,
      employeur: selectInfos.employer,
      adresse: {
        id: 1,
        ville: selectInfos.city,
        quartier: selectInfos.neighborhood,
        details: this.patientAddressControl.value,
      },
      type_patient: selectInfos.hasInsurance,
    };
    console.log(patient);

    const insurance: IInsurance = {
      id: this.insuranceControl.value,
      reference: "",
      nom: "",
      type_assurance: {
        id: this.insuranceTypeControl.value,
        libelle: "",
      },
    };

    const patientInsurance = this.getPatientInsuranceFormData();

    return { patient, insurance, patientInsurance };
  }

  getPatientInsuranceFormData() {
    const patientInsurance: IPatientInsurance = {
      id: -1,
      patient_id: -1,
      assurance_id: -1,
      taux: this.insuranceRateControl.value,
      date_debut: this.insuranceStartControl.value
        ? this.insuranceStartControl.value
        : null,
      date_expiration: this.insuranceEndControl.value
        ? this.insuranceEndControl.value
        : null,
    };

    return patientInsurance;
  }

  generateSummary() {
    const patient = this.getPatientFormData().patient;

    const selectedInsurance = this.insurances.find(
      (insurance) => this.insuranceControl.value == insurance.id
    );

    this.summary = {
      title: patient.sexe === "Masculin" ? "Monsieur" : "Mademoiselle",
      fullName: patient.nom + " " + patient.prenoms,
      birth: this.datePipe.transform(patient.date_naissance, "dd/MM/yyyy")!,
      age: this.ageControl.value as string,
      birthPlace: patient.lieu_naissance ? patient.lieu_naissance : "",
      profession: patient.profession ? patient.profession.nom : "",
      nationality: patient.pays_origine ? patient.pays_origine.nationalite : "",
      insuranceRate: this.insuranceRateControl.value
        ? this.insuranceRateControl.value
        : "",
      insurance: selectedInsurance != null ? selectedInsurance!.text : "",
      insuranceEnd: this.insuranceEndControl.value
        ? this.datePipe.transform(this.insuranceEndControl.value, "dd/MM/yyyy")!
        : "",

      tel1: patient.tel1 ? patient.tel1 : "",
      tel2: patient.tel2 ? patient.tel2 : "",

      personToContact: patient.personne_a_prevenir
        ? patient.personne_a_prevenir
        : "",
    };
  }

  async registerPatientAndLeave() {
    const patientData = this.getPatientFormData();

    this.patientService.registerPatient(
      patientData.patient,
      patientData.insurance,
      patientData.patientInsurance
    );

    await this.router.navigateByUrl("secretariat/patients/patient-list");
  }

  async registerPatientAndContinue() {
    const patientData = this.getPatientFormData();

    this.patientService.registerPatient(
      patientData.patient,
      patientData.insurance,
      patientData.patientInsurance
    );

    await this.router.navigateByUrl("secretariat/patients/patient-activity");
  }
}
