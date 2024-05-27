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



@NgModule({
  declarations: [
    CrBoardComponent,
    CreateComponent,
    AccouchementComponent,
    MultiInputComponent
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
