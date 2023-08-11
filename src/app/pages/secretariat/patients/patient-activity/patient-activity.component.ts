import { Component, OnInit } from "@angular/core";
import { IActivity, IPrestation, IPrestationSelect } from "./activity.models";
import { FormControl, FormGroup, Validators } from "@angular/forms";

import { DatePipe, DecimalPipe } from "@angular/common";
import { FormsModule } from "@angular/forms";
import {
  NgbModal,
  NgbModalRef,
  NgbPaginationModule,
  NgbTypeaheadModule,
} from "@ng-bootstrap/ng-bootstrap";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { PatientInvoiceFormComponent } from "../patient-invoice-form/patient-invoice-form.component";
import { ACTS } from "src/app/data/secretariat/activities/acts.data";
import { ANALYSIS } from "src/app/data/secretariat/activities/analysis.data";
import { ECHOGRAPHIES } from "src/app/data/secretariat/activities/echographies.data";
import { ENDOSCOPIES } from "src/app/data/secretariat/activities/endoscopies.data";
import { HEMODIALYSES } from "src/app/data/secretariat/activities/hemodialyses.data";
import { MRIS } from "src/app/data/secretariat/activities/mris.data";
import { SCANNERS } from "src/app/data/secretariat/activities/scanners.data";

@Component({
  selector: "app-patient-activity",
  templateUrl: "./patient-activity.component.html",
  styleUrls: ["./patient-activity.component.scss"],
})
export class PatientActivityComponent implements OnInit {
  isMedicalProceduresSelected = true;

  // Activity form controls
  sectorControl = new FormControl("", [Validators.required]);
  consultingDoctorControl = new FormControl("", [Validators.required]);

  doctorTypeControl = new FormControl({ value: "", disabled: true }, [
    Validators.required,
  ]);
  doctorControl = new FormControl({ value: "", disabled: true }, [
    Validators.required,
  ]);
  performedByControl = new FormControl({ value: "", disabled: true }, [
    Validators.required,
  ]);

  activityDateControl = new FormControl("", [Validators.required]);
  quantityControl = new FormControl(1, [Validators.required]);
  originControl = new FormControl("", [Validators.required]);

  // Activity form group
  activityForm: FormGroup = new FormGroup({});
  isActivityFormSubmitted = false;

  // To set date min
  today = new Date().toLocaleDateString("fr-ca");

  table1: IPrestation[] = [];

  table2: Array<IPrestationSelect> = [];

  page = 1;
  pageSize = 5;
  collectionSize = this.table1.length;
  activities: IPrestation[] = [];

  invoiceModalRef!: NgbModalRef;

  constructor(
    public patientService: PatientService,
    private datePipe: DatePipe,
    private modalService: NgbModal
  ) {
    this.generateSummary();

    this.table1 = (this.prestations[0].items as IActivity[]).map((item) => {
      let patientPrice = 0;
      if (this.patientService.getActivePatientType() == 1) {
        patientPrice = item.NA;
      } else if (this.patientService.getActivePatientType() == 2) {
        patientPrice = item.ENA;
      } else if (this.patientService.getActivePatientType() == 3) {
        patientPrice = item.AL_S;
      } else {
        patientPrice = item.AHZ;
      }

      return {
        id: item.id,
        designation: item.designation,
        price: patientPrice,
        description: item.description,
      };
    }) as IPrestation[];

    this.collectionSize = this.table1.length;

    this.refreshActivities();
  }

  ngOnInit(): void {
    this.openInvoiceModal();

    this.activityForm = new FormGroup({
      sectorControl: this.sectorControl,
      consultingDoctorControl: this.consultingDoctorControl,

      doctorTypeControl: this.doctorTypeControl,
      doctorControl: this.doctorControl,
      performedByControl: this.performedByControl,

      activityDateControl: this.activityDateControl,
      quantityControl: this.quantityControl,
      originControl: this.originControl,
    });
  }

  updateTable(selectedPrestationType: number) {
    if (selectedPrestationType == 0) {
      this.isMedicalProceduresSelected = true;

      // Enabling medical procedures controls
      this.sectorControl.addValidators([Validators.required]);
      this.sectorControl.updateValueAndValidity();
      this.sectorControl.enable();

      this.consultingDoctorControl.addValidators([Validators.required]);
      this.consultingDoctorControl.updateValueAndValidity();
      this.consultingDoctorControl.enable();

      // Disabling non-medical procedures controls
      this.doctorTypeControl.clearValidators();
      this.doctorTypeControl.updateValueAndValidity();
      this.doctorTypeControl.disable();

      this.doctorControl.clearValidators();
      this.doctorControl.updateValueAndValidity();
      this.doctorControl.disable();

      this.performedByControl.clearValidators();
      this.performedByControl.updateValueAndValidity();
      this.performedByControl.disable();
    } else {
      this.isMedicalProceduresSelected = false;

      // Disabling medical procedures controls
      this.sectorControl.clearValidators();
      this.sectorControl.updateValueAndValidity();
      this.sectorControl.disable();

      this.consultingDoctorControl.clearValidators();
      this.consultingDoctorControl.updateValueAndValidity();
      this.consultingDoctorControl.disable();

      // Enabling non-medical procedures controls
      this.doctorTypeControl.addValidators([Validators.required]);
      this.doctorTypeControl.updateValueAndValidity();
      this.doctorTypeControl.enable();

      this.doctorControl.addValidators([Validators.required]);
      this.doctorControl.updateValueAndValidity();
      this.doctorControl.enable();

      this.performedByControl.addValidators([Validators.required]);
      this.performedByControl.updateValueAndValidity();
      this.performedByControl.enable();
    }

    this.table1 = (
      this.prestations[selectedPrestationType].items as IActivity[]
    ).map((item) => {
      let patientPrice = 0;
      if (this.patientService.getActivePatientType() == 1) {
        patientPrice = item.NA;
      } else if (this.patientService.getActivePatientType() == 2) {
        patientPrice = item.ENA;
      } else if (this.patientService.getActivePatientType() == 3) {
        patientPrice = item.AL_S;
      } else {
        patientPrice = item.AHZ;
      }

      return {
        id: item.id,
        designation: item.designation,
        price: patientPrice,
        description: item.description,
      };
    }) as IPrestation[];

    this.page = 1;
    this.collectionSize = this.table1.length;

    this.refreshActivities();
  }

