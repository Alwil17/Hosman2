import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Consultation } from "src/app/models/medical-base/consultation.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { MedicalBaseRouterService } from "src/app/services/medical-base/router/medical-base-router.service";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { PatientVisitFormModalComponent } from "../patient-visit-form-modal/patient-visit-form-modal.component";
import { Location } from "@angular/common";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { Observable, Subscription, merge, tap } from "rxjs";

@Component({
  selector: "app-patient-visits-summary-page",
  templateUrl: "./patient-visits-summary-page.component.html",
  styleUrls: ["./patient-visits-summary-page.component.scss"],
})
export class PatientVisitsSummaryPageComponent implements OnInit, OnDestroy {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  // To set date min
  today = new Date().toLocaleDateString("fr-ca");

  activePatient!: Patient;

  // Activity form controls
  lastNameControl = new FormControl(null);
  firstNameControl = new FormControl(null);

  dateOfBirthControl = new FormControl(this.today);
  genderControl = new FormControl(null);

  // Activity form group
  patientVisitsForm: FormGroup = new FormGroup({});

  visitsList: Consultation[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.visitsList.length;
  visitsListCut: Consultation[] = [];

  genders = [
    { id: 1, text: "Masculin", short: "M" },
    { id: 2, text: "Féminin", short: "F" },
    { id: 3, text: "Indéterminé", short: "I" },
  ];

  routeParamChangesSubscription!: Subscription;

  constructor(
    private medicalBaseRouter: MedicalBaseRouterService,
    // private patientService: PatientService,
    private patientVisitService: PatientVisitService,
    private toastService: ToastService,
    private modalService: NgbModal,
    private location: Location,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Consultations antérieures", active: true },
    ];

    this.patientVisitsForm = new FormGroup({
      chronicDiseases: new FormArray([]),
    });

    // Getting patient id from route params and patient from that id
    this.routeParamChangesSubscription = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        const patientId = Number(params.get("patientId"));

        this.patientVisitService.getPatientById(patientId).subscribe({
          next: (data) => {
            // Setting active patient
            this.activePatient = data;

            // Displaying patient infos in fields
            this.initPatientVisitsForm();

            // Getting patient list of consultations/visits
            this.getPatientConsultations().subscribe();
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

    // if (
    //   !this.patientVisitService.selectedWaitingListItem &&
    //   !this.patientVisitService.selectedPatient
    // ) {
    //   this.medicalBaseRouter.navigateToPatientWaitingList();

    //   return;
    // }

    // if (this.patientVisitService.selectedWaitingListItem) {
    //   this.activePatient =
    //     this.patientVisitService.selectedWaitingListItem.patient;

    // } else {
    //   this.activePatient = this.patientVisitService.selectedPatient!;
    // }
    // console.log('--- '+JSON.stringify(this.activePatient, null, 2));

    // this.refreshVisitsList();
    // this.refreshVisitsListTable();
  }

  ngOnDestroy(): void {
    this.routeParamChangesSubscription.unsubscribe();
  }

  initPatientVisitsForm() {
    this.lastNameControl.setValue(this.activePatient.nom);
    this.firstNameControl.setValue(this.activePatient.prenoms);
    this.dateOfBirthControl.setValue(
      new Date(this.activePatient.date_naissance).toLocaleDateString("fr-ca")
    );
    this.genderControl.setValue(
      this.genders.find((value) => value.short == this.activePatient.sexe)
    );

    // Chronic diseases fields
    const chronicDiseases = this.activePatient.maladies;

    if (!chronicDiseases) {
      return;
    }

    console.log(JSON.stringify(chronicDiseases, null, 2));

    for (let i = 0; i < chronicDiseases.length; i++) {
      this.addChronicDiseasesField(chronicDiseases[i].nom);
    }
  }

  getPatientConsultations() {
    return this.patientVisitService
      .getConsultationsByPatientReference(this.activePatient.reference)
      .pipe(
        tap({
          next: async (data) => {
            console.log(data, "\nHere");

            this.visitsList = data;

            this.refreshVisitsListTable();
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
      );
  }

  goToPatientVisitForm() {
    this.medicalBaseRouter.navigateToPatientVisitForm(this.activePatient.id);
  }

  refreshVisitsList() {
    // this.waitingListService.getAll().subscribe({
    //   next: (data) => {
    //     this.toastService.show({
    //       messages: ["Rafraîchissement de la liste."],
    //       type: ToastType.Success,
    //     });
    //     this.visitsList = data;
    //     this.refreshVisitsListTable();
    //   },
    //   error: (e) => {
    //     console.error(e);
    //     this.toastService.show({
    //       messages: [
    //         "Une erreur s'est produite lors du rafraîchissment de la liste.",
    //       ],
    //       delay: 10000,
    //       type: ToastType.Error,
    //     });
    //   },
    // });
  }

  refreshVisitsListTable() {
    this.collectionSize = this.visitsList.length;

    this.visitsListCut = this.visitsList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  openPreviousVisitsModal(selectedIndex: number) {
    const patientVisitFormModal = this.modalService.open(
      PatientVisitFormModalComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );

    patientVisitFormModal.componentInstance.consultations = this.visitsList;
    patientVisitFormModal.componentInstance.activeIndex = selectedIndex;
    patientVisitFormModal.componentInstance.patientInfos = this.activePatient;

    merge(
      patientVisitFormModal.closed,
      patientVisitFormModal.dismissed
    ).subscribe({
      next: (value) => {
        this.getPatientConsultations().subscribe();
      },
    });
  }

  goToPreviousPage() {
    if (this.patientVisitService.selectedWaitingListItem) {
      this.medicalBaseRouter.navigateToPatientWaitingList();
    } else {
      this.medicalBaseRouter.navigateToPatientList();
    }
    // this.location.back();
  }

  // FORMS FIELDS --------------------------------------------------------------------------------------------------------
  get chronicDiseasesFields() {
    return this.patientVisitsForm.get("chronicDiseases") as FormArray;
  }

  addChronicDiseasesField(value: string) {
    this.chronicDiseasesFields.push(new FormControl(value));
  }

  removeChronicDiseasesField(index: number) {
    this.chronicDiseasesFields.removeAt(index);
    console.log(this.chronicDiseasesFields);
  }
}
