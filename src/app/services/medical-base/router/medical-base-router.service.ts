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
  };

  constructor(private router: Router) {}

  async navigateToPatientWaitingList() {
    await this.router.navigateByUrl(this.routesPath.patientWaitingList);
  }

  async navigateToPatientVisitsSummary() {
    await this.router.navigateByUrl(this.routesPath.patientVisitsSummary);
  }

  async navigateToPatientVisitForm() {
    await this.router.navigateByUrl(this.routesPath.patientVisitForm);
  }

  async navigateToPatientList() {
    await this.router.navigateByUrl(this.routesPath.patientList);
  }
}
