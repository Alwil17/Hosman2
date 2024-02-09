import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MedicinesComponent } from "./medicines/medicines.component";
import { MedicinesPrescriptionsRoutingModule } from "./medicines-prescriptions-routing.module";
import { MedicinesPrescriptionsPageComponent } from "./medicines-prescriptions-page/medicines-prescriptions-page.component";
import { MedicinesPageComponent } from "./medicines-page/medicines-page.component";
import { SharedModule } from "src/app/shared/shared.module";
import { PrescriptionsPageComponent } from "./prescriptions-page/prescriptions-page.component";
import { PrescriptionsComponent } from "./prescriptions/prescriptions.component";

@NgModule({
  declarations: [
    MedicinesPrescriptionsPageComponent,
    MedicinesPageComponent,
    MedicinesComponent,
    PrescriptionsPageComponent,
    PrescriptionsComponent,
  ],
  imports: [CommonModule, MedicinesPrescriptionsRoutingModule, SharedModule],
  exports: [MedicinesComponent, PrescriptionsComponent],
})
export class MedicinesPrescriptionsModule {}
