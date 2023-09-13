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

@NgModule({
  declarations: [ExpensesComponent, CollectionsComponent, PersonFormComponent],
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
