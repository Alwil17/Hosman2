import { NgModule } from "@angular/core";
import { CommonModule, DatePipe } from "@angular/common";
import { PatientsRoutingModule } from "./patients-routing.module";
import { PatientCreateComponent } from "./patient-create/patient-create.component";
import { SharedModule } from "src/app/shared/shared.module";
import { PatientFormComponent } from "./patient-form/patient-form.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
// Wizard
import { ArchwizardModule } from "angular-archwizard";
import { PatientActivityComponent } from "./patient-activity/patient-activity.component";
import {
  NgbAccordionModule,
  NgbDropdownModule,
  NgbNavModule,
  NgbPaginationModule,
  NgbTypeaheadModule,
} from "@ng-bootstrap/ng-bootstrap";
import { PatientListComponent } from "./patient-list/patient-list.component";
import { NgxMaskModule } from "ngx-mask";
import { PatientInvoiceFormComponent } from "./patient-invoice-form/patient-invoice-form.component";
import { PatientWaitingListComponent } from "./patient-waiting-list/patient-waiting-list.component";
import { PersonToContactFormComponent } from "./patient-form-two/person-to-contact-form/person-to-contact-form.component";
import { PatientFormTwoComponent } from "./patient-form-two/patient-form-two.component";
import { PatientAddressFormComponent } from "./patient-form-two/patient-address-form/patient-address-form.component";
import { DebtsComponent } from "./debts/debts.component";

@NgModule({
  declarations: [
    PatientCreateComponent,
    PatientFormComponent,
    PatientActivityComponent,
    PatientListComponent,
    PatientInvoiceFormComponent,
    PatientWaitingListComponent,
    PersonToContactFormComponent,
    PatientFormTwoComponent,
    PatientAddressFormComponent,
    DebtsComponent,
  ],
  imports: [
    CommonModule,
    PatientsRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    ArchwizardModule,
    FormsModule,
    NgbPaginationModule,
    NgbNavModule,
    NgbDropdownModule,
    NgxMaskModule,
    NgbAccordionModule,
    // NgbTypeaheadModule,
  ],
  exports: [],
  providers: [DatePipe],
})
export class PatientsModule {}
