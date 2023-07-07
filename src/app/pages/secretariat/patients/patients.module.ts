import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { PatientsRoutingModule } from "./patients-routing.module";
import { PatientCreateComponent } from "./patient-create/patient-create.component";
import { SharedModule } from "src/app/shared/shared.module";
import { PatientFormComponent } from "./patient-form/patient-form.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
// Wizard
import { ArchwizardModule } from "angular-archwizard";
import { PatientActivityComponent } from './patient-activity/patient-activity.component';
import { NgbPaginationModule, NgbTypeaheadModule } from "@ng-bootstrap/ng-bootstrap";
import { PatientListComponent } from './patient-list/patient-list.component';

@NgModule({
  declarations: [PatientCreateComponent, PatientFormComponent,  PatientActivityComponent, PatientListComponent],
  imports: [
    CommonModule,
    PatientsRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    ArchwizardModule,
    FormsModule,
    NgbPaginationModule,
    // NgbTypeaheadModule,
  ],
  exports: [],
})
export class PatientsModule {}
