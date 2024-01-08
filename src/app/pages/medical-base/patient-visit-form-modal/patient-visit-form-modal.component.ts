import { Component, Input, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup } from "@angular/forms";
import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import {
  Subject,
  catchError,
  concat,
  distinctUntilChanged,
  forkJoin,
  of,
  switchMap,
  tap,
} from "rxjs";
import { SelectOption } from "src/app/models/extras/select.model";
import { Consultation } from "src/app/models/medical-base/consultation.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { DiagnosticService } from "src/app/services/medical-base/diagnostic.service";
import { MotifService } from "src/app/services/medical-base/motif.service";
import { LoadingSpinnerService } from "src/app/services/secretariat/shared/loading-spinner.service";
import { SectorService } from "src/app/services/secretariat/shared/sector.service";
import { TariffService } from "src/app/services/secretariat/shared/tariff.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-patient-visit-form-modal",
  templateUrl: "./patient-visit-form-modal.component.html",
  styleUrls: ["./patient-visit-form-modal.component.scss"],
})
export class PatientVisitFormModalComponent implements OnInit {
  @Input()
  consultations!: Consultation[];

  @Input()
  activeIndex = 0;

  // @Input()
  consultation!: Consultation;

  // To set date min
  today = new Date();
  // .toLocaleDateString("fr-ca");

  // Visit form 2 controls
  visitDateControl = new FormControl(null);
  visitDoctorControl = new FormControl(null);
  visitSectorControl = new FormControl(null);

  weightControl = new FormControl(null);
  sizeControl = new FormControl(null);
  temperatureControl = new FormControl(null);
  pcControl = new FormControl(null);
  frControl = new FormControl(null);
  pulseControl = new FormControl(null);
  tensionControl = new FormControl(null);

  diseaseHistoryControl = new FormControl(null);
  prescribedMedicationControl = new FormControl(null);

  // Visit forms group
  patientVisitForm1!: FormGroup;
  patientVisitForm!: FormGroup;

  sectors!: SelectOption[];
  acts!: SelectOption[];
  motifs: SelectOption[] = [];
  diagnostics: SelectOption[] = [];

  motifsAreLoading = false;
  motifsTypeahead$ = new Subject<any>();

  diagnosticsAreLoading = false;
  diagnosticsTypeahead$ = new Subject<any>();

  waitingDetails = {
    visitActs: "",
    visitTotalCost: 0,
    visitDate: new Date(),
  };

  constructor(
    public modal: NgbActiveModal,
    private sectorService: SectorService,
    private tariffService: TariffService,
    private motifService: MotifService,
    private diagnosticService: DiagnosticService,
    // private patientVisitService: PatientVisitService,
    private loadingSpinnerService: LoadingSpinnerService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    // this.consultation = this.consultations[this.activeIndex];

    this.patientVisitForm = new FormGroup({
      weightControl: this.weightControl,
      sizeControl: this.sizeControl,
      temperatureControl: this.temperatureControl,
      pcControl: this.pcControl,
      frControl: this.frControl,
      pulseControl: this.pulseControl,
      tensionControl: this.tensionControl,

      acts: new FormArray([]),
      motifs: new FormArray([]),
      diagnostics: new FormArray([]),

      diseaseHistoryControl: this.diseaseHistoryControl,
      prescribedMedicationControl: this.prescribedMedicationControl,
    });

    this.fetchVisitSelectData();

    this.onFormInputsChanges();
  }

  firstVisit() {
    this.activeIndex = this.consultations.length - 1;
    // this.consultation = this.consultations[this.activeIndex];

    console.log(JSON.stringify(this.consultation, null, 2));

    this.setVisitInfoFieldsInitialValues();
  }

  previousVisit() {
    if (this.activeIndex < this.consultations.length - 1) {
      this.activeIndex++;
      // this.consultation = this.consultations[this.activeIndex];

      console.log(JSON.stringify(this.consultation, null, 2));

      this.setVisitInfoFieldsInitialValues();
    }
  }

