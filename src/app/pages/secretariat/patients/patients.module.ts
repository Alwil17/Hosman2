import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientsRoutingModule } from './patients-routing.module';
import { PatientCreateComponent } from './patient-create/patient-create.component';
import { SharedModule } from 'src/app/shared/shared.module';



@NgModule({
  declarations: [PatientCreateComponent],
  imports: [
    CommonModule,
    PatientsRoutingModule,
    SharedModule
  ],
  exports:[
    
  ]
})
export class PatientsModule { }
