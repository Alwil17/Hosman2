import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

const routes: Routes = [
  { path: "", redirectTo: "secretariat", pathMatch: "full" },
  {
    path: "secretariat",
    loadChildren: () =>
      import("./secretariat/secretariat.module").then(
        (m) => m.SecretariatModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {}
