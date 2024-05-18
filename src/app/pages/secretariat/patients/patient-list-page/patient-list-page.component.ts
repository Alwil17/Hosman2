import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-patient-list-page",
  templateUrl: "./patient-list-page.component.html",
  styleUrls: ["./patient-list-page.component.scss"],
})
export class PatientListPageComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  constructor() {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Patients" },
      { label: "Ancien patient", active: true },
    ];
  }
}
