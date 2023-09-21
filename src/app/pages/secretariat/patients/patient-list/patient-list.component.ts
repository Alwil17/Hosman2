import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { SelectOption } from "src/app/models/extras/select.model";
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

  searchCriteria: SelectOption[] = [
    {
      id: "fullname",
      text: "Nom et prénoms",
    },
    {
      id: "reference",
      text: "Référence",
    },
    {
      id: "dob",
      text: "Date de naissance",
    },
    {
      id: "doc",
      text: "Date d'entrée",
    },
  ];

  searchCriterionControl = new FormControl(this.searchCriteria[0]);

  searchedPatients: Patient[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.searchedPatients.length;
  patients: Patient[] = [];

  constructor(
    private secretariatRouter: SecretariatRouterService,
    private patientService: PatientService
  ) {
    // this.patientService.getAll().subscribe({
    //   next: (data) => {
    //     this.allPatients = data;
    //     // this.refreshPatients();
    //   },
    //   error: (error) => {
    //     console.error(error);
    //   },
    // });
  }

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Patients" },
      { label: "Ancien patient", active: true },
    ];

    this.searchCriterionControl.valueChanges.subscribe((value) => {
      this.searchPatients();
    });
  }

  searchPatients() {
    // const filteredPatients = this.searchTerm
    //   ? this.allPatients.filter((patient) =>
    //       (patient.nom + " " + patient.prenoms)
    //         .toLowerCase()
    //         .startsWith(this.searchTerm.toLowerCase())
    //     )
    //   : [];

    this.patientService
      .searchBy(this.searchTerm, this.searchCriterionControl.value?.id)
      .subscribe({
        next: (data) => {
          this.searchedPatients = data;

          this.refreshPatients();
        },
        error: (error) => {
          console.error(error);
        },
      });

    // this.collectionSize = this.searchedPatients.length;

    // this.patients = this.searchedPatients.slice(
    //   (this.page - 1) * this.pageSize,
    //   (this.page - 1) * this.pageSize + this.pageSize
    // );
  }

  refreshPatients() {
    this.collectionSize = this.searchedPatients.length;

    this.patients = this.searchedPatients.slice(
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
