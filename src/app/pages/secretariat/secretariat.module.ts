import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SecretariatRoutingModule } from "./secretariat-routing.module";
import { PatientsModule } from "./patients/patients.module";
import {
  NgbDropdownModule,
  NgbModalModule,
  NgbPaginationModule,
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

@NgModule({
  declarations: [
    ExpensesComponent,
    CollectionsComponent,
    PersonFormComponent,
    ActivitiesPageComponent,
    ReceiptsSummaryComponent,
    ActivitiesDetailComponent,
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

    // NgbModalModule
  ],
})
export class SecretariatModule {}
