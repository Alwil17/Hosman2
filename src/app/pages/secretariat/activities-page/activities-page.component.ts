import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-activities-page",
  templateUrl: "./activities-page.component.html",
  styleUrls: ["./activities-page.component.scss"],
})
export class ActivitiesPageComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  constructor() {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Activités" },
      { label: "Tout", active: true },
    ];
  }
}
