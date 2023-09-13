import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ExpensesComponent } from "./expenses/expenses.component";
import { CollectionsComponent } from "./collections/collections.component";

const routes: Routes = [
  { path: "", redirectTo: "patients", pathMatch: "full" },

  {
    path: "patients",
    loadChildren: () =>
      import("./patients/patients.module").then((m) => m.PatientsModule),
  },
  { path: "expenses", component: ExpensesComponent },
  { path: "collections", component: CollectionsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SecretariatRoutingModule {}
