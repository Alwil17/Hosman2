import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-patient-create",
  templateUrl: "./patient-create.component.html",
  styleUrls: ["./patient-create.component.scss"],
})
export class PatientCreateComponent implements OnInit {
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
