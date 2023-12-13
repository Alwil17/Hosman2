import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { MedicalBaseRoutingModule } from "./medical-base-routing.module";
import { PatientWaitingListPageComponent } from "./patient-waiting-list-page/patient-waiting-list-page.component";
import {
  NgbCollapseModule,
  NgbDropdownModule,
  NgbPaginationModule,
} from "@ng-bootstrap/ng-bootstrap";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgxMaskModule } from "ngx-mask";
import { SharedModule } from "src/app/shared/shared.module";
import { PatientVisitsSummaryPageComponent } from "./patient-visits-summary-page/patient-visits-summary-page.component";
import { PatientVisitFormComponent } from "./patient-visit-form/patient-visit-form.component";
import { AppointmentFormComponent } from './appointment-form/appointment-form.component';

@NgModule({
  declarations: [
    PatientWaitingListPageComponent,
    PatientVisitsSummaryPageComponent,
    PatientVisitFormComponent,
    AppointmentFormComponent,
  ],
  imports: [
    CommonModule,
    MedicalBaseRoutingModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    NgbPaginationModule,
    NgxMaskModule,
    NgbDropdownModule,
    NgbCollapseModule,
  ],
})
export class MedicalBaseModule {}
