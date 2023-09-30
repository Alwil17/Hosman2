import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ExpensesComponent } from "./expenses/expenses.component";
import { CollectionsComponent } from "./collections/collections.component";
import { ActivitiesPageComponent } from "./activities-page/activities-page.component";
import { TariffsComponent } from "./tariffs/tariffs.component";

const routes: Routes = [
  { path: "", redirectTo: "patients", pathMatch: "full" },

  {
    path: "patients",
    loadChildren: () =>
      import("./patients/patients.module").then((m) => m.PatientsModule),
  },
  { path: "expenses", component: ExpensesComponent },
  { path: "collections", component: CollectionsComponent },
  { path: "activities/all", component: ActivitiesPageComponent },
  { path: "secretariat/tariffs", component: TariffsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SecretariatRoutingModule {}
