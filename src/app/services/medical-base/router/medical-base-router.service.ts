import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class MedicalBaseRouterService {
  private medicalBasePath = "/medical-base";

  private patientsPath = this.medicalBasePath + "";

  private routesPath = {
    patientWaitingList: this.patientsPath + "/patient-waiting-list",
    patientVisitsSummary: this.patientsPath + "/patient-visits-summary",
    patientVisitForm: this.patientsPath + "/patient-visit-form",
    patientList: this.patientsPath + "/patient-list",
    medicinesPrescriptions: this.patientsPath + "/medicines-prescriptions",
    patientRecent: this.patientsPath + "/patient-recent",
  };

  constructor(private router: Router) {}

  async navigateToPatientWaitingList() {
    await this.router.navigateByUrl(this.routesPath.patientWaitingList);
  }

  async navigateToPatientVisitsSummary(patientId: number) {
    await this.router.navigateByUrl(
      this.routesPath.patientVisitsSummary + "/" + String(patientId)
    );
  }

  async navigateToPatientVisitForm(patientId: number) {
    await this.router.navigateByUrl(
      this.routesPath.patientVisitForm + "/" + String(patientId)
    );
  }

  async navigateToPatientList() {
    await this.router.navigateByUrl(this.routesPath.patientList);
  }

  async navigateToMedicinesAndPrescriptions() {
    await this.router.navigateByUrl(this.routesPath.medicinesPrescriptions);
  }

  async navigateToPatientRecent(period: String) {
    await this.router.navigateByUrl(this.routesPath.patientRecent + '/' + period);
  }
}
