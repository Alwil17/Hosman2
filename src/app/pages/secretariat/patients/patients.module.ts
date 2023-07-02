import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientsRoutingModule } from './patients-routing.module';
import { PatientCreateComponent } from './patient-create/patient-create.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { PatientFormComponent } from './patient-form/patient-form.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [PatientCreateComponent, PatientFormComponent],
  imports: [
    CommonModule,
    PatientsRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
  exports:[
    
  ]
})
export class PatientsModule { }
