import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { calculateExactAge } from "src/app/helpers/age-calculator";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { SecretariatRouterService } from "src/app/services/secretariat/router/secretariat-router.service";

@Component({
  selector: "app-patient-list",
  templateUrl: "./patient-list.component.html",
  styleUrls: ["./patient-list.component.scss"],
})
export class PatientListComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  allPatients: Patient[] = [
    // {
    //   age: calculateExactAge(new Date("1998-04-25")),
    //   ...{
    //     id: 0,
    //     reference: "PAT1",
    //     nom: "Catastrophe",
    //     prenoms: "Climatique",
    //     date_naissance: new Date("1998-04-25"),
    //     sexe: "Masculin",
    //     is_assure: false,
    //     tel1: "00000000",
    //     date_entre: new Date("2023-06-03"),
    //     no_carte: "0003-154-1324",
    //     assurance: "ASCOMA",
    //   },
    // },
  ];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.allPatients.length;
  patients: any[] = [];

  constructor(
    private secretariatRouter: SecretariatRouterService,
    private patientService: PatientService
  ) {
    this.allPatients =
      // ...this.allPatients,
      this.patientService.getAllPatients().map((patient) => ({
        ...patient,
        age: calculateExactAge(patient.date_naissance),
        nomAssurance: patientService.getInsurance(patient.id)?.nom,
      }));

    this.refreshPatients();
  }

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Patients" },
      { label: "Ancien patient", active: true },
    ];
  }

  refreshPatients() {
    this.collectionSize = this.allPatients.length;

    this.patients = this.allPatients
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  async view(patient: any) {
    this.patientService.setActivePatient(patient.id);

    await this.secretariatRouter.navigateToPatientActivity();
  }

  async goToPatientNew() {
    await this.secretariatRouter.navigateToPatientNew();
  }
}
