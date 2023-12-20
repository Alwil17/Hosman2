import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewBedComponent } from './add/new.component';
import { ListComponent } from './list/list.component';
import { HospAdminComponent } from './admin/admin.component';

const routes: Routes = [
  { path: "", component: NewBedComponent, },
  { path: "list", component: ListComponent },
  { path: "administration", component: HospAdminComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HospitalisationRoutingModule { }
