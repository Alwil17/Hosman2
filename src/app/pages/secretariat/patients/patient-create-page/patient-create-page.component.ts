import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-patient-create-page",
  templateUrl: "./patient-create-page.component.html",
  styleUrls: ["./patient-create-page.component.scss"],
})
export class PatientCreatePageComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  constructor() {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Patients" },
      { label: "Nouveau patient", active: true },
    ];
  }
}