  nextVisit() {
    if (this.activeIndex >= 1) {
      this.activeIndex--;
      // this.consultation = this.consultations[this.activeIndex];

      console.log(JSON.stringify(this.consultation, null, 2));

      this.setVisitInfoFieldsInitialValues();
    }
  }

  lastVisit() {
    this.activeIndex = 0;
    // this.consultation = this.consultations[this.activeIndex];

    console.log(JSON.stringify(this.consultation, null, 2));

    this.setVisitInfoFieldsInitialValues();
  }

  // GET SELECTS DATA-------------------------------------------------------------------------------------------------------------------------

  fetchVisitSelectData() {
    forkJoin({
      sectors: this.sectorService.getAll(),
      acts: this.tariffService.getByGroupCode("GRP001"),
      // motifs: this.motifService.getAll(),
      // diagnostics: this.diagnosticService.getAllDiagnostics(),
    }).subscribe({
      next: (data) => {
        this.sectors = data.sectors.map((value) => ({
          id: value.code,
          text: value.libelle,
        }));

        this.acts = data.acts.map((value) => ({
          id: value.code,
          text: value.libelle,
        }));

        // this.motifs = data.motifs.map((value) => ({
        //   id: value.id,
        //   text: value.libelle,
        // }));

        // this.diagnostics = data.diagnostics.map((value) => ({
        //   id: value.id,
        //   text: value.libelle,
        // }));

        this.setVisitInfoFieldsInitialValues();
      },
      error: (error) => {
        console.log(error);
      },
    });

    // MOTIFS
    const motifs$ = concat(
      of([]), // default items
      this.motifsTypeahead$.pipe(
        distinctUntilChanged(),
        tap(() => {
          this.motifsAreLoading = true;
          this.loadingSpinnerService.hideLoadingSpinner();
        }),
        switchMap((term) =>
          this.motifService.search(term).pipe(
            catchError((err) => {
              console.log(err);

              return of([]);
            }), // empty list on error
            tap(() => {
              this.motifsAreLoading = false;
              // this.loadingSpinnerService.showLoadingSpinner();
            })
          )
        )
      )
    );

    motifs$.subscribe({
      next: (data) => {
        this.motifs = data.map((value) => ({
          id: value.id,
          text: value.libelle,
        }));
      },
      error: (e) => {
        console.log(e);
      },
    });

    // DIAGNOSTICS
    const diagnostics$ = concat(
      of([]), // default items
      this.diagnosticsTypeahead$.pipe(
        distinctUntilChanged(),
        tap(() => {
          this.diagnosticsAreLoading = true;
          this.loadingSpinnerService.hideLoadingSpinner();
        }),
        switchMap((term) =>
          this.diagnosticService.search(term).pipe(
            catchError((err) => {
              console.log(err);

              return of([]);
            }), // empty list on error
            tap(() => {
              this.diagnosticsAreLoading = false;
              // this.loadingSpinnerService.showLoadingSpinner();
            })
          )
        )
      )
    );

    diagnostics$.subscribe({
      next: (data) => {
        this.diagnostics = data.map((value) => ({
          id: value.theCode,
          text: value.theCode + " - " + value.title,
        }));
      },
      error: (e) => {
        console.log(e);
      },
    });
  }

