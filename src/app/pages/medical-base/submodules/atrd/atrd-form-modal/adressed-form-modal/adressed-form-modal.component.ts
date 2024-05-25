import { Component, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
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
import { InputComponent } from 'src/app/shared/form-inputs/input/input.component';
import { SelectComponent } from 'src/app/shared/form-inputs/select/select.component';
import * as Yup from "yup";

@Component({
  selector: 'app-adressed-form-modal',
  templateUrl: './adressed-form-modal.component.html',
  styleUrls: ['./adressed-form-modal.component.scss']
})
export class AdressedFormModalComponent implements OnInit {
  @Input()
  consultationId$ = new BehaviorSubject<number | null>(null);

  @Output()
  addressedRegistering = new EventEmitter<boolean>();

  @Input()
  adressed$ = new Subject<Addressed>();
  
  @Input()
  adressed?: Addressed;

  report = new FormControl(false);
  comment = new FormControl(null, []);
  lettre = new FormControl(false);

  showControl = new FormControl(false);
  displayOptions = [
    { text: "Oui", value: true },
    { text: "Non", value: false },
  ];
  isAddressedFormSubmitted = false;

  sectorControl = new FormControl();
  sectors!: SelectOption[];
  
  medecins!: SelectOption[];
  medecin = new FormControl(null, [
    Validators.required,
  ]);

  subscription: Subscription | undefined;

  today = formatDate(new Date(), "yyyy-MM-dd");

  consultation_id: number | null = null;

  specialite = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  institution = new FormControl(
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
  @ViewChildren(InputComponent)
  inputFields!: QueryList<InputComponent>;

  @ViewChildren(SelectComponent)
  selectFields!: QueryList<SelectComponent>;
  
  addressedForm = new FormGroup({});

  
  


  
  constructor(
    private message: MessageService,
    private toastService: ToastService,
    private sectorService: SectorService,
    private doctorService: DoctorService,
    private atrdService: AtrdService,
  ) {}

  ngOnInit(): void {
    this.addressedForm = new FormGroup({
      medecin: this.medecin,
      specialite: this.specialite,
      institution: this.institution,
      transport: this.transport,
      date_op: this.date_op,
      motif: this.motif,
      comment: this.comment,
      lettre: this.lettre,
      rapport: this.report,
    });

    this.consultationId$.subscribe({
      next: (consultationId) => {
        if (consultationId) {
          //console.log(JSON.stringify(this.adressed, null, 2));
          this.consultation_id = consultationId;
          this.adressed!.consultation_id = consultationId;
          console.log("+++++++" + JSON.stringify(this.adressed, null, 2));
          
          this.atrdService
            .saveAdressed(this.adressed!)
            .subscribe({
              next: (data) => {
                console.log(data, "\nHere addressed");

                this.adressed$.next(this.adressed!);

                this.toastService.show({
                  messages: ["L'addressed a été enregistrée avec succès."],
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

/*
    
    this.atrdService.getValue("consultation_id")?.subscribe({
      next: (v) => {
        this.consultation_id = v;
      },
    });
    if(this.consultation_id){
      this.atrdService.fetchAdressed(this.consultation_id).subscribe({
        next: (v) => {
          this.adressed = v;
        },
      });
    }*/

    this.fetchSelectData();

    this.setValues();
  }

  setValues() {
    if(this.adressed){
      this.showControl.setValue(true);

      this.medecin.setValue(this.adressed!.med_ref);
      this.specialite.setValue(this.adressed!.specialite);
      this.institution.setValue(this.adressed!.institution);
      this.transport.setValue(this.adressed!.transport);
      this.date_op.setValue(formatDate(this.adressed!.date_op, "yyyy-MM-dd"));
      this.motif.setValue(this.adressed!.motif);
      this.comment.setValue(this.adressed!.comments);
      this.lettre.setValue(this.adressed!.medical_letter === true);
      this.report.setValue(this.adressed!.medical_report === true);
    }
    
  }

  fetchSelectData() {
    forkJoin({
      sectors: this.sectorService.getAll(),
      medecins: this.doctorService.getAll(),
    }).subscribe({
      next: (data) => {
        this.sectors = data.sectors.map((value) => ({
          id: value.code,
          text: value.libelle,
        }));

        this.medecins = data.medecins.map((value) => ({
          id: value.matricule,
          text: value.fullName,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    })
    
  }

  private markAllControlsAsTouched(): void {
    Object.keys(this.addressedForm.controls).forEach((controlName) => {
      const control = this.addressedForm.get(controlName);
      control!.markAsTouched();
    });
  }

  getInvalidFields() {
    const invalidInputs: string[] = [];
    this.inputFields.forEach((input) => {
      if (input.control.invalid) {
        invalidInputs.push("- " + input.label);
      }
    });

    const invalidSelects: string[] = [];
    this.selectFields.forEach((select) => {
      if (select.control.invalid) {
        invalidSelects.push("- " + select.label);
      }
    });

    let notificationMessages: string[] = [];
    if (invalidInputs.length !== 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_INPUT_FIELDS,
        ...invalidInputs
      );
    }

    if (notificationMessages.length !== 0) {
      notificationMessages.push("");
    }

    if (invalidSelects.length !== 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_SELECT_FIELDS,
        ...invalidSelects
      );
    }

    return notificationMessages;
  }

  async confirmAction() {
    this.isAddressedFormSubmitted = true

    if (!this.addressedForm.valid) {
      this.markAllControlsAsTouched();
    } else {
      const data: Addressed = {
        med_ref: this.addressedForm.value.medecin.id,
        specialite: this.addressedForm.value.specialite,
        institution: this.addressedForm.value.institution,
        transport: this.addressedForm.value.transport,
        date_op: new Date(this.addressedForm.value.date_op),
        motif: this.addressedForm.value.motif,
        comments: this.addressedForm.value.comment,
        medical_letter: this.lettre.value,
        medical_report: this.report.value,
        consultation_id: this.consultation_id,
      };

      if (this.adressed !== null && this.adressed !== undefined && 'id' in this.adressed) {
        data.id = this.adressed.id
      }
      console.log(JSON.stringify(data))

      this.adressed = data

      //this.atrdService.updateStore( this.adressed , "ADDRESSED")

      this.addressedRegistering.emit(true);
/*
      this.atrdService.saveAdressed(data).subscribe({
        next: (v) => {
          data.id = v;
          this.toastService.show({messages: ["Enregistrement effectué"], type: ToastType.Success});
          //this.atrdService.fetchAdressed(this.consultation_id!);
        },
        error: (e) => console.error(e),
      });*/
    }
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe;
  }
}
