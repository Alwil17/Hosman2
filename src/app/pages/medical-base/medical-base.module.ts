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
import { AppointmentFormComponent } from "./appointment-form/appointment-form.component";
import { InvoiceDetailsModalComponent } from "./patient-waiting-list-page/invoice-details-modal/invoice-details-modal.component";
import { PatientListPageComponent } from "./patient-list-page/patient-list-page.component";
import { PatientsModule } from "../secretariat/patients/patients.module";
import { PatientVisitFormModalComponent } from "./patient-visit-form-modal/patient-visit-form-modal.component";
import { CountdownModule } from "ngx-countdown";
import { AppointmentFormModalComponent } from "./appointment-form-modal/appointment-form-modal.component";
import { AllAppointmentsModalComponent } from './appointment-form/all-appointments-modal/all-appointments-modal.component';

@NgModule({
  declarations: [
    PatientWaitingListPageComponent,
    PatientVisitsSummaryPageComponent,
    PatientVisitFormComponent,
    AppointmentFormComponent,
    InvoiceDetailsModalComponent,
    PatientListPageComponent,
    PatientVisitFormModalComponent,
    AppointmentFormModalComponent,
    AllAppointmentsModalComponent,
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
    PatientsModule,
    CountdownModule,
  ],
  exports: [AppointmentFormComponent],
})
export class MedicalBaseModule {}
