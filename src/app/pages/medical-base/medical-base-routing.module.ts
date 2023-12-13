import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { PatientWaitingListPageComponent } from "./patient-waiting-list-page/patient-waiting-list-page.component";
import { PatientVisitsSummaryPageComponent } from "./patient-visits-summary-page/patient-visits-summary-page.component";
import { PatientVisitFormComponent } from "./patient-visit-form/patient-visit-form.component";

const routes: Routes = [
  { path: "", redirectTo: "patient-waiting-list", pathMatch: "full" },

  { path: "patient-waiting-list", component: PatientWaitingListPageComponent },
  {
    path: "patient-visits-summary",
    component: PatientVisitsSummaryPageComponent,
  },
  {
    path: "patient-visit-form",
    component: PatientVisitFormComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MedicalBaseRoutingModule {}
