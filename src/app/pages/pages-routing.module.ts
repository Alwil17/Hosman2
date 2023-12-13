import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AppsComponent } from "./apps.component";
import { AuthGuard } from "../guards/auth.guard";

const routes: Routes = [
  { path: "", redirectTo: "auth", pathMatch: "full" },

  {
    path: "auth",
    loadChildren: () => import("./auth/auth.module").then((m) => m.AuthModule),
  },

  { path: "apps", component: AppsComponent, canActivate: [AuthGuard] },
  {
    path: "secretariat",
    loadChildren: () =>
      import("./secretariat/secretariat.module").then(
        (m) => m.SecretariatModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: "medical-base",
    loadChildren: () =>
      import("./medical-base/medical-base.module").then(
        (m) => m.MedicalBaseModule
      ),
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {}
