import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class SecretariatRouterService {
  private routesPath = {
    patientList: "/secretariat/patients/patient-list",
    patientNew: "/secretariat/patients/patient-new",
    patientActivity: "/secretariat/patients/patient-activity",
    patientDebts: "/secretariat/patients/patient-debts",
    patientRests: "/secretariat/patients/patient-rests",
    patientWaitingList: "/secretariat/patients/patient-waiting-list",

  };

  constructor(private router: Router) {}

  async navigateToPatientList() {
    await this.router.navigateByUrl(this.routesPath.patientList);
  }

  async navigateToPatientNew() {
    await this.router.navigateByUrl(this.routesPath.patientNew);
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
}
