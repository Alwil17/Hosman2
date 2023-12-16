import { Component, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { AppointmentFormComponent } from "../appointment-form/appointment-form.component";
import { MbService } from "../mb.service";
import { forkJoin } from "rxjs";
import { SelectOption } from "src/app/models/extras/select.model";

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
  backgroundControl = new FormControl(null);
  commentsControl = new FormControl(null);

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

  constructor(
    public patientService: PatientService,
    private modalService: NgbModal,
    private mbService: MbService
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Navette", active: true },
    ];

    this.patientVisitForm1 = new FormGroup({
      // chronicDiseaseControl: this.chronicDiseaseControl,
      chronicDiseases: new FormArray([new FormControl(null)]),
      statusControl: this.statusControl,
      backgroundControl: this.backgroundControl,
      commentsControl: this.commentsControl,
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

    this.fetchSelectData();
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

  fetchSelectData() {
    forkJoin({
      motifs: this.mbService.getAllMotifs(),
      diagnostics: this.mbService.getAllDiagnostics(),
    }).subscribe({
      next: (data) => {
        this.motifs = data.motifs.map((value) => ({
          id: value.id,
          text: value.libelle,
        }));

        this.diagnostics = data.diagnostics.map((value) => ({
          id: value.id,
          text: value.libelle,
        }));

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
