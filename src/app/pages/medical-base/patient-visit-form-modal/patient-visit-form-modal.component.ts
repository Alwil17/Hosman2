import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { Consultation } from "src/app/models/medical-base/consultation.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { VisitInfosFormComponent } from "../patient-visit-form/visit-infos-form/visit-infos-form.component";

@Component({
  selector: "app-patient-visit-form-modal",
  templateUrl: "./patient-visit-form-modal.component.html",
  styleUrls: ["./patient-visit-form-modal.component.scss"],
})
export class PatientVisitFormModalComponent implements OnInit {
  @ViewChild(VisitInfosFormComponent)
  visitInfosFormComponent!: VisitInfosFormComponent;

  @Input()
  consultations: Consultation[] = [];

  @Input()
  activeIndex = 0;

  @Input()
  patientInfos!: Patient;

  constructor() {}

  ngOnInit(): void {}

  goToFirstVisit() {
    this.activeIndex = this.visitInfosFormComponent.goToFirstVisit();
  }

  goToPreviousVisit() {
    this.activeIndex = this.visitInfosFormComponent.goToPreviousVisit();
  }

  goToNextVisit() {
    this.activeIndex = this.visitInfosFormComponent.goToNextVisit();
  }

  goToLastVisit() {
    this.activeIndex = this.visitInfosFormComponent.goToLastVisit();
  }
}
