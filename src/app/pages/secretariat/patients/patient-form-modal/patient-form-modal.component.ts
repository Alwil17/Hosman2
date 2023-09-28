import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { Patient } from "src/app/models/secretariat/patients/patient.model";

@Component({
  selector: "app-patient-form-modal",
  templateUrl: "./patient-form-modal.component.html",
  styleUrls: ["./patient-form-modal.component.scss"],
})
export class PatientFormModalComponent implements OnInit {
  @Input()
  title = "";

  @Input()
  patientInfos?: Patient;

  @Output()
  isPatientModified = new EventEmitter<boolean>();

  constructor() {}

  ngOnInit(): void {}

  onPatientModification(isModified: boolean) {
    this.isPatientModified.emit(isModified);
  }
}
