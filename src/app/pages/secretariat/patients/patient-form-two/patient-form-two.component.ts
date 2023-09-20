import { DatePipe } from "@angular/common";
import {
  Component,
  Input,
  OnInit,
  QueryList,
  ViewChildren,
} from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { NgbModal, NgbNavChangeEvent } from "@ng-bootstrap/ng-bootstrap";
import { calculateExactAge } from "src/app/helpers/age-calculator";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { COUNTRIES } from "src/app/data/secretariat/countries.data";
import { PROFESSIONS } from "src/app/data/secretariat/professions.data";
import { INSURANCES } from "src/app/data/secretariat/insurances.data";
import { EMPLOYERS } from "src/app/data/secretariat/employers.data";
import { CITIES } from "src/app/data/secretariat/cities.data";
import { NEIGHBORHOODS } from "src/app/data/secretariat/neighborhoods.data";
import { INSURANCE_TYPES } from "src/app/data/secretariat/insurance-types.data";
import { HAS_INSURANCES } from "src/app/data/secretariat/has-insurance.data";
import {
  IPatient,
  Patient,
} from "src/app/models/secretariat/patients/patient.model";
import { PatientInsurance } from "src/app/models/secretariat/patients/patient-insurance.model";
import { Insurance } from "src/app/models/secretariat/patients/insurance.model";
import { HasInsuranceCode } from "src/app/models/secretariat/patients/has-insurance.model";
import { SecretariatRouterService } from "src/app/services/secretariat/router/secretariat-router.service";
import { PersonToContactFormComponent } from "./person-to-contact-form/person-to-contact-form.component";
import { PersonToContactRequest } from "src/app/models/secretariat/patients/requests/person-to-contact-request.model";
import {
  IPatientRequest,
  PatientRequest,
} from "src/app/models/secretariat/patients/requests/patient-request.model";
import { AddressRequest } from "src/app/models/secretariat/patients/requests/address-request.model";
import { InsuranceRequest } from "src/app/models/secretariat/patients/requests/insurance-request.model";
import { PatientAddressFormComponent } from "./patient-address-form/patient-address-form.component";
import { InsuranceService } from "src/app/services/secretariat/patients/insurance.service";
import { SelectOption } from "src/app/models/extras/select.model";
import { CountryService } from "src/app/services/secretariat/patients/country.service";
import { ProfessionService } from "src/app/services/secretariat/patients/profession.service";
import { EmployerService } from "src/app/services/secretariat/patients/employer.service";
import { InsuranceTypeService } from "src/app/services/secretariat/patients/insurance-type.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { ProfessionRequest } from "src/app/models/secretariat/patients/requests/profession-request.model";
import { EmployerRequest } from "src/app/models/secretariat/patients/requests/employer-request.model";
import { CityRequest } from "src/app/models/secretariat/patients/requests/city-request.model";
import { NeighborhoodRequest } from "src/app/models/secretariat/patients/requests/neighborhood-request.model";
import { InputComponent } from "src/app/shared/form-inputs/input/input.component";
import { SelectComponent } from "src/app/shared/form-inputs/select/select.component";
import { WarningMessages } from "src/app/helpers/messages";

@Component({
  selector: "app-patient-form-two",
  templateUrl: "./patient-form-two.component.html",
  styleUrls: ["./patient-form-two.component.scss"],
})
export class PatientFormTwoComponent implements OnInit {
  // @Input()
  // patientInfos = {
  //   patient: Patient.emptyPatient(),
  //   insurance: Insurance.emptyInsurance(),
  //   patientInsurance: {
  //     id: -1,
  //     patient_id: -1,
  //     assurance_id: -1,
  //     taux: 0,
  //     date_expiration: new Date(),
  //   },
  // };

  lastNameControl = new FormControl("", [Validators.required]);
  firstNameControl = new FormControl("", [Validators.required]);
  genderControl = new FormControl(null, [Validators.required]);

  dateOfBirthControl = new FormControl("", [Validators.required]);
  ageControl = new FormControl({ value: "", disabled: true });
  birthPlaceControl = new FormControl(null);

  patientTel1Control = new FormControl("", [Validators.required]);
  patientEmailControl = new FormControl("", [Validators.email]);
  idTypeControl = new FormControl(null);
  idNumberControl = new FormControl("");

  homelandControl = new FormControl(null, [Validators.required]);
  nationalityControl = new FormControl(null);
  professionControl = new FormControl(null);
  hasInsuranceControl = new FormControl(null, [Validators.required]);

  insuranceControl = new FormControl({ value: null, disabled: true });
  insuranceTypeControl = new FormControl({ value: null, disabled: true });
  insuranceRateControl = new FormControl({ value: "", disabled: true });

  patientEmployerControl = new FormControl(null);
  insuranceStartControl = new FormControl("");
  insuranceEndControl = new FormControl("");

