import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Consultation } from "src/app/models/medical-base/consultation.model";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { MedicalBaseRouterService } from "src/app/services/medical-base/router/medical-base-router.service";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

type Visit = {
  number: string;
  date: string;
  time: string;
  weight: number;
  size: number;
  tension: string;
  temperature: number;
  pc: any;
  acts: any[];
};

const VISITS: Visit[] = [
  {
    number: "012345",
    date: "01-03-2022",
    time: "15:03:59",
    weight: 75,
    size: 178,
    tension: "0/0",
    temperature: 38,
    pc: 0,
    acts: ["RL"],
  },
  {
    number: "015891",
    date: "01-01-2023",
    time: "11:31:46",
    weight: 75,
    size: 178,
    tension: "0/0",
    temperature: 38,
    pc: 0,
    acts: ["C"],
  },
  {
    number: "017472",
    date: "28-09-2023",
    time: "17:43:02",
    weight: 75,
    size: 178,
    tension: "0/0",
    temperature: 38,
    pc: 0,
    acts: ["C"],
  },
];

@Component({
  selector: "app-patient-visits-summary-page",
  templateUrl: "./patient-visits-summary-page.component.html",
  styleUrls: ["./patient-visits-summary-page.component.scss"],
})
export class PatientVisitsSummaryPageComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  // To set date min
  today = new Date().toLocaleDateString("fr-ca");

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

  constructor(
    private medicalBaseRouter: MedicalBaseRouterService,
    // private patientService: PatientService,
    private patientVisitService: PatientVisitService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Consultations antérieures", active: true },
    ];

    if (!this.patientVisitService.selectedWaitingListItem) {
      this.medicalBaseRouter.navigateToPatientWaitingList();

      return;
    }

    this.lastNameControl.setValue(
      this.patientVisitService.selectedWaitingListItem.patient.nom
    );
    this.firstNameControl.setValue(
      this.patientVisitService.selectedWaitingListItem.patient.prenoms
    );
    this.dateOfBirthControl.setValue(
      new Date(
        this.patientVisitService.selectedWaitingListItem.patient.date_naissance
      ).toLocaleDateString("fr-ca")
    );
    this.genderControl.setValue(
      this.genders.find(
        (value) =>
          value.short ==
          this.patientVisitService.selectedWaitingListItem!.patient.sexe
      )
    );

    this.patientVisitService
      .getConsultationsByPatientReference(
        this.patientVisitService.selectedWaitingListItem.patient.reference
      )
      .subscribe({
        next: async (data) => {
          console.log(data, "\nHere");

          this.visitsList = data;

          this.refreshVisitsListTable();
        },
        error: (e) => {
          console.error(e);

          this.toastService.show({
            delay: 10000,
            type: ToastType.Error,
          });
        },
      });
    // this.refreshVisitsList();
    // this.refreshVisitsListTable();
  }

  goToPatientVisitForm() {
    this.medicalBaseRouter.navigateToPatientVisitForm();
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

  openPreviousVisitsModal() {}
}
