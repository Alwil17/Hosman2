import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class MedicinesPrescriptionsRouterService {
  private medicalBasePath = "/medical-base";

  private medicinesPrescriptionsPath =
    this.medicalBasePath + "/medicines-prescriptions";

  private routesPath = {
    medicines: this.medicinesPrescriptionsPath + "/medicines",
    prescriptions: this.medicinesPrescriptionsPath + "/prescriptions",
  };

  constructor(private router: Router) {}

  async navigateToMedicines() {
    await this.router.navigateByUrl(this.routesPath.medicines);
  }

  async navigateToPrescriptions() {
    await this.router.navigateByUrl(this.routesPath.prescriptions);
  }
}
