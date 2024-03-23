import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { MedicalBaseRoutingModule } from "./medical-base-routing.module";
import { PatientWaitingListPageComponent } from "./patient-waiting-list-page/patient-waiting-list-page.component";
import {
  NgbCollapseModule,
  NgbDropdownModule,
  NgbNavModule,
  NgbPaginationModule,
  NgbTooltipModule,
  NgbTypeaheadModule,
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
import { AllAppointmentsModalComponent } from "./appointment-form/all-appointments-modal/all-appointments-modal.component";
import { MedicinesPrescriptionsModule } from "./submodules/medicines-prescriptions/medicines-prescriptions.module";
import { AdultPatientBackgroundsModalComponent } from "./patient-visit-form/adult-patient-backgrounds-modal/adult-patient-backgrounds-modal.component";
import { ChildPatientBackgroundsModalComponent } from "./patient-visit-form/child-patient-backgrounds-modal/child-patient-backgrounds-modal.component";
import { MultiInputModalComponent } from "./patient-visit-form/multi-input-modal/multi-input-modal.component";
import { SiblingsModalComponent } from "./patient-visit-form/siblings-modal/siblings-modal.component";
import { CoefficientSocialModalComponent } from "./patient-visit-form/coefficient-social-modal/coefficient-social-modal.component";
import { SiblingsNumberModalComponent } from './patient-visit-form/siblings-number-modal/siblings-number-modal.component';
import { SiblingsDetailModalComponent } from './patient-visit-form/siblings-detail-modal/siblings-detail-modal.component';
import { HospitalisationFormModalComponent } from './patient-visit-form/hospitalisation-form-modal/hospitalisation-form-modal.component';
import { PatientInfosFormComponent } from './patient-visit-form/patient-infos-form/patient-infos-form.component';
import { VisitInfosFormComponent } from './patient-visit-form/visit-infos-form/visit-infos-form.component';

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
    AdultPatientBackgroundsModalComponent,
    ChildPatientBackgroundsModalComponent,
    MultiInputModalComponent,
    SiblingsModalComponent,
    CoefficientSocialModalComponent,
    SiblingsNumberModalComponent,
    SiblingsDetailModalComponent,
    HospitalisationFormModalComponent,
    PatientInfosFormComponent,
    VisitInfosFormComponent,
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
    NgbTypeaheadModule,
    MedicinesPrescriptionsModule,
    NgbTooltipModule,
    NgbNavModule,
  ],
  exports: [AppointmentFormComponent],
})
export class MedicalBaseModule {}
