import { Component, OnInit } from "@angular/core";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { MedicalBaseRouterService } from "src/app/services/medical-base/router/medical-base-router.service";

@Component({
  selector: "app-patient-list-page",
  templateUrl: "./patient-list-page.component.html",
  styleUrls: ["./patient-list-page.component.scss"],
})
export class PatientListPageComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  constructor(
    private patientVisitService: PatientVisitService,
    private medicalBaseRouter: MedicalBaseRouterService
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Liste des patients", active: true },
    ];
  }

  goToPatientVisitsSummary(patient: Patient) {
    this.patientVisitService.selectPatient(patient);

    // this.patientVisitService
    //   .startVisit(waitingListItem.patient.reference)
    //   .subscribe({
    //     next: async (data) => {
    //       console.log(data, "\nHere");

    // await
    this.medicalBaseRouter.navigateToPatientVisitsSummary();
    //   },
    //   error: (e) => {
    //     console.error(e);

    //     this.toastService.show({
    //       delay: 10000,
    //       type: ToastType.Error,
    //     });
    //   },
    // });
  }
}
