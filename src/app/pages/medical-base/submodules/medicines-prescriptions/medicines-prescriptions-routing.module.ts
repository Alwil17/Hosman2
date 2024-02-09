import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { MedicinesPrescriptionsPageComponent } from "./medicines-prescriptions-page/medicines-prescriptions-page.component";
import { MedicinesComponent } from "./medicines/medicines.component";
import { MedicinesPageComponent } from "./medicines-page/medicines-page.component";
import { PrescriptionsPageComponent } from "./prescriptions-page/prescriptions-page.component";

const routes: Routes = [
  //   { path: "", redirectTo: "", pathMatch: "full" },

  { path: "", component: MedicinesPrescriptionsPageComponent },
  {
    path: "medicines",
    component: MedicinesPageComponent,
  },
  {
    path: "prescriptions",
    component: PrescriptionsPageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MedicinesPrescriptionsRoutingModule {}
