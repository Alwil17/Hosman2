import { Component, Input, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import {
  Subject,
  catchError,
  concat,
  distinctUntilChanged,
  firstValueFrom,
  forkJoin,
  of,
  switchMap,
  tap,
} from "rxjs";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { ActRequest } from "src/app/models/medical-base/requests/act-request.model";
import { ConstanteRequest } from "src/app/models/medical-base/requests/constante-request.model";
import { ConsultationRequest } from "src/app/models/medical-base/requests/consultation-request.model";
import { DiagnosticRequest } from "src/app/models/medical-base/requests/diagnostic-request.model";
import { MotifRequest } from "src/app/models/medical-base/requests/motif-request.model";
import { Prescription } from "src/app/models/medical-base/submodules/medicines-prescriptions/prescription.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { DiagnosticService } from "src/app/services/medical-base/diagnostic.service";
import { MotifService } from "src/app/services/medical-base/motif.service";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { MedicalBaseRouterService } from "src/app/services/medical-base/router/medical-base-router.service";
import { LoadingSpinnerService } from "src/app/services/secretariat/shared/loading-spinner.service";
import { SectorService } from "src/app/services/secretariat/shared/sector.service";
import { TariffService } from "src/app/services/secretariat/shared/tariff.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { PrescriptionsModalComponent } from "../../submodules/medicines-prescriptions/prescriptions/prescriptions-modal/prescriptions-modal.component";
import { parseIntOrZero } from "src/app/helpers/parsers";
import { AppointmentFormModalComponent } from "../../appointment-form-modal/appointment-form-modal.component";
import { ConfirmModalComponent } from "src/app/shared/modals/confirm-modal/confirm-modal.component";
import { HospitalisationFormModalComponent } from "../hospitalisation-form-modal/hospitalisation-form-modal.component";
import { SelectOption } from "src/app/models/extras/select.model";
import { Router } from "@angular/router";
import { Consultation } from "src/app/models/medical-base/consultation.model";

@Component({
  selector: "app-visit-infos-form",
  templateUrl: "./visit-infos-form.component.html",
  styleUrls: ["./visit-infos-form.component.scss"],
})
export class VisitInfosFormComponent implements OnInit {
  // Variables if using Component to display previous visits/consultations
  @Input()
  consultations: Consultation[] = [];

  @Input()
  activeIndex = 0;

  consultation?: Consultation;
  // ----------------------------------------------------------------------

  @Input()
  patientInfos!: Patient;

  visitInfosForm!: FormGroup;

  // Visit infos form controls
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

  consultationId?: number;
  prescriptions: Prescription[] = [];

  constructor(
    private modalService: NgbModal,
    private sectorService: SectorService,
    private tariffService: TariffService,
    private motifService: MotifService,
    private diagnosticService: DiagnosticService,
    private patientVisitService: PatientVisitService,
    private loadingSpinnerService: LoadingSpinnerService,
    private toastService: ToastService,
    private medicalBaseRouter: MedicalBaseRouterService,
    private routerService: Router
  ) {}

  ngOnInit(): void {
    this.visitInfosForm = new FormGroup({
      weightControl: this.weightControl,
      sizeControl: this.sizeControl,
      temperatureControl: this.temperatureControl,
      pcControl: this.pcControl,
      frControl: this.frControl,
      pulseControl: this.pulseControl,
      tensionControl: this.tensionControl,

      acts: new FormArray([new FormControl(null)]),
      motifs: new FormArray([new FormControl(null)]),
      diagnostics: new FormArray([new FormControl(null)]),

      diseaseHistoryControl: this.diseaseHistoryControl,
      prescribedMedicationControl: this.prescribedMedicationControl,
    });

    this.onVisitInfosFormInputsChanges();

    this.fetchVisitSelectData();
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

        // this.onVisitInfosFormInputsChanges();

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

  // SET FIELDS INITIAL VALUES --------------------------------------------------------------------------------------------------------------
  setVisitInfoFieldsInitialValues() {
    // If the component is used to register new visits/consultations
    if (this.consultations.length === 0) {
      if (this.patientVisitService.selectedWaitingListItem) {
        const actsLength =
          this.patientVisitService.selectedWaitingListItem.facture.prestation
            .tarifs.length ?? 0;

        this.patientVisitService.selectedWaitingListItem.facture.prestation.tarifs.forEach(
          (value) => {
            this.waitingDetails.visitActs += value.libelle + " * ";
          }
        );

        this.waitingDetails.visitTotalCost =
          this.patientVisitService.selectedWaitingListItem.facture.total;

        for (let i = 0; i < actsLength - 1; i++) {
          this.addActsField();
        }

        if (
          this.patientVisitService.selectedWaitingListItem.facture.prestation
            .tarifs != null &&
          this.patientVisitService.selectedWaitingListItem.facture.prestation
            .tarifs.length !== 0
        ) {
          this.actsFields.controls.forEach((control, index) => {
            control.setValue({
              id: this.patientVisitService.selectedWaitingListItem!.facture
                .prestation.tarifs![index].code,
              text: this.patientVisitService.selectedWaitingListItem!.facture
                .prestation.tarifs![index].libelle,
            });
          });
        }
      }

      if (this.patientVisitService.selectedWaitingListItem) {
        this.waitingDetails.visitDate = new Date(
          this.patientVisitService.selectedWaitingListItem!.date_attente
        );

        this.visitDateControl.setValue(
          new Date(
            this.patientVisitService.selectedWaitingListItem?.date_attente!
          ).toLocaleDateString("fr-ca")
        );

        this.visitDoctorControl.setValue("DOVI-AKUE J-P");

        this.visitSectorControl.setValue({
          id: this.patientVisitService.selectedWaitingListItem?.secteur?.code,
          text: this.patientVisitService.selectedWaitingListItem?.secteur
            ?.libelle,
        });
      } else {
        this.waitingDetails.visitDate = new Date();

        this.visitDateControl.setValue(new Date().toLocaleDateString("fr-ca"));

        this.visitDoctorControl.setValue("DOVI-AKUE J-P");
      }
    }

    // If the component is used to display (modify) previous visits/consultations
    else {
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
            id: this.consultation!.actes![index].code,
            text: this.consultation!.actes![index].libelle,
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
            id: this.consultation!.motifs![index].id,
            text: this.consultation!.motifs![index].libelle,
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
            id: this.consultation!.diagnostics![index].theCode,
            text: this.consultation!.diagnostics![index].title,
          });
        });
      }

      this.diseaseHistoryControl.setValue(this.consultation.hdm);

      // console.log(this.consultation?.ordonnance);

      // let medicines = "";

      // this.consultation?.ordonnance?.prescriptions.forEach(
      //   (prescription) =>
      //     (medicines +=
      //       prescription.produit.nom +
      //       " " +
      //       prescription.conditionnement +
      //       " " +
      //       prescription.forme.dosage +
      //       "\n")
      // );

      // console.log(medicines);

      // this.prescribedMedicationControl.setValue(medicines);
    }
  }

  // ON VISIT INFOS FORM INPUTS CHANGES -----------------------------------------------------------------------------------------------------------
  onVisitInfosFormInputsChanges() {
    this.visitDateControl.valueChanges.subscribe((value) => {
      this.waitingDetails.visitDate = new Date();
    });
  }

  // OPEN PRESCRIBER MODAL ------------------------------------------------------------------------------------------------------
  openPrescriberModal() {
    const prescriberModalRef = this.modalService.open(
      PrescriptionsModalComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );

    prescriberModalRef.componentInstance.patientInfos = this.patientInfos;
    prescriberModalRef.componentInstance.prescriptions = this.prescriptions;

    prescriberModalRef.componentInstance.prescriptionsRegistering.subscribe(
      (data: any) => {
        console.log("Prescription registering emitted");

        this.saveVisitObservable()?.subscribe((data) => {
          this.consultationId = parseIntOrZero(data);

          prescriberModalRef.componentInstance.consultationId$.next(
            parseIntOrZero(this.consultationId)
          );
        });
      }
    );

    prescriberModalRef.componentInstance.prescriptions$.subscribe(
      (data: Prescription[]) => {
        console.log("Prescriptions : " + JSON.stringify(data, null, 2));

        this.prescriptions = data;
      }
    );
  }

  // OPEN APPOINTMENT MODAL ------------------------------------------------------------------------------------------------------
  openAppointmentModal() {
    const appointmentFormModalRef = this.modalService.open(
      AppointmentFormModalComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );
  }

  // OPEN HOSPITALISATION MODAL ON CONFIRM ------------------------------------------------------------------------------------------------------
  async hospitalise() {
    // OPEN CONFIRMATION MODAL
    const confirmModalRef = this.modalService.open(ConfirmModalComponent, {
      size: "md",
      centered: true,
      keyboard: false,
      backdrop: "static",
    });

    confirmModalRef.componentInstance.message =
      "Voulez-vous vraiment hospitaliser ce patient ?";

    const isConfirmed = await firstValueFrom<boolean>(
      confirmModalRef.componentInstance.isConfirmed.asObservable()
    );

    // if (!isConfirmed) {
    //   const hospitalisationFormModalRef = this.modalService.open(
    //     HospitalisationFormModalComponent,
    //     {
    //       size: "xl",
    //       centered: true,
    //       scrollable: true,
    //       backdrop: "static",
    //     }
    //   );

    //   hospitalisationFormModalRef.componentInstance.patientInfos =
    //     this.patientInfos;
    // }

    if (isConfirmed) {
      // this.savePatientInfos();
      // this.saveVisitInfos();

      if (this.visitInfosForm.invalid) {
        return;
      }

      let acts: ActRequest[] = [];
      let motifs: MotifRequest[] = [];
      let diagnostics: DiagnosticRequest[] = [];

      this.actsFields.controls.forEach((control, index) => {
        if (control.value) {
          acts.push(new ActRequest({ acte: control.value?.id }));
        }
      });

      this.motifsFields.controls.forEach((control, index) => {
        if (control.value) {
          motifs.push(
            new MotifRequest({ motif_id: control.value?.id, caractere: "cara" })
          );
        }
      });

      this.diagnosticsFields.controls.forEach((control, index) => {
        if (control.value) {
          diagnostics.push(
            new DiagnosticRequest({
              diagnostic: control.value?.id,
              commentaire: "com",
            })
          );
        }
      });

      const visitDate = new Date(this.visitDateControl.value);
      if (this.visitDateControl.dirty) {
        console.log("visit date dirty");

        visitDate.setHours(new Date(Date.now()).getHours());
        visitDate.setMinutes(new Date(Date.now()).getMinutes());
        visitDate.setSeconds(new Date(Date.now()).getSeconds());
      } else {
        if (this.patientVisitService.selectedWaitingListItem) {
          visitDate.setHours(
            new Date(
              this.patientVisitService.selectedWaitingListItem!.date_attente
            ).getHours()
          );
          visitDate.setMinutes(
            new Date(
              this.patientVisitService.selectedWaitingListItem!.date_attente
            ).getMinutes()
          );
          visitDate.setSeconds(
            new Date(
              this.patientVisitService.selectedWaitingListItem!.date_attente
            ).getSeconds()
          );
        } else {
          visitDate.setHours(new Date(Date.now()).getHours());
          visitDate.setMinutes(new Date(Date.now()).getMinutes());
          visitDate.setSeconds(new Date(Date.now()).getSeconds());
        }
      }

      let attente_num;
      if (this.patientVisitService.selectedWaitingListItem) {
        attente_num =
          this.patientVisitService.selectedWaitingListItem?.num_attente!;
      }

      const consultation = new ConsultationRequest({
        patient_ref: this.patientInfos.reference,
        secteur_code: this.visitSectorControl.value.id,
        attente_num: attente_num,
        date_consultation: visitDate,
        // new Date(this.visitDateControl.value),
        hdm: this.diseaseHistoryControl.value,
        constante: new ConstanteRequest({
          poids: this.weightControl.value,
          taille: this.sizeControl.value,
          perimetre_cranien: this.pcControl.value,
          temperature: this.temperatureControl.value,
          frequence_respiratoire: this.frControl.value,
          poul: this.pulseControl.value,
          tension: this.tensionControl.value,
        }),
        actes: acts,
        motifs: motifs,
        diagnostics: diagnostics,
      });

      console.log(JSON.stringify(consultation, null, 2));

      this.patientVisitService.create(consultation).subscribe({
        next: async (data) => {
          console.log(data, "\nHere");

          this.toastService.show({
            messages: ["La consultation a été enregistrée avec succès."],
            type: ToastType.Success,
          });

          // const hospitalisationRoute = "/hospitalisation?consultation=" + data;

          // console.log(hospitalisationRoute);
          // console.log(this.routerService.url);

          // await this.routerService.navigateByUrl(hospitalisationRoute);

          const hospitalisationFormModalRef = this.modalService.open(
            HospitalisationFormModalComponent,
            {
              size: "xl",
              centered: true,
              scrollable: true,
              backdrop: "static",
            }
          );

          hospitalisationFormModalRef.componentInstance.patientInfos =
            this.patientInfos;
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

    // CLOSE CONFIRMATION MODAL
    confirmModalRef.close();
  }

  // SAVE CONSULTATION/VISIT INFOS --------------------------------------------------------------------------------------------------
  getPatientVisitFormData() {
    let acts: ActRequest[] = [];
    let motifs: MotifRequest[] = [];
    let diagnostics: DiagnosticRequest[] = [];

    this.actsFields.controls.forEach((control, index) => {
      if (control.value) {
        acts.push(new ActRequest({ acte: control.value?.id }));
      }
    });

    this.motifsFields.controls.forEach((control, index) => {
      if (control.value) {
        motifs.push(
          new MotifRequest({ motif_id: control.value?.id, caractere: "cara" })
        );
      }
    });

    this.diagnosticsFields.controls.forEach((control, index) => {
      if (control.value) {
        diagnostics.push(
          new DiagnosticRequest({
            diagnostic: control.value?.id,
            commentaire: "com",
          })
        );
      }
    });

    const visitDate = new Date(this.visitDateControl.value);
    if (this.visitDateControl.dirty) {
      console.log("visit date dirty");

      visitDate.setHours(new Date(Date.now()).getHours());
      visitDate.setMinutes(new Date(Date.now()).getMinutes());
      visitDate.setSeconds(new Date(Date.now()).getSeconds());
    } else {
      if (this.patientVisitService.selectedWaitingListItem) {
        visitDate.setHours(
          new Date(
            this.patientVisitService.selectedWaitingListItem!.date_attente
          ).getHours()
        );
        visitDate.setMinutes(
          new Date(
            this.patientVisitService.selectedWaitingListItem!.date_attente
          ).getMinutes()
        );
        visitDate.setSeconds(
          new Date(
            this.patientVisitService.selectedWaitingListItem!.date_attente
          ).getSeconds()
        );
      } else {
        visitDate.setHours(new Date(Date.now()).getHours());
        visitDate.setMinutes(new Date(Date.now()).getMinutes());
        visitDate.setSeconds(new Date(Date.now()).getSeconds());
      }
    }

    let attente_num;
    if (this.patientVisitService.selectedWaitingListItem) {
      attente_num =
        this.patientVisitService.selectedWaitingListItem?.num_attente!;
    }

    return new ConsultationRequest({
      patient_ref: this.patientInfos.reference,
      secteur_code: this.visitSectorControl.value.id,
      attente_num: attente_num,
      date_consultation: visitDate,
      // new Date(this.visitDateControl.value),
      hdm: this.diseaseHistoryControl.value,
      constante: new ConstanteRequest({
        poids: this.weightControl.value,
        taille: this.sizeControl.value,
        perimetre_cranien: this.pcControl.value,
        temperature: this.temperatureControl.value,
        frequence_respiratoire: this.frControl.value,
        poul: this.pulseControl.value,
        tension: this.tensionControl.value,
      }),
      actes: acts,
      motifs: motifs,
      diagnostics: diagnostics,
    });
  }

  saveVisitObservable() {
    if (this.visitInfosForm.invalid) {
      return;
    }

    const consultation = this.getPatientVisitFormData();

    console.log(JSON.stringify(consultation, null, 2));

    let createOrUpdate$;

    // DEFAULT - Executed if the visit/consultation has not been registered (without leaving the page)
    if (!this.consultationId) {
      createOrUpdate$ = this.patientVisitService.create(consultation);
    }
    // Executed if the visit/consultation has been registered without leaving the page
    else {
      createOrUpdate$ = this.patientVisitService.update(
        this.consultationId,
        consultation
      );
    }

    return createOrUpdate$.pipe(
      tap({
        next: (data) => {
          console.log(data, "\nHere");

          this.toastService.show({
            messages: ["La consultation a été enregistrée avec succès."],
            type: ToastType.Success,
          });

          // await this.medicalBaseRouter.navigateToPatientWaitingList();

          this.visitInfosForm.markAsPristine();
          // this.visitInfosForm.markAsUntouched()
        },
        error: (e) => {
          console.error(e);

          this.toastService.show({
            messages: [
              "Désolé, une erreur s'est produite lors de l'enregistrement de la consultation.",
            ],
            delay: 10000,
            type: ToastType.Error,
          });
        },
      })
    );
  }

  saveVisitInfos() {
    this.saveVisitObservable()?.subscribe();
  }

  saveVisitInfosAndLeave() {
    this.saveVisitObservable()?.subscribe(async (data) => {
      await this.medicalBaseRouter.navigateToPatientWaitingList();
    });
  }

  // SAVE VISIT/CONSULTATION MODIFICATIONS -------------------------------------
  registerVisitsModifications() {
    if (this.visitInfosForm.invalid) {
      return;
    }

    const consultation = this.getPatientVisitFormData();
    consultation.date_consultation = this.consultation!.date_consultation;

    console.log(JSON.stringify(consultation, null, 2));

    this.patientVisitService
      .update(this.consultation!.id, consultation)
      .pipe(
        tap({
          next: (data) => {
            console.log(data, "\nHere");

            this.toastService.show({
              messages: ["La consultation a été modifiée avec succès."],
              type: ToastType.Success,
            });

            // await this.medicalBaseRouter.navigateToPatientWaitingList();

            this.visitInfosForm.markAsPristine();
            // this.visitInfosForm.markAsUntouched()
          },
          error: (e) => {
            console.error(e);

            this.toastService.show({
              messages: [
                "Désolé, une erreur s'est produite lors de la modification de la consultation.",
              ],
              delay: 10000,
              type: ToastType.Error,
            });
          },
        })
      )
      .subscribe();
  }

  // ACTS FIELDS --------------------------------------------------------------------------------------------------------
  get actsFields() {
    return this.visitInfosForm.get("acts") as FormArray;
  }

  addActsField() {
    this.actsFields.push(new FormControl(null));
    console.log(this.actsFields);

    setTimeout(() => {
      document.body
        .querySelector("#actsSelects")
        ?.querySelector("input")
        ?.focus();
    }, 0);
  }

  removeActsField(index: number) {
    this.actsFields.removeAt(index);
    console.log(this.actsFields);
  }

  // MOTIFS FIELDS --------------------------------------------------------------------------------------------------------
  get motifsFields() {
    return this.visitInfosForm.get("motifs") as FormArray;
  }

  addMotifsField() {
    this.motifsFields.push(new FormControl(null));
    console.log(this.motifsFields);

    setTimeout(() => {
      document.body
        .querySelector("#motifsSelects")
        ?.querySelector("input")
        ?.focus();
    }, 0);
  }

  removeMotifsField(index: number) {
    this.motifsFields.removeAt(index);
    console.log(this.motifsFields);
  }

  // DIAGNOSTICS FIELDS --------------------------------------------------------------------------------------------------------
  get diagnosticsFields() {
    return this.visitInfosForm.get("diagnostics") as FormArray;
  }

  addDiagnosticsField() {
    this.diagnosticsFields.push(new FormControl(null));
    console.log(this.diagnosticsFields);

    setTimeout(() => {
      document.body
        .querySelector("#diagnosticsSelects")
        ?.querySelector("input")
        ?.focus();
    }, 0);
  }

  removeDiagnosticsField(index: number) {
    this.diagnosticsFields.removeAt(index);
    console.log(this.diagnosticsFields);
  }

  isVisitInfosFormDirty() {
    return this.visitInfosForm.dirty;
  }

  // PREVIOUS VISITS/CONSULTATIONS DISPLAY OR MODIFICATIONS -----------------------------------
  // Methods if using Component to display previous visits/consultations
  goToFirstVisit() {
    if (this.consultations.length === 0) {
      return this.activeIndex;
    }

    this.activeIndex = this.consultations.length - 1;

    console.log(JSON.stringify(this.consultation, null, 2));

    this.setVisitInfoFieldsInitialValues();

    return this.activeIndex;
  }

  goToPreviousVisit() {
    if (this.activeIndex < this.consultations.length - 1) {
      this.activeIndex++;

      console.log(JSON.stringify(this.consultation, null, 2));

      this.setVisitInfoFieldsInitialValues();
    }
    return this.activeIndex;
  }

  goToNextVisit() {
    if (this.activeIndex >= 1) {
      this.activeIndex--;

      console.log(JSON.stringify(this.consultation, null, 2));

      this.setVisitInfoFieldsInitialValues();
    }
    return this.activeIndex;
  }

  goToLastVisit() {
    this.activeIndex = 0;

    console.log(JSON.stringify(this.consultation, null, 2));

    this.setVisitInfoFieldsInitialValues();

    return this.activeIndex;
  }
  // -------------------------------------------------------------------------------
}
