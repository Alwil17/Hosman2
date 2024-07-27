import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { ContextMenuModule } from '../hospitalisation/context-menu.module';
import { NgxMaskModule } from 'ngx-mask';
import { ToastrModule } from 'ngx-toastr';
import { MessageService } from '@services/messages/message.service';
import { CrBoardComponent } from './board/board.component';
import { CreateComponent } from './create/create.component';
import { CrRoutingModule } from './cr-routing.module';
import { AccouchementComponent } from './sections/accouchement/accouchement.component';
import { MultiInputComponent } from './sections/multi-input/multi-input.component';
import { ExamenbabyComponent } from './sections/examenbaby/examenbaby.component';
import { FamiliyHistoryComponent } from './sections/familiy-history/familiy-history.component';
import { PregnancyHistoryComponent } from './sections/pregnancy-history/pregnancy-history.component';
import { ParacliniquesComponent } from './sections/paracliniques/paracliniques.component';



@NgModule({
  declarations: [
    CrBoardComponent,
    CreateComponent,
    AccouchementComponent,
    MultiInputComponent,
    ExamenbabyComponent,
    FamiliyHistoryComponent,
    PregnancyHistoryComponent,
    ParacliniquesComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    NgbModalModule,
    ContextMenuModule,
    NgxMaskModule,
    CrRoutingModule,
    ToastrModule.forRoot(),
  ],
  providers: [
    MessageService
  ]
})
export class CrModule { }