  insuranceTel1Control = new FormControl("");
  insuranceTel2Control = new FormControl("");
  insuranceEmailControl = new FormControl("", [Validators.email]);

  patientAddressControl = new FormControl("", [Validators.required]);
  // cityControl = new FormControl("");
  // neighborhoodControl = new FormControl("");

  patientTel2Control = new FormControl("");
  personToContactControl = new FormControl("", [Validators.required]);

  // Form groups
  patientInfoForm: FormGroup = new FormGroup({});
  // personToContactForm: FormGroup = new FormGroup({});

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  isPatientInfoFormSubmitted = false;

  // Form selects data
  insurances!: SelectOption[];
  insuranceTypes!: SelectOption[];
  countries!: SelectOption[];
  nationalities!: SelectOption[];
  professions!: SelectOption[];
  employers!: SelectOption[];
  genders = [
    { id: 1, text: "Masculin", short: "M" },
    { id: 2, text: "Féminin", short: "F" },
  ];
  idTypes = [
    { id: 1, text: "CNI" },
    { id: 2, text: "Passeport" },
    { id: 3, text: "Autres" },
  ];
  hasInsurances = HAS_INSURANCES.map((hasInsurance) => ({
    id: hasInsurance.code,
    text: hasInsurance.text,
  }));

  @ViewChildren(InputComponent)
  inputFields!: QueryList<InputComponent>;

  @ViewChildren(SelectComponent)
  selectFields!: QueryList<SelectComponent>;

  constructor(
    private patientService: PatientService,
    private secretariatRouter: SecretariatRouterService,
    private modalService: NgbModal,
    private insuranceService: InsuranceService,
    private insuranceTypeService: InsuranceTypeService,
    private countryService: CountryService,
    private professionService: ProfessionService,
    private employerService: EmployerService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.patientInfoForm = new FormGroup({
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
      nationalityControl: this.nationalityControl,
      professionControl: this.professionControl,
      hasInsuranceControl: this.hasInsuranceControl,

      insuranceControl: this.insuranceControl,
      insuranceTypeControl: this.insuranceTypeControl,
      insuranceRateControl: this.insuranceRateControl,

      patientEmployerControl: this.patientEmployerControl,
      insuranceStartControl: this.insuranceStartControl,
      insuranceEndControl: this.insuranceEndControl,

      insuranceTel1Control: this.insuranceTel1Control,
      insuranceTel2Control: this.insuranceTel2Control,
      insuranceEmailControl: this.insuranceEmailControl,

      patientAddressControl: this.patientAddressControl,
      // cityControl: this.cityControl,
      // neighborhoodControl: this.neighborhoodControl,

      patientTel2Control: this.patientTel2Control,
      personToContactControl: this.personToContactControl,
    });

    this.onChanges();

    this.fetchSelectData();
  }

  isEmployerMandatory = false;
  onChanges() {
    this.dateOfBirthControl.valueChanges.subscribe((value) => {
      if (this.dateOfBirthControl.valid) {
        const dob = calculateExactAge(new Date(value));

        this.ageControl.setValue(dob);
      }
    });

    this.hasInsuranceControl.valueChanges.subscribe((value) => {
      if (!value) {
        return;
      }

      if (value.id < 2) {
        this.insuranceControl.clearValidators();
        this.insuranceControl.updateValueAndValidity();
        // this.insuranceControl.setValue(null);
        this.insuranceControl.disable();

        this.insuranceTypeControl.clearValidators();
        this.insuranceTypeControl.updateValueAndValidity();
        // this.insuranceTypeControl.setValue(null);
        this.insuranceTypeControl.disable();

        this.insuranceRateControl.clearValidators();
        this.insuranceRateControl.updateValueAndValidity();
        // this.insuranceRateControl.setValue("");
        this.insuranceRateControl.disable();

        this.patientEmployerControl.clearValidators();
        this.patientEmployerControl.updateValueAndValidity();
        // this.patientEmployerControl.setValue("");
        // this.patientEmployerControl.disable();
        this.isEmployerMandatory = false;
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

        this.patientEmployerControl.addValidators([Validators.required]);
        this.patientEmployerControl.updateValueAndValidity();
        this.patientEmployerControl.enable();
        this.isEmployerMandatory = true;
      }
    });

    this.insuranceControl.valueChanges.subscribe((value) => {
      if (!value) {
        return;
      }

      const selectedIns = this.insuranceService.insurances.find(
        (insurance) => value.id == insurance.id
      );

      const selectedInsuranceType = this.insuranceTypes.find(
        (insuranceType) => selectedIns?.type_assurance.id == insuranceType.id
      );

      this.insuranceTypeControl.setValue(selectedInsuranceType);
    });

    this.homelandControl.valueChanges.subscribe((value) => {
      if (value) {
        this.nationalityControl.setValue(
          this.nationalities.find((nationality) => value.id == nationality.id)
        );
      }
    });

    // this.fetchSelectData();
  }

