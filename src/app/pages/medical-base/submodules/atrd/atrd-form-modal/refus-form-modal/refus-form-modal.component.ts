import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { AtrdService } from '@services/medical-base/submodules/atrd.service';
import { MessageService } from '@services/messages/message.service';
import { ToastService } from '@services/secretariat/shared/toast.service';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';
import { ErrorMessages } from 'src/app/helpers/messages';
import { formatDate, validateYupSchema } from 'src/app/helpers/utils';
import { ToastType } from 'src/app/models/extras/toast-type.model';
import { Refused } from 'src/app/models/medical-base/submodules/atrd/refused.model';
import * as Yup from "yup";

@Component({
  selector: 'app-refus-form-modal',
  templateUrl: './refus-form-modal.component.html',
  styleUrls: ['./refus-form-modal.component.scss']
})
export class RefusFormModalComponent implements OnInit {
  @Input()
  consultationId$ = new BehaviorSubject<number | null>(null);

  @Output()
  refusedRegistering = new EventEmitter<boolean>();

  @Input()
  refused$ = new Subject<Refused>();
  
  @Input()
  refused?: Refused;


  @Input()
  ordonnance = new FormControl(false, []);
  decharge = new FormControl(false, []);
  

  showControl = new FormControl(false);
  displayOptions = [
    { text: "Oui", value: true },
    { text: "Non", value: false },
  ];

  subscription: Subscription | undefined;

  today = formatDate(new Date(), "yyyy-MM-dd");
  comment = new FormControl(null, []);

  consultation_id: number | null = null;
  isRefusedFormSubmitted = false;

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

  fGroup: FormGroup = new FormGroup({});

  constructor(
    private message: MessageService,
    private toastService: ToastService,
    private atrdService: AtrdService,
  ) {}

  ngOnInit(): void {
    this.fGroup = new FormGroup({
      date_op: this.date_op,
      motif: this.motif,
      comment: this.comment,
      decharge: this.decharge,
      rapport: this.ordonnance,
    });

    this.consultationId$.subscribe({
      next: (consultationId) => {
        if (consultationId) {
          //console.log(JSON.stringify(this.adressed, null, 2));
          this.consultation_id = consultationId;
          this.refused!.consultation_id = consultationId;
          console.log("+++++++" + JSON.stringify(this.refused, null, 2));
          
          this.atrdService
            .saveRefused(this.refused!)
            .subscribe({
              next: (data) => {
                console.log(data, "\nHere refused");

                this.refused$.next(this.refused!);

                this.toastService.show({
                  messages: ["Le refused a été enregistrée avec succès."],
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

  setValues() {
    this.showControl.setValue(true);

    this.date_op.setValue(formatDate(this.refused!.date_op, "yyyy-MM-dd"));
    this.motif.setValue(this.refused!.motif);
    this.decharge.setValue(this.refused!.has_decharge === false);
    this.ordonnance.setValue(this.refused!.has_ordonnance === false);
    this.comment.setValue(this.refused!.comments);
  }

  private markAllControlsAsTouched(): void {
    Object.keys(this.fGroup.controls).forEach((controlName) => {
      const control = this.fGroup.get(controlName);
      control!.markAsTouched();
    });
  }

  async confirmAction() {
    this.isRefusedFormSubmitted = true

    if (!this.fGroup.valid) {
      this.markAllControlsAsTouched();
    } else {
      const data: Refused = {
        date_op: new Date(this.fGroup.value.date_op),
        has_decharge: this.decharge.value,
        has_ordonnance: this.ordonnance.value,
        comments: this.fGroup.value.comment,
        motif: this.fGroup.value.motif,
        consultation_id: this.consultation_id,
      };

      if (this.refused !== null && this.refused !== undefined && 'id' in this.refused) {
        data.id = this.refused.id
      }
      console.log(JSON.stringify(data))

      this.refused = data

      this.refusedRegistering.emit(true);

    }
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe;
  }

}
