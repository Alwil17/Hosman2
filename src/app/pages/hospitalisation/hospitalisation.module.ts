import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HospitalisationRoutingModule } from './hospitalisation-routing.module';
import { NewBedComponent } from './add/new.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ListComponent } from './list/list.component';


@NgModule({
  declarations: [
    NewBedComponent,
    ListComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    HospitalisationRoutingModule
  ]
})
export class HospitalisationModule { }
