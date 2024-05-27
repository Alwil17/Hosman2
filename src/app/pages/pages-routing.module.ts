import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AppsComponent } from "./apps.component";
import { AuthGuard } from "../guards/auth.guard";
import { HospitalisationComponent } from "../layouts/hospitalisation/hospitalisation.component";
import { LayoutComponent } from "../layouts/layout.component";
import { CrComponent } from "../layouts/cr/cr.component";

const routes: Routes = [
  { path: "", redirectTo: "auth", pathMatch: "full" },

  {
    path: "auth",
    loadChildren: () => import("./auth/auth.module").then((m) => m.AuthModule),
  },

  { path: "apps", component: AppsComponent, canActivate: [AuthGuard] },
  {
    path: "secretariat",
    component: LayoutComponent,
    loadChildren: () =>
      import("./secretariat/secretariat.module").then(
        (m) => m.SecretariatModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: "medical-base",
    component: LayoutComponent,
    loadChildren: () =>
      import("./medical-base/medical-base.module").then(
        (m) => m.MedicalBaseModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: "hospitalisation",
    component: HospitalisationComponent,
    loadChildren: () =>
      import("./hospitalisation/hospitalisation.module").then(
        (m) => m.HospitalisationModule
      ),
  },
  {
    path: "cr",
    component: CrComponent,
    loadChildren: () =>
      import("./cr/cr.module").then(
        (m) => m.CrModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {}
