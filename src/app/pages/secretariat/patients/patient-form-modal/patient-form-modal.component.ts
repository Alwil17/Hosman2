import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
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

  @Output()
  isPatientCreated = new EventEmitter<boolean>();

  @Input()
  showSimpleCreateButtons = false;
  
  constructor(public modal: NgbActiveModal) {}

  ngOnInit(): void {}

  onPatientModification(isModified: boolean) {
    this.isPatientModified.emit(isModified);
  }

  onPatientCreation(isModified: boolean) {
    this.isPatientCreated.emit(isModified);
  }
}
