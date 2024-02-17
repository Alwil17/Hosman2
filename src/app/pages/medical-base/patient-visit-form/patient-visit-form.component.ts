import { Component, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import {
  Observable,
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
import { SelectOption } from "src/app/models/extras/select.model";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { MedicalBaseRouterService } from "src/app/services/medical-base/router/medical-base-router.service";
import { calculateExactAge, isAdult } from "src/app/helpers/age-calculator";
import { parseIntOrZero } from "src/app/helpers/parsers";
import { ProfessionService } from "src/app/services/secretariat/patients/profession.service";
import { EmployerService } from "src/app/services/secretariat/patients/employer.service";
import { InsuranceService } from "src/app/services/secretariat/patients/insurance.service";
import { ParentRequest } from "src/app/models/secretariat/patients/requests/parent-request.model";
import { PatientVisitInfoRequest } from "src/app/models/secretariat/patients/requests/patient-visit-info-request.model";
import { ChronicDiseaseRequest } from "src/app/models/secretariat/patients/requests/chronic-disease-request.model";
import { Parent } from "src/app/models/secretariat/patients/parent.model";
import { MotifService } from "src/app/services/medical-base/motif.service";
import { DiagnosticService } from "src/app/services/medical-base/diagnostic.service";
import { ConsultationRequest } from "src/app/models/medical-base/requests/consultation-request.model";
import { ConstanteRequest } from "src/app/models/medical-base/requests/constante-request.model";
import { ActRequest } from "src/app/models/medical-base/requests/act-request.model";
import { MotifRequest } from "src/app/models/medical-base/requests/motif-request.model";
import { DiagnosticRequest } from "src/app/models/medical-base/requests/diagnostic-request.model";
import { SectorService } from "src/app/services/secretariat/shared/sector.service";
import { LoadingSpinnerService } from "src/app/services/secretariat/shared/loading-spinner.service";
import { TariffService } from "src/app/services/secretariat/shared/tariff.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";
import { Location } from "@angular/common";
import { IsNotDirty } from "src/app/guards/is-not-dirty.guard";
import { ConfirmModalComponent } from "src/app/shared/modals/confirm-modal/confirm-modal.component";
import { AppointmentFormModalComponent } from "../appointment-form-modal/appointment-form-modal.component";
import { AdultPatientBackgroundsModalComponent } from "./adult-patient-backgrounds-modal/adult-patient-backgrounds-modal.component";
import { ChildPatientBackgroundsModalComponent } from "./child-patient-backgrounds-modal/child-patient-backgrounds-modal.component";
import { SiblingsModalComponent } from "./siblings-modal/siblings-modal.component";
import { CoefficientSocialModalComponent } from "./coefficient-social-modal/coefficient-social-modal.component";

@Component({
  selector: "app-patient-visit-form",
  templateUrl: "./patient-visit-form.component.html",
  styleUrls: ["./patient-visit-form.component.scss"],
})
export class PatientVisitFormComponent implements OnInit, IsNotDirty {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  isPatientInfoCollapsed = false;
  isVisitFormCollapsed = true;

  // To set date min
  today = new Date();
  // .toLocaleDateString("fr-ca");

  // Visit form 1 controls
  // chronicDiseaseControl = new FormControl(null);
  statusControl = new FormControl(null);
  // backgroundControl = new FormControl(null);
  commentsControl = new FormControl(null);
  backgroundsControl = new FormControl(null);

  motherProfessionControl = new FormControl(null);
  motherTelControl = new FormControl(null);
  motherEmployerControl = new FormControl(null);
  motherInsuranceControl = new FormControl(null);
  motherBirthYearControl = new FormControl(null, [
    Validators.min(1800),
    Validators.max(this.today.getFullYear()),
  ]);
  motherAgeControl = new FormControl(null);

  fatherProfessionControl = new FormControl(null);
  fatherTelControl = new FormControl(null);
  fatherEmployerControl = new FormControl(null);
  fatherInsuranceControl = new FormControl(null);
  fatherBirthYearControl = new FormControl(null, [
    Validators.min(1800),
    Validators.max(this.today.getFullYear()),
  ]);
  fatherAgeControl = new FormControl(null);

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
  patientVisitForm2!: FormGroup;

  isFormSubmitted = false;

  activePatient!: Patient;
  isActivePatientAdult = false;

  professions!: SelectOption[];
  employers!: SelectOption[];
  insurances!: SelectOption[];

  chronicDiseases: SelectOption[] = [];

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
    public patientService: PatientService,
    private modalService: NgbModal,
    private professionService: ProfessionService,
    private employerService: EmployerService,
    private insuranceService: InsuranceService,
    private sectorService: SectorService,
    private tariffService: TariffService,
    private motifService: MotifService,
    private diagnosticService: DiagnosticService,
    private medicalBaseRouter: MedicalBaseRouterService,
    private patientVisitService: PatientVisitService,
    private loadingSpinnerService: LoadingSpinnerService,
    private toastService: ToastService,
    private location: Location
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Navette", active: true },
    ];

    // this.activePatient = this.patientService.getActivePatient();
    if (
      !this.patientVisitService.selectedWaitingListItem &&
      !this.patientVisitService.selectedPatient
    ) {
      this.medicalBaseRouter.navigateToPatientWaitingList();

      return;
    }

    if (this.patientVisitService.selectedWaitingListItem) {
      this.activePatient =
        this.patientVisitService.selectedWaitingListItem.patient;
    } else {
      this.activePatient = this.patientVisitService.selectedPatient!;
    }

    this.isActivePatientAdult = isAdult(this.activePatient.date_naissance);
    // const activePatientExactAge = calculateExactAge(
    //   new Date(this.activePatient.date_naissance)
    // );
    // const activePatientAge = parseIntOrZero(
    //   activePatientExactAge.split(" ")[0]
    // );
    // console.log(activePatientAge);
    // if (activePatientAge < 18) {
    //   this.isActivePatientAdult = true;
    // }

    this.patientVisitForm1 = new FormGroup({
      // chronicDiseaseControl: this.chronicDiseaseControl,
      chronicDiseases: new FormArray([new FormControl(null)]),
      statusControl: this.statusControl,
      // backgroundControl: this.backgroundControl,
      commentsControl: this.commentsControl,
      backgroundsControl: this.backgroundsControl,

      motherProfessionControl: this.motherProfessionControl,
      motherTelControl: this.motherTelControl,
      motherEmployerControl: this.motherEmployerControl,
      motherInsuranceControl: this.motherInsuranceControl,
      motherBirthYearControl: this.motherBirthYearControl,
      motherAgeControl: this.motherAgeControl,

      fatherProfessionControl: this.fatherProfessionControl,
      fatherTelControl: this.fatherTelControl,
      fatherEmployerControl: this.fatherEmployerControl,
      fatherInsuranceControl: this.fatherInsuranceControl,
      fatherBirthYearControl: this.fatherBirthYearControl,
      fatherAgeControl: this.fatherAgeControl,
    });

    this.patientVisitForm2 = new FormGroup({
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

    this.fetchPatientSelectData();

    this.fetchVisitSelectData();

    // this.onFormInputsChanges();
  }

  async isNotDirty(): Promise<boolean> {
    // if (
    //   !this.patientVisitService.selectedWaitingListItem &&
    //   !this.patientVisitService.selectedPatient
    // ) {
    //   return Promise.resolve(true);
    // }

    console.log("Form 1 is dirty : " + this.patientVisitForm1.dirty);
    console.log("Form 2 is dirty : " + this.patientVisitForm2.dirty);

    if (this.patientVisitForm1.dirty || this.patientVisitForm2.dirty) {
      // OPEN CONFIRMATION MODAL
      const confirmModalRef = this.modalService.open(ConfirmModalComponent, {
        size: "md",
        centered: true,
        keyboard: false,
        backdrop: "static",
      });

      confirmModalRef.componentInstance.message =
        "Vous n'avez pas enregistré vos modifications. Voulez-vous vraiment quitter la page ?";

      const isConfirmed = await firstValueFrom<boolean>(
        confirmModalRef.componentInstance.isConfirmed.asObservable()
      );

      // CLOSE CONFIRMATION MODAL
      confirmModalRef.close();

      return Promise.resolve(isConfirmed);
    } else {
      return Promise.resolve(true);
    }
  }

  // GET SELECTS DATA-------------------------------------------------------------------------------------------------------------------------

  fetchPatientSelectData() {
    forkJoin({
      professions: this.professionService.getAll(),
      employers: this.employerService.getAll(),
      insurances: this.insuranceService.getAll(),
    }).subscribe({
      next: (data) => {
        this.professions = data.professions.map((value) => ({
          id: value.id,
          text: value.denomination,
        }));

        this.employers = data.employers.map((value) => ({
          id: value.id,
          text: value.nom,
        }));

        this.insurances = data.insurances.map((value) => ({
          id: value.id,
          text: value.nom,
        }));

        this.setPatientInfoFieldsInitialValues();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

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

        this.onFormInputsChanges();
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
  setPatientInfoFieldsInitialValues() {
    const cdLength = this.activePatient.maladies?.length ?? 0;
    console.log(this.activePatient);

    let mother: Parent | undefined;
    let father: Parent | undefined;

    if (this.activePatient.parents) {
      const parents = this.activePatient.parents;

      parents.forEach((parent) => {
        if (parent.type === "mere") {
          mother = parent;
        } else if (parent.type === "pere") {
          father = parent;
        }
      });
    }

    for (let i = 0; i < cdLength - 1; i++) {
      this.addChronicDiseaseField();
    }

    this.activePatient.maladies?.map((value) => {
      this.chronicDiseases.push({
        id: value.id,
        text: value.nom,
      });
    });

    console.log(this.chronicDiseases);

    // const cd = this.activePatient.maladies!

    if (
      this.activePatient.maladies != null &&
      this.activePatient.maladies?.length !== 0
    ) {
      this.chronicDiseasesFields.controls.forEach((control, index) => {
        control.setValue({
          id: this.activePatient.maladies![index].id,
          text: this.activePatient.maladies![index].nom,
        });
      });
    }

    this.commentsControl.setValue(this.activePatient.commentaire);
    this.backgroundsControl.setValue(this.activePatient.antecedent);

    if (mother) {
      this.motherProfessionControl.setValue(
        this.professions.find((value) => {
          return (
            value.text.toLowerCase() ===
            mother?.profession.denomination.toLowerCase()
          );
        })
      );
      this.motherTelControl.setValue(mother?.telephone);
      this.motherEmployerControl.setValue(
        this.employers.find((value) => {
          return (
            value.text.toLowerCase() === mother?.employeur.nom.toLowerCase()
          );
        })
      );
      this.motherInsuranceControl.setValue(
        this.insurances.find((value) => {
          return (
            value.text.toLowerCase() === mother?.assurance.nom.toLowerCase()
          );
        })
      );
      this.motherBirthYearControl.setValue(mother?.annee_naissance);
    }

    if (father) {
      this.fatherProfessionControl.setValue(
        this.professions.find((value) => {
          return (
            value.text.toLowerCase() ===
            father?.profession.denomination.toLowerCase()
          );
        })
      );
      this.fatherTelControl.setValue(father?.telephone);
      this.fatherEmployerControl.setValue(
        this.employers.find((value) => {
          return (
            value.text.toLowerCase() === father?.employeur.nom.toLowerCase()
          );
        })
      );
      this.fatherInsuranceControl.setValue(
        this.insurances.find((value) => {
          return (
            value.text.toLowerCase() === father?.assurance.nom.toLowerCase()
          );
        })
      );
      this.fatherBirthYearControl.setValue(father?.annee_naissance);
    }
  }

  setVisitInfoFieldsInitialValues() {
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

    // this.waitingDetails.visitActs = "C";
    // this.waitingDetails.visitTotalCost = 0;

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

      // this.visitSectorControl.setValue({
      //   id: this.patientVisitService.selectedWaitingListItem?.secteur?.code,
      //   text: this.patientVisitService.selectedWaitingListItem?.secteur?.libelle,
      // });
    }
  }

  // OPEN PATIENT INFOS MODALS -------------------------------------------------------------------------------------------------------
  openBackgroundsModal() {
    let backgroundsModalRef;

    if (this.isActivePatientAdult) {
      backgroundsModalRef = this.modalService.open(
        AdultPatientBackgroundsModalComponent,
        {
          size: "lg",
          centered: true,
          scrollable: true,
          backdrop: "static",
        }
      );
    } else {
      backgroundsModalRef = this.modalService.open(
        ChildPatientBackgroundsModalComponent,
        {
          size: "lg",
          centered: true,
          scrollable: true,
          backdrop: "static",
        }
      );
    }
  }

  openSiblingsModal() {
    const siblingsModalRef = this.modalService.open(SiblingsModalComponent, {
      size: "xl",
      centered: true,
      scrollable: true,
      backdrop: "static",
    });

    siblingsModalRef.componentInstance.patientInfos = this.activePatient;
  }

  openCoefficientSocialModal() {
    const coefficientSocialModalRef = this.modalService.open(
      CoefficientSocialModalComponent,
      {
        size: "md",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );
  }

  // SAVE PATIENT INFO ---------------------------------------------------------------------------------------------------------------
  savePatientInfo() {
    let chronicDiseases: ChronicDiseaseRequest[] = [];
    this.chronicDiseasesFields.controls.map((control) =>
      chronicDiseases.push({ maladie: control.value?.text })
    );

    let parents: ParentRequest[] = [];

    let mother = new ParentRequest({
      profession: this.motherProfessionControl.value?.text,
      employeur: this.motherEmployerControl.value?.text,
      assurance: this.motherInsuranceControl.value?.text,
      telephone: this.motherTelControl.value,
      annee_naissance: this.motherBirthYearControl.value,
      sexe: "F",
      type: "mere",
    });

    const father = new ParentRequest({
      profession: this.fatherProfessionControl.value?.text,
      employeur: this.fatherEmployerControl.value?.text,
      assurance: this.fatherInsuranceControl.value?.text,
      telephone: this.fatherTelControl.value,
      annee_naissance: this.fatherBirthYearControl.value,
      sexe: "M",
      type: "pere",
    });

    parents.push(mother, father);

    const patientVisitInfo = new PatientVisitInfoRequest({
      commentaire: this.commentsControl.value,
      antecedent: this.backgroundsControl.value,
      maladies: chronicDiseases,
      parents: parents,
    });

    console.log(JSON.stringify(patientVisitInfo, null, 2));

    this.patientService
      .updateVisitInfo(this.activePatient.id, patientVisitInfo)
      .subscribe({
        next: (data) => {
          // this.setFieldsInitialValues();
        },
        error: (error) => {
          console.log(error);
        },
      });
  }

  // ON FORM INPUTS CHANGES -----------------------------------------------------------------------------------------------------------
  onFormInputsChanges() {
    this.visitDateControl.valueChanges.subscribe((value) => {
      this.waitingDetails.visitDate = new Date();
    });
  }

  // SAVE CONSULTATION/VISIT INFOS --------------------------------------------------------------------------------------------------
  saveVisitInfos() {
    if (this.patientVisitForm2.invalid) {
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
      patient_ref: this.activePatient.reference,
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

        await this.medicalBaseRouter.navigateToPatientWaitingList();
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  // CHRONIC DISEASES FIELDS --------------------------------------------------------------------------------------------------------
  get chronicDiseasesFields() {
    return this.patientVisitForm1.get("chronicDiseases") as FormArray;
  }

  addChronicDiseaseField() {
    this.chronicDiseasesFields.push(new FormControl(null));
    console.log(this.chronicDiseasesFields);
  }

  removeChronicDiseaseField(index: number) {
    this.chronicDiseasesFields.removeAt(index);
    console.log(this.chronicDiseasesFields);
  }

  // ACTS FIELDS --------------------------------------------------------------------------------------------------------
  get actsFields() {
    return this.patientVisitForm2.get("acts") as FormArray;
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
    return this.patientVisitForm2.get("motifs") as FormArray;
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
    return this.patientVisitForm2.get("diagnostics") as FormArray;
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

  // OPEN APPOINTMENT MODAL ------------------------------------------------------------------------------------------------------
  openAppointmentModal() {
    const appointmentFormModal = this.modalService.open(
      AppointmentFormModalComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );

    // personToContactModal.componentInstance.data = this.personToContactData;

    // personToContactModal.componentInstance.formData.subscribe(
    //   (formData: PersonToContactRequest) => {
    //     this.personToContactData = new PersonToContactRequest(
    //       formData.nom,
    //       formData.prenoms,
    //       formData.tel,
    //       formData.adresse
    //     );

    //     this.personToContactControl.setValue(
    //       formData.nom +
    //         ", " +
    //         formData.prenoms +
    //         ", " +
    //         formData.tel +
    //         ", " +
    //         formData.adresse
    //     );

    //     personToContactModal.close();
    //   }
    // );
  }

  goToPreviousPage() {
    this.medicalBaseRouter.navigateToPatientVisitsSummary();
    // this.location.back(); // Some bug when using it with canDeactive guard. Would go back twice
  }
}