  setVisitInfoFieldsInitialValues() {
    if (this.consultations) {
      this.consultation = this.consultations[this.activeIndex];

      const actsLength = this.consultation.actes?.length ?? 0;
      const motifsLength = this.consultation.motifs?.length ?? 0;
      const diagnosticsLength = this.consultation.diagnostics?.length ?? 0;

      this.actsFields.clear();
      this.motifsFields.clear();
      this.diagnosticsFields.clear();

      if (actsLength === 0) {
        this.addActsField();
      }
      if (motifsLength === 0) {
        this.addMotifsField();
      }
      if (diagnosticsLength === 0) {
        this.addDiagnosticsField();
      }

      this.visitDateControl.setValue(
        new Date(this.consultation.date_consultation).toLocaleDateString(
          "fr-ca"
        )
      );
      this.visitDoctorControl.setValue("DOVI-AKUE J-P");
      this.visitSectorControl.setValue({
        id: this.consultation.secteur.code,
        text: this.consultation.secteur.libelle,
      });

      this.weightControl.setValue(this.consultation.constante?.poids);
      this.sizeControl.setValue(this.consultation.constante?.taille);
      this.temperatureControl.setValue(
        this.consultation.constante?.temperature
      );
      this.pcControl.setValue(this.consultation.constante?.perimetre_cranien);
      this.frControl.setValue(
        this.consultation.constante?.frequence_respiratoire
      );
      this.pulseControl.setValue(this.consultation.constante?.poul);
      this.tensionControl.setValue(this.consultation.constante?.tension);

      for (let i = 0; i <= actsLength - 1; i++) {
        this.addActsField();
      }

      if (
        this.consultation.actes != null &&
        this.consultation.actes.length !== 0
      ) {
        this.actsFields.controls.forEach((control, index) => {
          control.setValue({
            id: this.consultation.actes![index].code,
            text: this.consultation.actes![index].libelle,
          });
        });
      }

      for (let i = 0; i <= motifsLength - 1; i++) {
        this.addMotifsField();
      }

      if (
        this.consultation.motifs != null &&
        this.consultation.motifs.length !== 0
      ) {
        this.motifsFields.controls.forEach((control, index) => {
          control.setValue({
            id: this.consultation.motifs![index].id,
            text: this.consultation.motifs![index].libelle,
          });
        });
      }

      for (let i = 0; i <= diagnosticsLength - 1; i++) {
        this.addDiagnosticsField();
      }

      if (
        this.consultation.diagnostics != null &&
        this.consultation.diagnostics.length !== 0
      ) {
        this.diagnosticsFields.controls.forEach((control, index) => {
          control.setValue({
            id: this.consultation.diagnostics![index].theCode,
            text: this.consultation.diagnostics![index].title,
          });
        });
      }

      this.diseaseHistoryControl.setValue(this.consultation.hdm);
    } else {
      this.addActsField();
      this.addMotifsField();
      this.addDiagnosticsField();
    }
  }

  // ON FORM INPUTS CHANGES -----------------------------------------------------------------------------------------------------------
  onFormInputsChanges() {
    this.visitDateControl.valueChanges.subscribe((value) => {});
  }

  // SAVE CONSULTATION/VISIT INFOS --------------------------------------------------------------------------------------------------
  // saveVisitInfos() {
  //   if (this.patientVisitForm.invalid) {
  //     return;
  //   }

  //   let acts: ActRequest[] = [];
  //   let motifs: MotifRequest[] = [];
  //   let diagnostics: DiagnosticRequest[] = [];

  //   this.actsFields.controls.forEach((control, index) => {
  //     if (control.value) {
  //       acts.push(new ActRequest({ acte: control.value?.id }));
  //     }
  //   });

  //   this.motifsFields.controls.forEach((control, index) => {
  //     if (control.value) {
  //       motifs.push(
  //         new MotifRequest({ motif_id: control.value?.id, caractere: "cara" })
  //       );
  //     }
  //   });

  //   this.diagnosticsFields.controls.forEach((control, index) => {
  //     if (control.value) {
  //       diagnostics.push(
  //         new DiagnosticRequest({
  //           diagnostic: control.value?.id,
  //           commentaire: "com",
  //         })
  //       );
  //     }
  //   });

  //   const visitDate = new Date(this.visitDateControl.value);
  //   if (this.visitDateControl.dirty) {
  //     console.log("visit date dirty");

