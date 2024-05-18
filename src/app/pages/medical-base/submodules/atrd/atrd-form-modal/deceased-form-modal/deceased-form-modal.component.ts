import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AtrdService } from '@services/medical-base/submodules/atrd.service';
import { MessageService } from '@services/messages/message.service';
import { ToastService } from '@services/secretariat/shared/toast.service';
import { HospitalisationStore } from '@stores/hospitalisation';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject, Subject, Subscription, pairwise } from 'rxjs';
import { ErrorMessages, WarningMessages } from 'src/app/helpers/messages';
import { validateYupSchema, markAllControlsAsTouched, formatDate } from 'src/app/helpers/utils';
import { ToastType } from 'src/app/models/extras/toast-type.model';
import { Deceded } from 'src/app/models/medical-base/submodules/atrd/deceded.model';
import * as Yup from "yup";


@Component({
  selector: 'app-deceased-form-modal',
  templateUrl: './deceased-form-modal.component.html',
  styleUrls: ['./deceased-form-modal.component.scss']
})
export class DeceasedFormModalComponent implements OnInit {
  @Input()
  consultationId$ = new BehaviorSubject<number | null>(null);

  @Output()
  refusedRegistering = new EventEmitter<boolean>();

  @Input()
  deceded$ = new Subject<Deceded>();
  
  @Input()
  deceded?: Deceded;

  showControl = new FormControl(false); 
  displayOptions = [{text : "Oui", value: true}, {text : "Non", value: false}]

  subscription: Subscription | undefined;

  today = formatDate(new Date(), "yyyy-MM-dd");

  consultation_id: number | null = null;
  isDecededFormSubmitted = false;

  date_op = new FormControl(
    this.today,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  motif = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  reanimation = new FormControl(false, []);
  deces = new FormControl(false, []);
  comment = new FormControl(null, []);

  fGroup: FormGroup = new FormGroup({});

  constructor(
    private message: MessageService,
    private toastService: ToastService,
    private atrdService: AtrdService,
  ) { }

  ngOnInit(): void {
    this.fGroup = new FormGroup({
      date_op: this.date_op,
      motif: this.motif,
      comment: this.comment,
      reanimation: this.reanimation,
      deces: this.deces,
    });

    this.consultationId$.subscribe({
      next: (consultationId) => {
        if (consultationId) {
          //console.log(JSON.stringify(this.adressed, null, 2));
          this.consultation_id = consultationId;
          this.deceded!.consultation_id = consultationId;
          console.log("+++++++" + JSON.stringify(this.deceded, null, 2));
          
          this.atrdService
            .saveDeceded(this.deceded!)
            .subscribe({
              next: (data) => {
                console.log(data, "\nHere deceded");

                this.deceded$.next(this.deceded!);

                this.toastService.show({
                  messages: ["Le deceded a été enregistrée avec succès."],
                  type: ToastType.Success,
                });
              },
              error: (e) => {
                console.error(e);

                this.toastService.show({
                  messages: ["Désolé, une erreur s'est produite."],
                  delay: 10000,
                  type: ToastType.Error,
                });
              },
            });
        }
      },
    });
    

    this.setValues();
  }

  private markAllControlsAsTouched(): void {
    Object.keys(this.fGroup.controls).forEach((controlName) => {
      const control = this.fGroup.get(controlName);
      control!.markAsTouched();
    });
  }

  setValues() {
    this.showControl.setValue(true);

    this.date_op.setValue(formatDate(this.deceded!.date_op, "yyyy-MM-dd"));
    this.motif.setValue(this.deceded!.motif);
    this.comment.setValue(this.deceded!.comments);
    this.reanimation.setValue(this.deceded!.reanimation === true);
    this.deces.setValue(this.deceded!.after_out === true);
  }

  async confirmAction() {
    this.isDecededFormSubmitted = true

    if (!this.fGroup.valid) {
      this.markAllControlsAsTouched();
    } else {
      const data: Deceded = {
        date_op: new Date(this.fGroup.value.date_op),
        after_out: this.deces.value,
        reanimation: this.reanimation.value,
        comments: this.fGroup.value.comment,
        motif: this.fGroup.value.motif,
        consultation_id: this.consultation_id,
      };

      if (this.deceded !== null && this.deceded !== undefined && 'id' in this.deceded) {
        data.id = this.deceded.id
      }
      console.log(JSON.stringify(data))

      this.deceded = data

      this.refusedRegistering.emit(true);

    }
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe;
  }


}
