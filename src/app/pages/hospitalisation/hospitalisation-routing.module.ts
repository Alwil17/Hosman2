import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HospitHomeComponent } from './add/new.component';
import { ListComponent } from './list/list.component';

const routes: Routes = [
  { path: "",  redirectTo: "list", pathMatch: "full", },
  { path: "list", component: ListComponent },
  { path: "edit", component: HospitHomeComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HospitalisationRoutingModule { }
