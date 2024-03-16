import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { BehaviorSubject, Observable, Subject } from "rxjs";
import { Prescription } from "src/app/models/medical-base/submodules/medicines-prescriptions/prescription.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";

@Component({
  selector: "app-prescriptions-modal",
  templateUrl: "./prescriptions-modal.component.html",
  styleUrls: ["./prescriptions-modal.component.scss"],
})
export class PrescriptionsModalComponent implements OnInit {
  @Input()
  patientInfos?: Patient;

  consultationId$ = new BehaviorSubject<number | null>(null);

  @Output()
  prescriptionsRegistering = new EventEmitter<boolean>();

  prescriptions$ = new BehaviorSubject<Prescription[]>([]);

  @Input()
  prescriptions: Prescription[] = [];

  constructor() {}

  ngOnInit(): void {}

  emitPrescriptionRegistration() {
    this.prescriptionsRegistering.emit(true);
  }
}
