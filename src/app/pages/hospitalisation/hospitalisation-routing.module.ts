import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewBedComponent } from './add/new.component';
import { ListComponent } from './list/list.component';

const routes: Routes = [
  { path: "", redirectTo: "beds", pathMatch: "full" },
  { path: "new", component: NewBedComponent, },
  { path: "beds", component: ListComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HospitalisationRoutingModule { }
