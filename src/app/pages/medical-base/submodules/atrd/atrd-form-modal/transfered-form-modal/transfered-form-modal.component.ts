import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { AtrdService } from '@services/medical-base/submodules/atrd.service';
import { MessageService } from '@services/messages/message.service';
import { DoctorService } from '@services/secretariat/shared/doctor.service';
import { SectorService } from '@services/secretariat/shared/sector.service';
import { ToastService } from '@services/secretariat/shared/toast.service';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject, Subject, Subscription, forkJoin } from 'rxjs';
import { ErrorMessages, WarningMessages } from 'src/app/helpers/messages';
import { formatDate, validateYupSchema } from 'src/app/helpers/utils';
import { SelectOption } from 'src/app/models/extras/select.model';
import { ToastType } from 'src/app/models/extras/toast-type.model';
import { Addressed } from 'src/app/models/medical-base/submodules/atrd/adressed.model';
import { Transfered } from 'src/app/models/medical-base/submodules/atrd/transfered.model';
import * as Yup from "yup";

@Component({
  selector: 'app-transfered-form-modal',
  templateUrl: './transfered-form-modal.component.html',
  styleUrls: ['./transfered-form-modal.component.scss']
})
export class TransferedFormModalComponent implements OnInit {
  @Input()
  consultationId$ = new BehaviorSubject<number | null>(null);

  @Output()
  transferedRegistering = new EventEmitter<boolean>();

  @Input()
  transfered$ = new Subject<Transfered>();

  @Input()
  transfered?: Transfered;

  @Input()
  comment = new FormControl(null, []);


  showControl = new FormControl(false);
  displayOptions = [
    { text: "Oui", value: true },
    { text: "Non", value: false },
  ];

  sectorControl = new FormControl();
  sectors!: SelectOption[];

  subscription: Subscription | undefined;

  today = formatDate(new Date(), "yyyy-MM-dd");

  consultation_id: number | null = null;
  isTransferedFormSubmitted = false;

  destination = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  accompagne = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  specialite = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  transport = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

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
    private sectorService: SectorService,
    private atrdService: AtrdService,
  ) { }

  ngOnInit(): void {
    this.fGroup = new FormGroup({
      destination: this.destination,
      accompagne: this.accompagne,
      specialite: this.specialite,
      transport: this.transport,
      date_op: this.date_op,
      motif: this.motif,
      comment: this.comment,
    });


    this.consultationId$.subscribe({
      next: (consultationId) => {
        if (consultationId) {
          //console.log(JSON.stringify(this.adressed, null, 2));
          this.consultation_id = consultationId;
          this.transfered!.consultation_id = consultationId;
          console.log("+++++++" + JSON.stringify(this.transfered, null, 2));

          this.atrdService
            .saveTransfered(this.transfered!)
            .subscribe({
              next: (data) => {
                console.log(data, "\nHere transfered");

                this.transfered$.next(this.transfered!);

                this.toastService.show({
                  messages: ["Le transfered a été enregistrée avec succès."],
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

    this.fetchSelectData();

    this.setValues();
  }

  setValues() {
    if (this.transfered) {
      this.showControl.setValue(true);

      this.specialite.setValue(this.transfered!.specialite);
      this.transport.setValue(this.transfered!.transport);
      this.date_op.setValue(formatDate(this.transfered!.date_op, "yyyy-MM-dd"));
      this.motif.setValue(this.transfered!.motif);
      this.comment.setValue(this.transfered!.comments);
      this.accompagne.setValue(this.transfered!.accompagne);
      this.destination.setValue(this.transfered!.destination);
    }

  }

  fetchSelectData() {
    forkJoin({
      sectors: this.sectorService.getAll()
    }).subscribe({
      next: (data) => {
        this.sectors = data.sectors.map((value) => ({
          id: value.code,
          text: value.libelle,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    })

  }

  private markAllControlsAsTouched(): void {
    Object.keys(this.fGroup.controls).forEach((controlName) => {
      const control = this.fGroup.get(controlName);
      control!.markAsTouched();
    });
  }

  async confirmAction() {
    this.isTransferedFormSubmitted = true

    if (!this.fGroup.valid) {
      this.markAllControlsAsTouched();
    } else {
      const data: Transfered = {
        date_op: new Date(this.fGroup.value.date_op),
        specialite: this.fGroup.value.specialite,
        destination: this.fGroup.value.destination,
        comments: this.fGroup.value.comment,
        accompagne: this.fGroup.value.accompagne,
        motif: this.fGroup.value.motif,
        transport: this.fGroup.value.transport,
        consultation_id: this.consultation_id,
      };

      if (this.transfered !== null && this.transfered !== undefined && 'id' in this.transfered) {
        data.id = this.transfered.id
      }
      console.log(JSON.stringify(data))

      this.transfered = data

      this.transferedRegistering.emit(true);

    }
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe;
  }

}
