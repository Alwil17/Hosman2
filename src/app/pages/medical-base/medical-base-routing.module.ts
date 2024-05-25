import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { PatientWaitingListPageComponent } from "./patient-waiting-list-page/patient-waiting-list-page.component";
import { PatientVisitsSummaryPageComponent } from "./patient-visits-summary-page/patient-visits-summary-page.component";
import { PatientVisitFormComponent } from "./patient-visit-form/patient-visit-form.component";
import { PatientListPageComponent } from "./patient-list-page/patient-list-page.component";
import { IsNotDirtyGuard } from "src/app/guards/is-not-dirty.guard";
import { PatientRecentComponent } from "./patient-recent/patient-recent.component";
import { SpecialRequestsPageComponent } from "./special-requests-page/special-requests-page.component";
import { ReqPecPageComponent } from "./req-pec-page/req-pec-page.component";
import { RequetesJaunesPageComponent } from "./requetes-jaunes-page/requetes-jaunes-page.component";

const routes: Routes = [
  { path: "", redirectTo: "patient-waiting-list", pathMatch: "full" },

  { path: "patient-waiting-list", component: PatientWaitingListPageComponent },
  {
    path: "patient-visits-summary/:patientId",
    component: PatientVisitsSummaryPageComponent,
  },
  {
    path: "patient-visit-form/:patientId",
    component: PatientVisitFormComponent,
    canDeactivate: [IsNotDirtyGuard],
  },
  {
    path: "patient-list",
    component: PatientListPageComponent,
  },
  {
    path: "patient-recent/:period",
    component: PatientRecentComponent,
  },
  {
    path: "special-requests/:type",
    component: SpecialRequestsPageComponent,
  },
  {
    path: "requetes-pec",
    component: ReqPecPageComponent,
  },
  {
    path: "requetes-jaunes",
    component: RequetesJaunesPageComponent,
  },
  {
    path: "medicines-prescriptions",
    loadChildren: () =>
      import(
        "./submodules/medicines-prescriptions/medicines-prescriptions.module"
      ).then((m) => m.MedicinesPrescriptionsModule),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MedicalBaseRoutingModule {}
