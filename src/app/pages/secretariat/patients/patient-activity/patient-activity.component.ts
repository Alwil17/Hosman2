import { Component, OnInit } from "@angular/core";
import { IActivity, IActivitySelect } from "./activity.models";
import { FormControl, FormGroup } from "@angular/forms";

import { DatePipe, DecimalPipe } from "@angular/common";
import { FormsModule } from "@angular/forms";
import {
  NgbPaginationModule,
  NgbTypeaheadModule,
} from "@ng-bootstrap/ng-bootstrap";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";

@Component({
  selector: "app-patient-activity",
  templateUrl: "./patient-activity.component.html",
  styleUrls: ["./patient-activity.component.scss"],
})
export class PatientActivityComponent implements OnInit {
  table1 = [
    {
      prestation: "VS",
      prix: 3250,
      ss_traitance: 0,
      new_tariff: 2990,
    },
    {
      prestation: "NB",
      prix: 1500,
      ss_traitance: 0,
      new_tariff: 1500,
    },
    {
      prestation: "GE",
      prix: 2500,
      ss_traitance: 1,
      new_tariff: 2300,
    },
    {
      prestation: "HB",
      prix: 2000,
      ss_traitance: 0,
      new_tariff: 2000,
    },
    {
      prestation: "NFS/Plaquette",
      prix: 8750,
      ss_traitance: 0,
      new_tariff: 8050,
    },
    {
      prestation: "TS",
      prix: 3750,
      ss_traitance: 0,
      new_tariff: 3450,
    },
    {
      prestation: "TP",
      prix: 8750,
      ss_traitance: 0,
      new_tariff: 8050,
    },
  ];

  table2: Array<IActivitySelect> = [];

  quantityControl = new FormControl(1);

  page = 1;
  pageSize = 5;
  collectionSize = this.table1.length;
  activities: IActivity[] = [];

  constructor(
    public patientService: PatientService,
    private datePipe: DatePipe
  ) {
    this.generateSummary();

    this.refreshActivities();
  }

  ngOnInit(): void {}

  activityForm = new FormGroup({
    quantityControl: this.quantityControl,
  });

  add(item: IActivity) {
    console.log(item);

    const item2: IActivitySelect = {
      id: item.id,
      rubrique: "ANALYSE",
      activity: item.prestation,
      prix: item.prix,
      quantite: this.quantityControl.value,
      prix_total: item.prix * this.quantityControl.value,
    };
    var index = this.table2.findIndex((value) => value.id == item.id);

    if (index === -1) {
      this.table2 = [...this.table2, item2];
    } else {
    }
  }

  remove(item: IActivitySelect) {
    console.log(item);

    this.table2 = [
      ...this.table2.filter((value) => {
        return value.id !== item.id;
      }),
    ];
  }

  refreshActivities() {
    this.activities = this.table1
      .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  medicalProcedures = ["Actes médicaux"];
  examinations = ["Analyses", "Hémodialyses"];
  medicalImaging = ["Radio", "Scanners", "IRM", "Echographie", "ECG", "EEG"];
  meds = ["Médicaments", "Solutés", "Consommables"];
  others = [
    "Kinésithérapie",
    "Pansement",
    "Injections",
    "Endoscopie",
    "Déplacements",
  ];

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
        ? (this.patientService.getActivePatient().profession as string)
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

      personToContact: this.patientService.getActivePatient().personToContact
        ? this.patientService.getActivePatient().personToContact
        : "",
    };
  }
}
