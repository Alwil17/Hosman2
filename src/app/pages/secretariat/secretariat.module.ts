import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SecretariatRoutingModule } from "./secretariat-routing.module";
import { PatientsModule } from "./patients/patients.module";
import {
  NgbDropdownModule,
  NgbModalModule,
  NgbPaginationModule,
  NgbTypeaheadModule,
} from "@ng-bootstrap/ng-bootstrap";
import { ExpensesComponent } from "./expenses/expenses.component";
import { SharedModule } from "src/app/shared/shared.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgxMaskModule } from "ngx-mask";
import { CollectionsComponent } from "./collections/collections.component";
import { PersonFormComponent } from "./expenses/person-form/person-form.component";
import { ActivitiesPageComponent } from "./activities-page/activities-page.component";
import { ReceiptsSummaryComponent } from "./activities-page/receipts-summary/receipts-summary.component";
import { ActivitiesDetailComponent } from "./activities-page/activities-detail/activities-detail.component";
import { TariffsComponent } from "./tariffs/tariffs.component";
import { ReportSearchCriteriaModalComponent } from "./activities-page/receipts-summary/report-search-criteria-modal/report-search-criteria-modal.component";
import { InsurancesDebtsModalComponent } from "./activities-page/receipts-summary/insurances-debts-modal/insurances-debts-modal.component";
import { PhoneBookPageComponent } from "./phone-book-page/phone-book-page.component";
import { PhoneBookFormModalComponent } from './phone-book-page/phone-book-form-modal/phone-book-form-modal.component';
import { AppointmentPageComponent } from './appointment-page/appointment-page.component';
import { MedicalBaseModule } from "../medical-base/medical-base.module";
import { TariffQuantityModalComponent } from './tariffs/tariff-quantity-modal/tariff-quantity-modal.component';

@NgModule({
  declarations: [
    ExpensesComponent,
    CollectionsComponent,
    PersonFormComponent,
    ActivitiesPageComponent,
    ReceiptsSummaryComponent,
    ActivitiesDetailComponent,
    TariffsComponent,
    ReportSearchCriteriaModalComponent,
    InsurancesDebtsModalComponent,
    PhoneBookPageComponent,
    PhoneBookFormModalComponent,
    AppointmentPageComponent,
    TariffQuantityModalComponent,
  ],
  imports: [
    CommonModule,
    SecretariatRoutingModule,
    PatientsModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule,
    NgxMaskModule,
    NgbPaginationModule,
    NgbDropdownModule,
    NgbTypeaheadModule,
    MedicalBaseModule
    // NgbModalModule
  ],
})
export class SecretariatModule {}
