import { Component, Input, OnInit } from "@angular/core";
import { Patient } from "src/app/models/secretariat/patients/patient.model";

@Component({
  selector: "app-prescriptions-modal",
  templateUrl: "./prescriptions-modal.component.html",
  styleUrls: ["./prescriptions-modal.component.scss"],
})
export class PrescriptionsModalComponent implements OnInit {
  @Input()
  patientInfos?: Patient;

  constructor() {}

  ngOnInit(): void {}
}
