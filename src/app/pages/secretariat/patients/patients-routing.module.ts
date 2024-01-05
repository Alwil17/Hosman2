import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { PatientListComponent } from "./patient-list/patient-list.component";
import { PatientActivityComponent } from "./patient-activity/patient-activity.component";
import { PatientWaitingListComponent } from "./patient-waiting-list/patient-waiting-list.component";
import { DebtsComponent } from "./debts/debts.component";
import { PatientCreatePageComponent } from "./patient-create-page/patient-create-page.component";
import { PatientListPageComponent } from "./patient-list-page/patient-list-page.component";

const routes: Routes = [
  { path: "", redirectTo: "patient-list", pathMatch: "full" },
  { path: "patient-create", component: PatientCreatePageComponent },
  { path: "patient-list", component: PatientListPageComponent },
  { path: "patient-activity", component: PatientActivityComponent },
  { path: "patient-waiting-list", component: PatientWaitingListComponent },
  { path: "patient-debts", component: DebtsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PatientsRoutingModule {}
