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
import { Subscription, firstValueFrom } from "rxjs";
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
    setTimeout(() => {
      this.patientInfosFormComponent.patientInfosForm$.subscribe((data) =>
        console.log("Nexted")
      );
    }, 5000);
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

  goToPreviousPage() {
    this.medicalBaseRouter.navigateToPatientVisitsSummary(
      this.activePatient.id
    );
    // this.location.back(); // Some bug when using it with canDeactive guard. Would go back twice
  }
}
