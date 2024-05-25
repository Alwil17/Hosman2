import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PatientVisitService } from '@services/medical-base/patient-visit.service';
import { DoctorService } from '@services/secretariat/shared/doctor.service';
import { SectorService } from '@services/secretariat/shared/sector.service';
import { ToastService } from '@services/secretariat/shared/toast.service';
import { Subscription, forkJoin } from 'rxjs';
import { SelectOption } from 'src/app/models/extras/select.model';
import { ToastType } from 'src/app/models/extras/toast-type.model';
import { Consultation } from 'src/app/models/medical-base/consultation.model';
import { PdfModalComponent } from 'src/app/shared/modals/pdf-modal/pdf-modal.component';

@Component({
  selector: 'app-special-requests-page',
  templateUrl: './special-requests-page.component.html',
  styleUrls: ['./special-requests-page.component.scss']
})
export class SpecialRequestsPageComponent implements OnInit {

  breadCrumbItems!: Array<{}>;
  isTextTerm = true;
  
  // To set date min
  today = new Date().toLocaleDateString("fr-ca");

  startDateControl = new FormControl(this.today);
  endDateControl = new FormControl(this.today);

  requestsList: Consultation[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.requestsList.length;
  requestsListCut: Consultation[] = [];
  
  typeSelected = { id: "general", text: "Générale" };
  searchControl = new FormControl(this.typeSelected);

  searchCriteria: SelectOption[] = [
    {
      id: "general",
      text: "Générale",
    },
    {
      id: "sector",
      text: "Par secteur",
    },
    {
      id: "doctor",
      text: "Par Docteur",
    }
  ];

  sectorControl = new FormControl();
  sectors!: SelectOption[];
  doctorControl = new FormControl();
  doctors!: SelectOption[];

  routeParamChangesSubscription!: Subscription;
  
  constructor(
    private route: ActivatedRoute,
    private patientVisitService: PatientVisitService,
    private sectorService: SectorService,
    private doctorService: DoctorService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Requêtes spéciales", active: true },
    ];

    // Getting period from route params and load list
    this.routeParamChangesSubscription = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        console.log(params.get("type"))
        const typeFirstOption = this.searchCriteria.find((value) => String(params.get("type")) == value.id);
        
        this.typeSelected = { id: typeFirstOption?.id, text: String(typeFirstOption?.text) };
        
        this.searchControl.setValue(this.typeSelected);
      }
    );

    this.fetchSelectData();
    this.refreshRequestsList();
  }

  fetchSelectData() {
    forkJoin({
      sectors: this.sectorService.getAll(),
      doctors: this.doctorService.getAll(),
    }).subscribe({
      next: (data) => {
        this.sectors = data.sectors.map((value) => ({
          id: value.code,
          text: value.libelle,
        }));

        this.doctors = data.doctors.map((value) => ({
          id: value.matricule,
          text: value.fullName,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    })
    
  }

  refreshRequestsList() {
    const minDate = new Date(this.startDateControl.value);
    const maxDate = this.endDateControl.value
      ? new Date(this.endDateControl.value)
      : new Date(this.startDateControl.value);
    
      let sectorCode;
      if(this.sectorControl.value){ 
        sectorCode = this.sectors.find(
          (value) => this.sectorControl.value?.id == value.id
        )?.id
      }else{
        sectorCode = ""
      }

      let doctorCode;
      if(this.doctorControl.value){ 
        doctorCode = this.doctors.find(
          (value) => this.doctorControl.value?.id == value.id
        )?.id
      }else{
        doctorCode = ""
      }

      this.patientVisitService.searchBy({
        minDate: minDate,
        maxDate: maxDate,
        sector: sectorCode,
        doctor: doctorCode
      }).subscribe({
        next: (data) => {
          this.requestsList = data;

          this.refreshRequests();
        },
        error: (error) => {
          console.error(error);

          this.toastService.show({
            messages: [
              "Une erreur s'est produite lors du rafraîchissment de la liste.",
            ],
            delay: 10000,
            type: ToastType.Error,
          });
        },
      });
      
  }

  refreshRequests() {
    this.collectionSize = this.requestsList.length;

    this.requestsListCut = this.requestsList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  printReport(vue: string) {
    const minDate = new Date(this.startDateControl.value);
    const maxDate = this.endDateControl.value
      ? new Date(this.endDateControl.value)
      : new Date(this.startDateControl.value);
    
      let sectorCode;
      if(this.sectorControl.value){ 
        sectorCode = this.sectors.find(
          (value) => this.sectorControl.value?.id == value.id
        )?.id
      }else{
        sectorCode = ""
      }

      let doctorCode;
      if(this.doctorControl.value){ 
        doctorCode = this.doctors.find(
          (value) => this.doctorControl.value?.id == value.id
        )?.id
      }else{
        doctorCode = ""
      }


    this.patientVisitService.printConsultationReport({
        minDate: minDate,
        maxDate: maxDate,
        sector: sectorCode,
        doctor: doctorCode,
        vue: vue
      }).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Génération de l'état."],
          type: ToastType.Success,
        });

        const pdfModalRef = this.modalService.open(PdfModalComponent, {
          size: "xl",
          centered: true,
          scrollable: true,
          backdrop: "static",
        });

        pdfModalRef.componentInstance.title = "Reçu";
        pdfModalRef.componentInstance.pdfSrc = data;
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Echec de la génération de l'état."],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }
}
