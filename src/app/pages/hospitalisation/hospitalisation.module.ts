import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HospitalisationRoutingModule } from './hospitalisation-routing.module';
import { NewBedComponent } from './add/new.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ListComponent } from './list/list.component';
import { AdmissionComponent } from './sections/admission/admission.component';
import { FicheComptableComponent } from './sections/fiche-comptable/fiche-comptable.component';
import { MedecinExterneComponent } from './sections/medecin-externe/medecin-externe.component';
import { InterventionChirurgicaleComponent } from './sections/intervention-chirurgicale/intervention-chirurgicale.component';
import { FicheAdresseComponent } from './sections/fiche-adresse/fiche-adresse.component';
import { FicheTransfuseComponent } from './sections/fiche-transfuse/fiche-transfuse.component';
import { FicheTransfereComponent } from './sections/fiche-transfere/fiche-transfere.component';
import { FicheScamComponent } from './sections/fiche-scam/fiche-scam.component';
import { FicheDecedeComponent } from './sections/fiche-decede/fiche-decede.component';
import { FicheSortieComponent } from './sections/fiche-sortie/fiche-sortie.component';
import { FicheSyntheseComponent } from './sections/fiche-synthese/fiche-synthese.component';
import { ComptableTableClassicComponent } from './sections/fiche-comptable/comptable-table-classic/comptable-table-classic.component';
import { HospAdminComponent } from './admin/admin.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MessageService } from '@services/messages/message.service';
import { ToastrModule } from 'ngx-toastr';


@NgModule({
  declarations: [
    NewBedComponent,
    ListComponent,
    AdmissionComponent,
    FicheComptableComponent,
    MedecinExterneComponent,
    InterventionChirurgicaleComponent,
    FicheAdresseComponent,
    FicheTransfuseComponent,
    FicheTransfereComponent,
    FicheScamComponent,
    FicheDecedeComponent,
    FicheSortieComponent,
    FicheSyntheseComponent,
    ComptableTableClassicComponent,
    HospAdminComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    HospitalisationRoutingModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
  ],
  providers: [
    MessageService
  ]
})
export class HospitalisationModule { }