  refreshActivities() {
    this.activities = this.table1
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  add(item: IPrestation) {
    console.log(item);

    const item2: IPrestationSelect = {
      id: item.id,
      rubrique: "ANALYSE",
      prestation: item.designation,
      price: item.price,
      quantity: this.quantityControl.value,
      total_price: item.price * this.quantityControl.value,
    };
    var index = this.table2.findIndex((value) => value.id == item.id);

    if (index === -1) {
      this.table2 = [...this.table2, item2];
    } else {
    }
  }

  remove(item: IPrestationSelect) {
    console.log(item);

    this.table2 = [
      ...this.table2.filter((value) => {
        return value.id !== item.id;
      }),
    ];
  }

  // medicalProcedures = ["Actes médicaux"];
  // examinations = ["Analyses", "Hémodialyses"];
  // medicalImaging = ["Radio", "Scanners", "IRM", "Echographie", "ECG", "EEG"];
  // meds = ["Médicaments", "Solutés", "Consommables"];
  // others = [
  //   "Kinésithérapie",
  //   "Pansement",
  //   "Injections",
  //   "Endoscopie",
  //   "Déplacements",
  // ];

  prestationTypes: string[] = [
    "Bilan",
    "Actes médicaux",
    "Analyses",
    "Hémodialyses",
    "Radio",
    "Scanners",
    "IRM",
    "Echographie",
    "ECG",
    "EEG",
    "Médicaments",
    "Solutés",
    "Consommables",
    "Kinésithérapie",
    "Pansement",
    "Injections",
    "Endoscopie",
    "Déplacements",
  ];

  prestations = [
    {
      name: "Actes médicaux",
      items: ACTS,
    },
    {
      name: "Analyses",
      items: ANALYSIS.map((anal, index) => ({
        id: index,
        ...anal,
      })),
    },
    {
      name: "Echograpies",
      items: ECHOGRAPHIES,
    },
    {
      name: "Endoscopies",
      items: ENDOSCOPIES,
    },
    {
      name: "Hémodialyses",
      items: HEMODIALYSES,
    },
    {
      name: "IRM",
      items: MRIS,
    },
    {
      name: "Scanners",
      items: SCANNERS,
    },
  ];

  summary = {
    title: "",
    fullName: "",
    birth: "",
    // age: "",
    birthPlace: "",
    profession: "",
    // nationality: "",
    // insuranceRate: "",
    // insurance: "",
    // insuranceEnd: "",
    tel1: "",
    tel2: "",
    personToContact: "",
  };

  generateSummary() {
    this.summary = {
      title:
        this.patientService.getActivePatient().sexe === "Masculin"
          ? "Monsieur"
          : "Mademoiselle",
      fullName:
        this.patientService.getActivePatient().nom +
        " " +
        this.patientService.getActivePatient().prenoms,
      birth: this.datePipe.transform(
        this.patientService.getActivePatient().date_naissance,
        "dd/MM/yyyy"
      )!,
      // age: this.ageControl.value as string,
      birthPlace: this.patientService.getActivePatient().lieu_naissance
        ? this.patientService.getActivePatient().lieu_naissance!
        : "",
      profession: this.patientService.getActivePatient().profession
        ? this.patientService.getActivePatient().profession!.nom
        : "",
      // nationality: this.patientService.getActivePatient().pays_origine ? this.patientService.getActivePatient().pays_origine as string : "",
      // insuranceRate: this.patientService.getActivePatient().assurance..value
      //   ? this.insuranceRateControl.value
      //   : "",
      // insurance: this.insuranceControl.value ? this.insuranceControl.value : "",
      // insuranceEnd: this.insuranceEndControl.value
      //   ? this.datePipe.transform(this.insuranceEndControl.value, "dd/MM/yyyy")!
      //   : "",

      tel1: this.patientService.getActivePatient().tel1
        ? this.patientService.getActivePatient().tel1
        : "",
      tel2: this.patientService.getActivePatient().tel2
        ? this.patientService.getActivePatient().tel2!
        : "",

      personToContact: this.patientService.getActivePatient()
        .personne_a_prevenir
        ? this.patientService.getActivePatient().personne_a_prevenir
        : "",
    };
  }

  openInvoiceModal() {
    // this.isActivityFormSubmitted = true;

    // if (this.activityForm.valid) {
    // if (!this.invoiceModalRef) {
    this.invoiceModalRef = this.modalService.open(PatientInvoiceFormComponent, {
      size: "xl",
      centered: true,
      scrollable: true,
    });
    // }

    this.invoiceModalRef.componentInstance.patientActivities = this.table2;
    // }
  }
}