  fetchSelectData() {
    this.insuranceService.getAll().subscribe({
      next: (data) => {
        this.insurances = data.map((insurance) => ({
          id: insurance.id,
          text: insurance.nom,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    });

    this.insuranceTypeService.getAll().subscribe({
      next: (data) => {
        this.insuranceTypes = data.map((insuranceType) => ({
          id: insuranceType.id,
          text: insuranceType.nom,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    });

    this.countryService.getAll().subscribe({
      next: (data) => {
        this.countries = data.map((country) => ({
          id: country.id,
          text: country.nom,
        }));

        this.nationalities = data.map((country) => ({
          id: country.id,
          text: country.nationalite,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    });

    this.professionService.getAll().subscribe({
      next: (data) => {
        this.professions = data.map((profession) => ({
          id: profession.id,
          text: profession.denomination,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    });

    this.employerService.getAll().subscribe({
      next: (data) => {
        this.employers = data.map((employer) => ({
          id: employer.id,
          text: employer.nom,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  patientAddressData = new AddressRequest(
    new CityRequest(""),
    new NeighborhoodRequest("")
  );

  onPatientAddressClick() {
    const patientAddressModal = this.modalService.open(
      PatientAddressFormComponent,
      {
        size: "lg",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );
    console.log(this.patientAddressData);

    patientAddressModal.componentInstance.address = this.patientAddressData;

    patientAddressModal.componentInstance.formData.subscribe(
      (formData: AddressRequest) => {
        this.patientAddressData = new AddressRequest(
          formData.ville,
          formData.quartier,
          formData.rue,
          formData.bp,
          formData.arrondissement,
          formData.no_maison
        );

        patientAddressModal.componentInstance.cityAndNeighborhood.subscribe(
          (cityAndNeighborhood: any) => {
            this.patientAddressControl.setValue(
              cityAndNeighborhood.city +
                ", " +
                cityAndNeighborhood.neighborhood +
                ", " +
                (formData.rue ?? "--- ") +
                ", " +
                (formData.bp ?? "--- ") +
                ", " +
                (formData.arrondissement ?? "--- ") +
                ", " +
                (formData.no_maison
                  ? "Maison n° " + formData.no_maison
                  : "--- ")
            );
          }
        );

        patientAddressModal.close();
      }
    );
  }

  personToContactData = new PersonToContactRequest("", "", "", "");

  onPersonToContactClick() {
    const personToContactModal = this.modalService.open(
      PersonToContactFormComponent,
      {
        size: "lg",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );
    console.log(this.personToContactData);

    personToContactModal.componentInstance.data = this.personToContactData;

    personToContactModal.componentInstance.formData.subscribe(
      (formData: PersonToContactRequest) => {
        this.personToContactData = new PersonToContactRequest(
          formData.nom,
          formData.prenoms,
          formData.tel,
          formData.adresse
        );

        this.personToContactControl.setValue(
          formData.nom +
            ", " +
            formData.prenoms +
            ", " +
            formData.tel +
            ", " +
            formData.adresse
        );

        personToContactModal.close();
      }
    );
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

    // const computedProperties = {
    //   // id: this.patientService.allPatients.length + 1,
    //   // reference: "PAT" + (this.patientService.allPatients.length + 1),
    //   hasInsurance: this.hasInsuranceControl.value < 3 ? false : true,
    // };

    const selectInfos = {
      gender: this.genders[this.genderControl.value.id - 1].short,
      idType: this.idTypeControl.value
        ? this.idTypes[this.idTypeControl.value.id - 1]
          ? this.idTypes[this.idTypeControl.value.id - 1].text
          : undefined
        : "",
      // hasInsurance: this.hasInsurances[this.hasInsuranceControl.value - 1].text,
      // homeland: {
      //   id: this.homelandControl.value,
      //   nom: "",
      //   nationalite: "",
      // },
      // nationality: {
      //   id: this.nationalityControl.value,
      //   nom: "",
      //   nationalite: "",
      // },
      // profession: {
      //   id: this.professionControl.value,
      //   denomination: "",
      // },
      // insurance: {
      //   id: this.insuranceControl.value,
      //   reference: "",
      //   nom: "",
      //   type_assurance: {
      //     id: this.insuranceTypeControl.value,
      //     libelle: "",
      //   },
      // },
      // insuranceType: {
      //   id: 1, // this.insuranceTypeControl.value,
      //   nom: "",
      // },
      // employer: {
      //   id: this.patientEmployerControl.value,
      //   nom: "",
      // },
      // city: {
      //   id: this.cityControl.value,
      //   nom: "",
      // },
      // neighborhood: {
      //   id: this.neighborhoodControl.value,
      //   nom: "",
      // },
      // hasInsurance: {
      //   id: this.hasInsuranceControl.value,
      //   code: HasInsuranceCode.NO_LOCAL,
      // },
    };

    const profession: ProfessionRequest | undefined = this.professionControl
      .value
      ? {
          id: this.professionControl.value.id,
          denomination: this.professionControl.value.text,
        }
      : undefined;

    const employer: EmployerRequest | undefined = this.patientEmployerControl
      .value
      ? {
          id: this.patientEmployerControl.value.id,
          nom: this.patientEmployerControl.value.text,
        }
      : undefined;

    const insurance: InsuranceRequest | undefined = this.insuranceControl.value
      ? {
          id: this.insuranceControl.value.id,
          nom: this.insuranceControl.value.text,
          type_assurance_id: this.insuranceTypeControl.value.id, // this.insuranceTypeControl.value,
        }
      : undefined;

    const patientInsurance = this.getPatientInsuranceFormData();

    const patient: IPatientRequest = {
      nom: this.lastNameControl.value,
      prenoms: this.firstNameControl.value,
      date_naissance: new Date(this.dateOfBirthControl.value),
      sexe: selectInfos.gender,
      lieu_naissance: this.birthPlaceControl.value,
      is_assure: this.hasInsuranceControl.value.id,
      tel1: this.patientTel1Control.value,
      tel2: this.patientTel2Control.value,
      personne_a_prevenir: this.personToContactData,
      type_piece: selectInfos.idType,
      no_piece: this.idNumberControl.value,
      date_ajout: new Date(),
      pays_origine_id: this.homelandControl.value.id,
      profession: profession,
      employeur: employer,
      nationalite_id: this.nationalityControl.value
        ? this.nationalityControl.value.id
        : undefined,
      adresse: this.patientAddressData,
      assurance: insurance,
      taux_assurance: patientInsurance.taux,
      date_debut_assurance: patientInsurance.date_debut,
      date_fin_assurance: patientInsurance.date_fin,
    };
    console.log(patient);

    return new PatientRequest(patient);
  }

  getPatientInsuranceFormData() {
    const patientInsurance: PatientInsurance = {
      taux: this.insuranceRateControl.value,
      date_debut: this.insuranceStartControl.value
        ? this.insuranceStartControl.value
        : null,
      date_fin: this.insuranceEndControl.value
        ? this.insuranceEndControl.value
        : null,
    };

    return patientInsurance;
  }

  getInvalidFields() {
    const invalidInputs: string[] = [];
    this.inputFields.forEach((input) => {
      if (input.control.invalid) {
        invalidInputs.push("- " + input.label);
      }
    });

    const invalidSelects: string[] = [];
    this.selectFields.forEach((select) => {
      if (select.control.invalid) {
        invalidSelects.push("- " + select.label);
      }
    });

    let notificationMessages: string[] = [];
    if (invalidInputs.length !== 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_INPUT_FIELDS,
        ...invalidInputs
      );
    }

    if (notificationMessages.length !== 0) {
      notificationMessages.push("");
    }

    if (invalidSelects.length !== 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_SELECT_FIELDS,
        ...invalidSelects
      );
    }

    return notificationMessages;
  }

  registerPatientAndLeave() {
    this.isPatientInfoFormSubmitted = true;

    if (this.patientInfoForm.invalid) {
      const notificationMessages = this.getInvalidFields();

      this.toastService.show({
        messages: notificationMessages,
        type: ToastType.Warning,
      });

      return;
    }

    const patientData = this.getPatientFormData();

    console.log(JSON.stringify(patientData));

    this.patientService.registerPatient(patientData).subscribe({
      next: async (data) => {
        console.log(data, "\nHere");

        this.toastService.show({
          messages: ["Le patient a été enregistré avec succès."],
          type: ToastType.Success,
        });

        await this.secretariatRouter.navigateToPatientList();
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  registerPatientAndContinue() {
    this.isPatientInfoFormSubmitted = true;

    if (this.patientInfoForm.invalid) {
      const notificationMessages = this.getInvalidFields();

      this.toastService.show({
        messages: notificationMessages,
        type: ToastType.Warning,
      });

      return;
    }

    const patientData = this.getPatientFormData();

    this.patientService.registerPatient(patientData).subscribe({
      next: (data) => {
        console.log(data, "\nHere");

        this.toastService.show({
          messages: ["Le patient a été enregistré avec succès."],
          type: ToastType.Success,
        });

        this.patientService.setActivePatient(data).subscribe({
          next: async (data) => {
            await this.secretariatRouter.navigateToPatientActivity();
          },
          error: (e) => {
            console.error(e);
          },
        });
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }
}