  //     visitDate.setHours(new Date(Date.now()).getHours());
  //     visitDate.setMinutes(new Date(Date.now()).getMinutes());
  //     visitDate.setSeconds(new Date(Date.now()).getSeconds());
  //   } else {
  //     if (
  //       this.patientVisitService.selectedWaitingListItem instanceof
  //       WaitingListItem
  //     ) {
  //       visitDate.setHours(
  //         new Date(
  //           this.patientVisitService.selectedWaitingListItem!.date_attente
  //         ).getHours()
  //       );
  //       visitDate.setMinutes(
  //         new Date(
  //           this.patientVisitService.selectedWaitingListItem!.date_attente
  //         ).getMinutes()
  //       );
  //       visitDate.setSeconds(
  //         new Date(
  //           this.patientVisitService.selectedWaitingListItem!.date_attente
  //         ).getSeconds()
  //       );
  //     } else {
  //       visitDate.setHours(new Date(Date.now()).getHours());
  //       visitDate.setMinutes(new Date(Date.now()).getMinutes());
  //       visitDate.setSeconds(new Date(Date.now()).getSeconds());
  //     }
  //   }

  //   let attente_num;
  //   if (
  //     this.patientVisitService.selectedWaitingListItem instanceof
  //     WaitingListItem
  //   ) {
  //     attente_num =
  //       this.patientVisitService.selectedWaitingListItem?.num_attente!;
  //   }

  //   const consultation = new ConsultationRequest({
  //     patient_ref: this.activePatient.reference,
  //     secteur_code: this.visitSectorControl.value.id,
  //     attente_num: attente_num,
  //     date_consultation: visitDate,
  //     // new Date(this.visitDateControl.value),
  //     hdm: this.diseaseHistoryControl.value,
  //     constante: new ConstanteRequest({
  //       poids: this.weightControl.value,
  //       taille: this.sizeControl.value,
  //       perimetre_cranien: this.pcControl.value,
  //       temperature: this.temperatureControl.value,
  //       frequence_respiratoire: this.frControl.value,
  //       poul: this.pulseControl.value,
  //       tension: this.tensionControl.value,
  //     }),
  //     actes: acts,
  //     motifs: motifs,
  //     diagnostics: diagnostics,
  //   });

  //   console.log(JSON.stringify(consultation, null, 2));

  //   this.patientVisitService.create(consultation).subscribe({
  //     next: async (data) => {
  //       console.log(data, "\nHere");

  //       this.toastService.show({
  //         messages: ["La consultation a été enregistrée avec succès."],
  //         type: ToastType.Success,
  //       });

  //       // await this.medicalBaseRouter.navigateToPatientWaitingList();
  //     },
  //     error: (e) => {
  //       console.error(e);

  //       this.toastService.show({
  //         delay: 10000,
  //         type: ToastType.Error,
  //       });
  //     },
  //   });
  // }

  // ACTS FIELDS --------------------------------------------------------------------------------------------------------
  get actsFields() {
    return this.patientVisitForm.get("acts") as FormArray;
  }

  addActsField() {
    this.actsFields.push(new FormControl(null));
    console.log(this.actsFields);
  }

  removeActsField(index: number) {
    if (this.actsFields.length !== 1) {
      this.actsFields.removeAt(index);
    }

    console.log(this.actsFields);
  }

  // MOTIFS FIELDS --------------------------------------------------------------------------------------------------------
  get motifsFields() {
    return this.patientVisitForm.get("motifs") as FormArray;
  }

  addMotifsField() {
    this.motifsFields.push(new FormControl(null));
    console.log(this.motifsFields);
  }

  removeMotifsField(index: number) {
    if (this.motifsFields.length !== 1) {
      this.motifsFields.removeAt(index);
    }

    console.log(this.motifsFields);
  }

  // DIAGNOSTICS FIELDS --------------------------------------------------------------------------------------------------------
  get diagnosticsFields() {
    return this.patientVisitForm.get("diagnostics") as FormArray;
  }

  addDiagnosticsField() {
    this.diagnosticsFields.push(new FormControl(null));
    console.log(this.diagnosticsFields);
  }

  removeDiagnosticsField(index: number) {
    if (this.diagnosticsFields.length !== 1) {
      this.diagnosticsFields.removeAt(index);
    }

    console.log(this.diagnosticsFields);
  }
}
