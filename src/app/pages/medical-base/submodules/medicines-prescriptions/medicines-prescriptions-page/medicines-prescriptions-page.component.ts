import { Component, OnInit } from "@angular/core";
import { MedicinesPrescriptionsRouterService } from "src/app/services/medical-base/submodules/router/medicines-prescriptions-router.service";

@Component({
  selector: "app-medicines-prescriptions-page",
  templateUrl: "./medicines-prescriptions-page.component.html",
  styleUrls: ["./medicines-prescriptions-page.component.scss"],
})
export class MedicinesPrescriptionsPageComponent implements OnInit {
  constructor(
    private medicinesPrescriptionsRouter: MedicinesPrescriptionsRouterService
  ) {}

  ngOnInit(): void {}

  goToMedicines() {
    this.medicinesPrescriptionsRouter.navigateToMedicines();
  }

  goToPrescriptions() {
    this.medicinesPrescriptionsRouter.navigateToPrescriptions();
  }
}
