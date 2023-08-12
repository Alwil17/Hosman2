import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SecretariatRoutingModule } from './secretariat-routing.module';
import { PatientsModule } from './patients/patients.module';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    SecretariatRoutingModule,
    PatientsModule,
    // NgbModalModule
  ]
})
export class SecretariatModule { }
