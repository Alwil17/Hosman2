import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MedicinesComponent } from "./medicines/medicines.component";
import { MedicinesPrescriptionsRoutingModule } from "./medicines-prescriptions-routing.module";
import { MedicinesPrescriptionsPageComponent } from "./medicines-prescriptions-page/medicines-prescriptions-page.component";
import { MedicinesPageComponent } from "./medicines-page/medicines-page.component";
import { SharedModule } from "src/app/shared/shared.module";
import { PrescriptionsPageComponent } from "./prescriptions-page/prescriptions-page.component";
import { PrescriptionsComponent } from "./prescriptions/prescriptions.component";
import {
  NgbDropdownModule,
  NgbNavModule,
  NgbTypeaheadModule,
} from "@ng-bootstrap/ng-bootstrap";
import { ProductListComponent } from "./medicines/products/product-list/product-list.component";
import { ProductFormModalComponent } from "./medicines/products/product-form-modal/product-form-modal.component";
import { ReactiveFormsModule } from "@angular/forms";
import { ProductDetailModalComponent } from "./medicines/products/product-detail-modal/product-detail-modal.component";
import { TherapeuticClassListComponent } from "./medicines/therapeutic-classes/therapeutic-class-list/therapeutic-class-list.component";
import { NgxMaskModule } from "ngx-mask";
import { TherapeuticClassFormModalComponent } from './medicines/therapeutic-classes/therapeutic-class-form-modal/therapeutic-class-form-modal.component';

@NgModule({
  declarations: [
    MedicinesPrescriptionsPageComponent,
    MedicinesPageComponent,
    MedicinesComponent,
    PrescriptionsPageComponent,
    PrescriptionsComponent,
    ProductListComponent,
    ProductFormModalComponent,
    ProductDetailModalComponent,
    TherapeuticClassListComponent,
    TherapeuticClassFormModalComponent,
  ],
  imports: [
    CommonModule,
    MedicinesPrescriptionsRoutingModule,
    SharedModule,
    NgbNavModule,
    NgbDropdownModule,
    ReactiveFormsModule,
    NgbTypeaheadModule,
    NgxMaskModule,
  ],
  exports: [MedicinesComponent, PrescriptionsComponent],
})
export class MedicinesPrescriptionsModule {}
