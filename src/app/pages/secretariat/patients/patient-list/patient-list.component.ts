import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { calculateExactAge } from "src/app/core/helpers/age-calculator";
import { IPatient } from "src/app/core/models/patient.model";

@Component({
  selector: "app-patient-list",
  templateUrl: "./patient-list.component.html",
  styleUrls: ["./patient-list.component.scss"],
})
export class PatientListComponent implements OnInit {
  allPatients: [{}] = [
    {
      age: calculateExactAge(new Date("1998-04-25")),
      ...{
        id: 0,
        reference: "PAT1",
        nom: "Catastrophe",
        prenoms: "Climatique",
        date_naissance: new Date("1998-04-25"),
        sexe: "Masculin",
        is_assure: false,
        tel1: "00000000",
        date_entre: new Date("2023-06-03"),
        no_carte: "0003-154-1324",
        assurance: "ASCOMA",
      },
    },
  ];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.allPatients.length;
  patients: any[] = [];

  constructor(private router: Router) {
    this.refreshActivities();
  }

  ngOnInit(): void {}

  refreshActivities() {
    this.patients = this.allPatients
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  view(patient: any) {
    console.log(patient);
    
  }
  goToPatientNew() {
    this.router.navigateByUrl('/secretariat/patients/patient-new');
  }
}
