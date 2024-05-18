import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdressedFormModalComponent } from './atrd-form-modal/adressed-form-modal/adressed-form-modal.component';
import { TransferedFormModalComponent } from './atrd-form-modal/transfered-form-modal/transfered-form-modal.component';
import { RefusFormModalComponent } from './atrd-form-modal/refus-form-modal/refus-form-modal.component';
import { DeceasedFormModalComponent } from './atrd-form-modal/deceased-form-modal/deceased-form-modal.component';
import { AtrdFormModalComponent } from './atrd-form-modal/atrd-form-modal.component';
import { SharedModule } from "src/app/shared/shared.module";
import { NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
    declarations: [
        AdressedFormModalComponent,
        TransferedFormModalComponent,
        RefusFormModalComponent,
        DeceasedFormModalComponent,
        AtrdFormModalComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        NgbNavModule,
        ReactiveFormsModule
    ]
})
export class AtrdModule { }
