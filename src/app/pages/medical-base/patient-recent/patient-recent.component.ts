import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { PatientVisitService } from '@services/medical-base/patient-visit.service';
import { MedicalBaseRouterService } from '@services/medical-base/router/medical-base-router.service';
import { ToastService } from '@services/secretariat/shared/toast.service';
import { Subscription, tap } from 'rxjs';
import { SelectOption } from 'src/app/models/extras/select.model';
import { ToastType } from 'src/app/models/extras/toast-type.model';
import { Consultation } from 'src/app/models/medical-base/consultation.model';

@Component({
  selector: 'app-patient-recent',
  templateUrl: './patient-recent.component.html',
  styleUrls: ['./patient-recent.component.scss']
})
export class PatientRecentComponent implements OnInit, OnDestroy {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  isTextTerm = true;

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  period = "today"

  dateTermControl = new FormControl(this.today);

  // Pagination handling variables
  page = 1;
  pageSize = 10;

  searchCriteria: SelectOption[] = [
    {
      id: "fullname",
      text: "Nom et prénoms",
    },
    {
      id: "firstname",
      text: "Prénoms",
    },
    {
      id: "reference",
      text: "Référence",
    },
    {
      id: "dob",
      text: "Date de naissance",
    },
    {
      id: "doc",
      text: "Date d'entrée",
    },
  ];

  searchControl = new FormControl("");

  recents: Consultation[] = [];
  

  searchCriterionControl = new FormControl(this.searchCriteria[0]);

  searchedConsults: Consultation[] = [];
  
  collectionSize = this.searchedConsults.length;

  routeParamChangesSubscription!: Subscription;
  
  constructor(
    private patientVisitService: PatientVisitService,
    private medicalBaseRouter: MedicalBaseRouterService,
    private route: ActivatedRoute,
    private toastService: ToastService,
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Patients récents", active: true },
    ];

    // Getting period from route params and load list
    this.routeParamChangesSubscription = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        this.period = String(params.get("period"));

        this.getRecentConsultations().subscribe()
      }
    );
  }
  
  ngOnDestroy(): void {
    this.routeParamChangesSubscription.unsubscribe();
  }

  refreshPatientRecents() {
    this.collectionSize = this.recents.length;

    this.recents = this.recents
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  getRecentConsultations() {
    return this.patientVisitService
      .getConsultationsByPeriod(this.period)
      .pipe(
        tap({
          next: async (data) => {
            console.log(data, "\nHere");

            this.recents = data;

            this.refreshPatientRecents();
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
}
