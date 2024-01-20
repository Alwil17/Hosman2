import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-appointment-page",
  templateUrl: "./appointment-page.component.html",
  styleUrls: ["./appointment-page.component.scss"],
})
export class AppointmentPageComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  constructor() {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Secrétariat" },
      { label: "Rendez-vous", active: true },
    ];
  }
}
