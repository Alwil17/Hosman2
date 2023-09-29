import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class SecretariatRouterService {
  private secretariatPath = "/secretariat";

  private patientsPath = this.secretariatPath + "/patients";

  private routesPath = {
    patientList: this.patientsPath + "/patient-list",
    patientCreate: this.patientsPath + "/patient-create",
    patientActivity: this.patientsPath + "/patient-activity",
    patientDebts: this.patientsPath + "/patient-debts",
    patientRests: this.patientsPath + "/patient-rests",
    patientWaitingList: this.patientsPath + "/patient-waiting-list",

    expenses: this.secretariatPath + "/expenses",
    collections: this.secretariatPath + "/collections",
    activities: this.secretariatPath + "/activities/all",
  };

  constructor(private router: Router) {}

  async navigateToPatientList() {
    await this.router.navigateByUrl(this.routesPath.patientList);
  }

  async navigateToPatientCreate() {
    await this.router.navigateByUrl(this.routesPath.patientCreate);
  }

  async navigateToPatientActivity() {
    await this.router.navigateByUrl(this.routesPath.patientActivity);
  }

  async navigateToPatientDebts() {
    await this.router.navigateByUrl(this.routesPath.patientDebts);
  }

  async navigateToPatientRests() {
    await this.router.navigateByUrl(this.routesPath.patientRests);
  }

  async navigateToPatientWaitingList() {
    await this.router.navigateByUrl(this.routesPath.patientWaitingList);
  }

  async navigateToExpenses() {
    await this.router.navigateByUrl(this.routesPath.expenses);
  }

  async navigateToCollections() {
    await this.router.navigateByUrl(this.routesPath.collections);
  }
  async navigateToActivities() {
    await this.router.navigateByUrl(this.routesPath.activities);
  }
}
