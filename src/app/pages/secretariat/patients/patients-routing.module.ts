import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { PatientCreateComponent } from "./patient-create/patient-create.component";
import { PatientListComponent } from "./patient-list/patient-list.component";

const routes: Routes = [
  { path: "", redirectTo: "patient-list", pathMatch: "full" },
  { path: "patient-new", component: PatientCreateComponent },
  { path: "patient-list", component: PatientListComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PatientsRoutingModule {}
