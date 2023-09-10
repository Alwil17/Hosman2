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

  searchTerm = "";

  allPatients: Patient[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.allPatients.length;
  patients: Patient[] = [];

  constructor(
    private secretariatRouter: SecretariatRouterService,
    private patientService: PatientService
  ) {
    this.patientService.getAll().subscribe({
      next: (data) => {
        this.allPatients = data;

        // this.refreshPatients();
      },
      error: (error) => {
        console.error(error);
      },
    });
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

  filterPatients() {
    const filteredPatients = this.searchTerm
      ? this.allPatients.filter((patient) =>
          (patient.nom + " " + patient.prenoms)
            .toLowerCase()
            .startsWith(this.searchTerm)
        )
      : [];

    this.collectionSize = filteredPatients.length;

    this.patients = filteredPatients.slice(
      (this.page - 1) * this.pageSize,
      (this.page - 1) * this.pageSize + this.pageSize
    );
  }

  refreshPatients() {
    this.collectionSize = this.allPatients.length;

    this.patients = this.allPatients.slice(
      (this.page - 1) * this.pageSize,
      (this.page - 1) * this.pageSize + this.pageSize
    );
  }

  // async
  view(patient: any) {
    this.patientService.setActivePatient(patient.id).subscribe({
      next: async (data) => {
        await this.secretariatRouter.navigateToPatientActivity();
      },
      error: (e) => {
        console.error(e);
      },
    });

    // console.log(JSON.stringify(patient, null, 2));
  }

  async goToPatientNew() {
    await this.secretariatRouter.navigateToPatientNew();
  }
}
