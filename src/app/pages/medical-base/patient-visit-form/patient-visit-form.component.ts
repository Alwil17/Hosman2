import { Component, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { AppointmentFormComponent } from "../appointment-form/appointment-form.component";
import { MbService } from "../mb.service";
import { forkJoin } from "rxjs";
import { SelectOption } from "src/app/models/extras/select.model";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { MedicalBaseRouterService } from "src/app/services/medical-base/router/medical-base-router.service";
import { calculateExactAge } from "src/app/helpers/age-calculator";
import { parseIntOrZero } from "src/app/helpers/parsers";
import { ProfessionService } from "src/app/services/secretariat/patients/profession.service";
import { EmployerService } from "src/app/services/secretariat/patients/employer.service";
import { InsuranceService } from "src/app/services/secretariat/patients/insurance.service";
import { ParentRequest } from "src/app/models/secretariat/patients/requests/parent-request.model";
import { PatientVisitInfoRequest } from "src/app/models/secretariat/patients/requests/patient-visit-info-request.model";
import { ChronicDiseaseRequest } from "src/app/models/secretariat/patients/requests/chronic-disease-request.model";
import { Parent } from "src/app/models/secretariat/patients/parent.model";
import { MotifService } from "src/app/services/medical-base/motif.service";
import { DiagnosticService } from "src/app/services/medical-base/diagnostic.service";

@Component({
  selector: "app-patient-visit-form",
  templateUrl: "./patient-visit-form.component.html",
  styleUrls: ["./patient-visit-form.component.scss"],
})
export class PatientVisitFormComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  isPatientInfoCollapsed = false;
  isVisitFormCollapsed = true;

  // To set date min
  today = new Date();
  // .toLocaleDateString("fr-ca");

  // Visit form 1 controls
  // chronicDiseaseControl = new FormControl(null);
  statusControl = new FormControl(null);
  // backgroundControl = new FormControl(null);
  commentsControl = new FormControl(null);
  backgroundsControl = new FormControl(null);

  motherProfessionControl = new FormControl(null);
  motherTelControl = new FormControl(null);
  motherEmployerControl = new FormControl(null);
  motherInsuranceControl = new FormControl(null);
  motherBirthYearControl = new FormControl(null, [
    Validators.min(1800),
    Validators.max(this.today.getFullYear()),
  ]);
  motherAgeControl = new FormControl(null);

  fatherProfessionControl = new FormControl(null);
  fatherTelControl = new FormControl(null);
  fatherEmployerControl = new FormControl(null);
  fatherInsuranceControl = new FormControl(null);
  fatherBirthYearControl = new FormControl(null, [
    Validators.min(1800),
    Validators.max(this.today.getFullYear()),
  ]);
  fatherAgeControl = new FormControl(null);

  // Visit form 2 controls
  weightControl = new FormControl(null);
  sizeControl = new FormControl(null);
  temperatureControl = new FormControl(null);
  pcControl = new FormControl(null);
  frControl = new FormControl(null);
  pulseControl = new FormControl(null);
  tensionControl = new FormControl(null);
  diseaseHistoryControl = new FormControl(null);
  prescribedMedicationControl = new FormControl(null);

  // Visit forms group
  patientVisitForm1!: FormGroup;
  patientVisitForm2!: FormGroup;

  isFormSubmitted = false;

  activePatient!: Patient;
  isActivePatientAdult = false;

  professions!: SelectOption[];
  employers!: SelectOption[];
  insurances!: SelectOption[];
  chronicDiseases: SelectOption[] = [];

  constructor(
    public patientService: PatientService,
    private modalService: NgbModal,
    private motifService: MotifService,
    private diagnosticService: DiagnosticService,
    private medicalBaseRouter: MedicalBaseRouterService,
    private patientVisitService: PatientVisitService,
    private professionService: ProfessionService,
    private employerService: EmployerService,
    private insuranceService: InsuranceService
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Navette", active: true },
    ];

    // this.activePatient = this.patientService.getActivePatient();
    if (!this.patientVisitService.selectedWaitingListItem) {
      this.medicalBaseRouter.navigateToPatientWaitingList();

      return;
    }

    this.activePatient =
      this.patientVisitService.selectedWaitingListItem.patient;

    const activePatientExactAge = calculateExactAge(
      new Date(this.activePatient.date_naissance)
    );
    const activePatientAge = parseIntOrZero(
      activePatientExactAge.split(" ")[0]
    );
    console.log(activePatientAge);
    if (activePatientAge < 18) {
      this.isActivePatientAdult = true;
    }

    this.patientVisitForm1 = new FormGroup({
      // chronicDiseaseControl: this.chronicDiseaseControl,
      chronicDiseases: new FormArray([new FormControl(null)]),
      statusControl: this.statusControl,
      // backgroundControl: this.backgroundControl,
      commentsControl: this.commentsControl,
      backgroundsControl: this.backgroundsControl,

      motherProfessionControl: this.motherProfessionControl,
      motherTelControl: this.motherTelControl,
      motherEmployerControl: this.motherEmployerControl,
      motherInsuranceControl: this.motherInsuranceControl,
      motherBirthYearControl: this.motherBirthYearControl,
      motherAgeControl: this.motherAgeControl,

      fatherProfessionControl: this.fatherProfessionControl,
      fatherTelControl: this.fatherTelControl,
      fatherEmployerControl: this.fatherEmployerControl,
      fatherInsuranceControl: this.fatherInsuranceControl,
      fatherBirthYearControl: this.fatherBirthYearControl,
      fatherAgeControl: this.fatherAgeControl,
    });

    this.patientVisitForm2 = new FormGroup({
      weightControl: this.weightControl,
      sizeControl: this.sizeControl,
      temperatureControl: this.temperatureControl,
      pcControl: this.pcControl,
      frControl: this.frControl,
      pulseControl: this.pulseControl,
      tensionControl: this.tensionControl,

      acts: new FormArray([new FormControl(null)]),
      motifs: new FormArray([new FormControl(null)]),
      diagnostics: new FormArray([new FormControl(null)]),

      diseaseHistoryControl: this.diseaseHistoryControl,
      prescribedMedicationControl: this.prescribedMedicationControl,
    });

    this.fetchPatientSelectData();

    this.fetchVisitSelectData();
  }

  fetchPatientSelectData() {
    forkJoin({
      professions: this.professionService.getAll(),
      employers: this.employerService.getAll(),
      insurances: this.insuranceService.getAll(),
    }).subscribe({
      next: (data) => {
        this.professions = data.professions.map((value) => ({
          id: value.id,
          text: value.denomination,
        }));

        this.employers = data.employers.map((value) => ({
          id: value.id,
          text: value.nom,
        }));

        this.insurances = data.insurances.map((value) => ({
          id: value.id,
          text: value.nom,
        }));

        this.setFieldsInitialValues();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  setFieldsInitialValues() {
    const cdLength = this.activePatient.maladies?.length ?? 0;
    console.log(this.activePatient);

    let mother: Parent | undefined;
    let father: Parent | undefined;

    if (this.activePatient.parents) {
      const parents = this.activePatient.parents;

      parents.forEach((parent) => {
        if (parent.type === "mere") {
          mother = parent;
        } else if (parent.type === "pere") {
          father = parent;
        }
      });
    }

    for (let i = 0; i < cdLength - 1; i++) {
      this.addChronicDiseaseField();
    }

    this.activePatient.maladies?.map((value) => {
      this.chronicDiseases.push({
        id: value.id,
        text: value.nom,
      });
    });

    console.log(this.chronicDiseases);

    // const cd = this.activePatient.maladies!

    if (
      this.activePatient.maladies !== null &&
      this.activePatient.maladies?.length !== 0
    ) {
      this.chronicDiseasesFields.controls.forEach((control, index) => {
        control.setValue({
          id: this.activePatient.maladies![index].id,
          text: this.activePatient.maladies![index].nom,
        });
      });
    }

    this.commentsControl.setValue(this.activePatient.commentaire);
    this.backgroundsControl.setValue(this.activePatient.antecedent);

    if (mother) {
      this.motherProfessionControl.setValue(
        this.professions.find((value) => {
          return (
            value.text.toLowerCase() ===
            mother?.profession.denomination.toLowerCase()
          );
        })
      );
      this.motherTelControl.setValue(mother?.telephone);
      this.motherEmployerControl.setValue(
        this.employers.find((value) => {
          return (
            value.text.toLowerCase() === mother?.employeur.nom.toLowerCase()
          );
        })
      );
      this.motherInsuranceControl.setValue(
        this.insurances.find((value) => {
          return (
            value.text.toLowerCase() === mother?.assurance.nom.toLowerCase()
          );
        })
      );
      this.motherBirthYearControl.setValue(mother?.annee_naissance);
    }

    if (father) {
      this.fatherProfessionControl.setValue(
        this.professions.find((value) => {
          return (
            value.text.toLowerCase() ===
            father?.profession.denomination.toLowerCase()
          );
        })
      );
      this.fatherTelControl.setValue(father?.telephone);
      this.fatherEmployerControl.setValue(
        this.employers.find((value) => {
          return (
            value.text.toLowerCase() === father?.employeur.nom.toLowerCase()
          );
        })
      );
      this.fatherInsuranceControl.setValue(
        this.insurances.find((value) => {
          return (
            value.text.toLowerCase() === father?.assurance.nom.toLowerCase()
          );
        })
      );
      this.fatherBirthYearControl.setValue(father?.annee_naissance);
    }
  }

  savePatientInfo() {
    let chronicDiseases: ChronicDiseaseRequest[] = [];
    this.chronicDiseasesFields.controls.map((control) =>
      chronicDiseases.push({ maladie: control.value?.text })
    );

    let parents: ParentRequest[] = [];

    let mother = new ParentRequest({
      profession: this.motherProfessionControl.value?.text,
      employeur: this.motherEmployerControl.value?.text,
      assurance: this.motherInsuranceControl.value?.text,
      telephone: this.motherTelControl.value,
      annee_naissance: this.motherBirthYearControl.value,
      sexe: "F",
      type: "mere",
    });

    const father = new ParentRequest({
      profession: this.fatherProfessionControl.value?.text,
      employeur: this.fatherEmployerControl.value?.text,
      assurance: this.fatherInsuranceControl.value?.text,
      telephone: this.fatherTelControl.value,
      annee_naissance: this.fatherBirthYearControl.value,
      sexe: "M",
      type: "pere",
    });

    parents.push(mother, father);

    const patientVisitInfo = new PatientVisitInfoRequest({
      commentaire: this.commentsControl.value,
      antecedent: this.backgroundsControl.value,
      maladies: chronicDiseases,
      parents: parents,
    });

    console.log(JSON.stringify(patientVisitInfo, null, 2));

    this.patientService
      .updateVisitInfo(this.activePatient.id, patientVisitInfo)
      .subscribe({
        next: (data) => {
          // this.setFieldsInitialValues();
        },
        error: (error) => {
          console.log(error);
        },
      });
  }

  // CHRONIC DISEASES FIELDS --------------------------------------------------------------------------------------------------------
  get chronicDiseasesFields() {
    return this.patientVisitForm1.get("chronicDiseases") as FormArray;
  }

  addChronicDiseaseField() {
    this.chronicDiseasesFields.push(new FormControl(null));
    console.log(this.chronicDiseasesFields);
  }

  removeChronicDiseaseField(index: number) {
    this.chronicDiseasesFields.removeAt(index);
    console.log(this.chronicDiseasesFields);
  }

  // ACTS FIELDS --------------------------------------------------------------------------------------------------------
  get actsFields() {
    return this.patientVisitForm2.get("acts") as FormArray;
  }

  addActsField() {
    this.actsFields.push(new FormControl(null));
    console.log(this.actsFields);
  }

  removeActsField(index: number) {
    this.actsFields.removeAt(index);
    console.log(this.actsFields);
  }

  // MOTIFS FIELDS --------------------------------------------------------------------------------------------------------
  get motifsFields() {
    return this.patientVisitForm2.get("motifs") as FormArray;
  }

  addMotifsField() {
    this.motifsFields.push(new FormControl(null));
    console.log(this.motifsFields);
  }

  removeMotifsField(index: number) {
    this.motifsFields.removeAt(index);
    console.log(this.motifsFields);
  }

  // DIAGNOSTICS FIELDS --------------------------------------------------------------------------------------------------------
  get diagnosticsFields() {
    return this.patientVisitForm2.get("diagnostics") as FormArray;
  }

  addDiagnosticsField() {
    this.diagnosticsFields.push(new FormControl(null));
    console.log(this.diagnosticsFields);
  }

  removeDiagnosticsField(index: number) {
    this.diagnosticsFields.removeAt(index);
    console.log(this.diagnosticsFields);
  }

  // GET -------------------------------------------------------------------------------------------------------------------------
  motifs: SelectOption[] = [];
  diagnostics: SelectOption[] = [];

  fetchVisitSelectData() {
    forkJoin({
      motifs: this.motifService.getAllMotifs(),
      // diagnostics: this.diagnosticService.getAllDiagnostics(),
    }).subscribe({
      next: (data) => {
        this.motifs = data.motifs.map((value) => ({
          id: value.id,
          text: value.libelle,
        }));

        // this.diagnostics = data.diagnostics.map((value) => ({
        //   id: value.id,
        //   text: value.libelle,
        // }));

        // this.setFieldsInitialValues();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  // OPEN APPOINTMENT MODAL ------------------------------------------------------------------------------------------------------
  openAppointmentModal() {
    const appointmentFormModal = this.modalService.open(
      AppointmentFormComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );

    // personToContactModal.componentInstance.data = this.personToContactData;

    // personToContactModal.componentInstance.formData.subscribe(
    //   (formData: PersonToContactRequest) => {
    //     this.personToContactData = new PersonToContactRequest(
    //       formData.nom,
    //       formData.prenoms,
    //       formData.tel,
    //       formData.adresse
    //     );

    //     this.personToContactControl.setValue(
    //       formData.nom +
    //         ", " +
    //         formData.prenoms +
    //         ", " +
    //         formData.tel +
    //         ", " +
    //         formData.adresse
    //     );

    //     personToContactModal.close();
    //   }
    // );
  }
}
