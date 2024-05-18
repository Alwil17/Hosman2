import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { FormGroup } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { Subscription, firstValueFrom, tap } from "rxjs";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { MedicalBaseRouterService } from "src/app/services/medical-base/router/medical-base-router.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Location } from "@angular/common";
import { IsNotDirty } from "src/app/guards/is-not-dirty.guard";
import { ConfirmModalComponent } from "src/app/shared/modals/confirm-modal/confirm-modal.component";
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { PatientInfosFormComponent } from "./patient-infos-form/patient-infos-form.component";
import { VisitInfosFormComponent } from "./visit-infos-form/visit-infos-form.component";
import { MultiChoicesModalComponent } from "src/app/shared/modals/multi-choices-modal/multi-choices-modal.component";
import {
  ButtonColorClass,
  MutliChoicesButtonProps,
} from "src/app/models/extras/multi-choices-button-props.model";
import { PatientFormModalComponent } from "../../secretariat/patients/patient-form-modal/patient-form-modal.component";
import { PatientVisitFormModalComponent } from "../patient-visit-form-modal/patient-visit-form-modal.component";

@Component({
  selector: "app-patient-visit-form",
  templateUrl: "./patient-visit-form.component.html",
  styleUrls: ["./patient-visit-form.component.scss"],
})
export class PatientVisitFormComponent
  implements OnInit, AfterViewInit, OnDestroy, IsNotDirty
{
  @ViewChild(PatientInfosFormComponent)
  patientInfosFormComponent!: PatientInfosFormComponent;

  @ViewChild(VisitInfosFormComponent)
  visitInfosFormComponent?: VisitInfosFormComponent;

  // bread crumb items
  breadCrumbItems!: Array<{}>;

  isPatientInfoCollapsed = false;
  isVisitFormCollapsed = true;

  activePatient!: Patient;

  routeParamChangesSubscription!: Subscription;

  constructor(
    public patientService: PatientService,
    private modalService: NgbModal,
    private medicalBaseRouter: MedicalBaseRouterService,
    private patientVisitService: PatientVisitService,
    private toastService: ToastService,
    private location: Location,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Navette", active: true },
    ];

    // Getting patient id from route params and patient from that id
    this.routeParamChangesSubscription = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        const patientId = Number(params.get("patientId"));

        this.patientVisitService.getPatientById(patientId).subscribe({
          next: (data) => {
            // Setting active patient
            this.activePatient = data;
          },
          error: (e) => {
            console.log(e);

            this.toastService.show({
              messages: ["Désolé, une erreur s'est produite."],
              delay: 10000,
              type: ToastType.Error,
            });
          },
        });
      }
    );
  }

  ngOnDestroy(): void {
    this.routeParamChangesSubscription.unsubscribe();
  }

  ngAfterViewInit(): void {
    // setTimeout(() => {
    //   this.patientInfosFormComponent.patientInfosForm$.subscribe((data) =>
    //     console.log("Nexted")
    //   );
    // }, 5000);
  }

  async isNotDirty(): Promise<boolean> {
    // if (
    //   !this.patientVisitService.selectedWaitingListItem &&
    //   !this.patientVisitService.selectedPatient
    // ) {
    //   return Promise.resolve(true);
    // }

    console.log(
      "Form 1 is dirty : " +
        this.patientInfosFormComponent.isPatientInfosFormDirty()
    );
    console.log(
      "Form 2 is dirty : " +
        this.visitInfosFormComponent?.isVisitInfosFormDirty()
    );

    if (
      this.patientInfosFormComponent.isPatientInfosFormDirty() ||
      this.visitInfosFormComponent?.isVisitInfosFormDirty()
    ) {
      // OPEN CONFIRMATION MODAL
      // const confirmModalRef = this.modalService.open(ConfirmModalComponent, {
      //   size: "md",
      //   centered: true,
      //   keyboard: false,
      //   backdrop: "static",
      // });

      // confirmModalRef.componentInstance.message =
      //   "Vous n'avez pas enregistré vos modifications. Voulez-vous vraiment quitter la page ?";

      // const isConfirmed = await firstValueFrom<boolean>(
      //   confirmModalRef.componentInstance.isConfirmed.asObservable()
      // );

      // // CLOSE CONFIRMATION MODAL
      // confirmModalRef.close();

      // OPEN CHOICES MODAL
      const choicesModalRef = this.modalService.open(
        MultiChoicesModalComponent,
        {
          size: "lg",
          centered: true,
          keyboard: false,
          backdrop: "static",
        }
      );

      choicesModalRef.componentInstance.messages = [
        "Vous n'avez pas enregistré vos modifications.",
        "Voulez-vous vraiment quitter la page ?",
      ];

      const buttons: MutliChoicesButtonProps[] = [
        {
          text: "Annuler",
          buttonColorClass: ButtonColorClass.OUTLINE_DARK,
        },
        {
          text: "Quitter sans sauvegarder",
          buttonColorClass: ButtonColorClass.WARNING,
        },
        {
          text: "Sauvegarder et quitter",
          buttonColorClass: ButtonColorClass.SUCCESS,
          hasSaveIcon: true,
        },
      ];
      choicesModalRef.componentInstance.buttons = buttons;

      const choiceIndex = await firstValueFrom<number>(
        choicesModalRef.componentInstance.choiceIndex.asObservable()
      );

      if (choiceIndex === 0) {
        choicesModalRef.close();

        return Promise.resolve(false);
      } else if (choiceIndex === 1) {
        choicesModalRef.close();

        return Promise.resolve(true);
      } else if (choiceIndex === 2) {
        await firstValueFrom<any>(
          this.patientInfosFormComponent.savePatientInfosObservable()
        ).catch((e) => console.log(e));

        if (this.visitInfosFormComponent) {
          await firstValueFrom<any>(
            this.visitInfosFormComponent.saveVisitObservable()!
          );
        }

        // CLOSE CHOICES MODAL
        choicesModalRef.close();

        return Promise.resolve(true);
      }

      return Promise.resolve(false);
    } else {
      return Promise.resolve(true);
    }
  }

  goToPreviousPage() {
    this.medicalBaseRouter.navigateToPatientVisitsSummary(
      this.activePatient.id
    );
    // this.location.back(); // Some bug when using it with canDeactive guard. Would go back twice
  }

  openPatientModificationModal() {
    const patientModifyModalRef = this.modalService.open(
      PatientFormModalComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        backdrop: "static",
        keyboard: false,
      }
    );

    patientModifyModalRef.componentInstance.title =
      "Modifier les informations du patient";
    patientModifyModalRef.componentInstance.patientInfos = this.activePatient;

    patientModifyModalRef.componentInstance.isPatientModified.subscribe(
      (isPatientModified: boolean) => {
        if (isPatientModified) {
          patientModifyModalRef.close();

          this.patientVisitService
            .getPatientById(this.activePatient.id)
            .subscribe({
              next: (data) => {
                // Setting active patient
                this.activePatient = data;
              },
              error: (e) => {
                console.log(e);

                this.toastService.show({
                  messages: ["Désolé, une erreur s'est produite."],
                  delay: 10000,
                  type: ToastType.Error,
                });
              },
            });
        }
      }
    );
  }

  openPreviousVisitsModal() {
    this.patientVisitService
      .getConsultationsByPatientReference(this.activePatient.reference)
      .pipe(
        tap({
          next: async (data) => {
            console.log(data, "\nHere");

            const patientVisitFormModal = this.modalService.open(
              PatientVisitFormModalComponent,
              {
                size: "xl",
                centered: true,
                scrollable: true,
                backdrop: "static",
              }
            );

            patientVisitFormModal.componentInstance.consultations = data;
            patientVisitFormModal.componentInstance.patientInfos =
              this.activePatient;
          },
          error: (e) => {
            console.log(e);

            this.toastService.show({
              messages: ["Désolé, une erreur s'est produite."],
              delay: 10000,
              type: ToastType.Error,
            });
          },
        })
      )
      .subscribe();
  }

  async toggleVisitDisplay() {
    if (
      this.isVisitFormCollapsed === false &&
      this.visitInfosFormComponent?.isVisitInfosFormDirty() === true
    ) {
      // OPEN CONFIRMATION MODAL
      const confirmModalRef = this.modalService.open(ConfirmModalComponent, {
        size: "md",
        centered: true,
        keyboard: false,
        backdrop: "static",
      });

      confirmModalRef.componentInstance.message =
        "Vous n'avez pas enregistré les informations de consultation. Voulez-vous vraiment annuler la consultation ?";

      confirmModalRef.componentInstance.cancelButtonText = "Non";
      confirmModalRef.componentInstance.confirmButtonText = "Oui";

      const isConfirmed = await firstValueFrom<boolean>(
        confirmModalRef.componentInstance.isConfirmed.asObservable()
      );

      if (isConfirmed) {
        this.isVisitFormCollapsed = !this.isVisitFormCollapsed;
        this.isPatientInfoCollapsed = !this.isPatientInfoCollapsed;
      }

      // CLOSE CONFIRMATION MODAL
      confirmModalRef.close();

      return;
    }

    this.isVisitFormCollapsed = !this.isVisitFormCollapsed;
    this.isPatientInfoCollapsed = !this.isPatientInfoCollapsed;
  }
}
