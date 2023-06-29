import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SecretariatRoutingModule } from './secretariat-routing.module';
import { PatientsModule } from './patients/patients.module';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    SecretariatRoutingModule,
    PatientsModule
  ]
})
export class SecretariatModule { }
