import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PatientVisitService } from '@services/medical-base/patient-visit.service';
import { InsuranceDebtService } from '@services/secretariat/activities/insurance-debt.service';
import { DoctorService } from '@services/secretariat/shared/doctor.service';
import { ToastService } from '@services/secretariat/shared/toast.service';
import { Subscription, forkJoin } from 'rxjs';
import { SelectOption } from 'src/app/models/extras/select.model';
import { ToastType } from 'src/app/models/extras/toast-type.model';
import { PdfModalComponent } from 'src/app/shared/modals/pdf-modal/pdf-modal.component';

@Component({
  selector: 'app-req-pec-page',
  templateUrl: './req-pec-page.component.html',
  styleUrls: ['./req-pec-page.component.scss']
})
export class ReqPecPageComponent implements OnInit {
  breadCrumbItems!: Array<{}>;
  isTextTerm = true;
  
  // To set date min
  today = new Date().toLocaleDateString("fr-ca");

  startDateControl = new FormControl(this.today);
  endDateControl = new FormControl(this.today);

  doctorControl = new FormControl();
  doctors!: SelectOption[];

  routeParamChangesSubscription!: Subscription;

  
  constructor(
    private route: ActivatedRoute,
    private insuranceDebtService: InsuranceDebtService,
    private doctorService: DoctorService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Requêtes des prises en charge", active: true },
    ];

    this.fetchSelectData();
  }

  fetchSelectData() {
    forkJoin({
      doctors: this.doctorService.getAll(),
    }).subscribe({
      next: (data) => {

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

  printReport() {
    const minDate = new Date(this.startDateControl.value);
    const maxDate = this.endDateControl.value
      ? new Date(this.endDateControl.value)
      : new Date(this.startDateControl.value);

      let doctorCode;
      if(this.doctorControl.value){ 
        doctorCode = this.doctors.find(
          (value) => this.doctorControl.value?.id == value.id
        )?.id
      }else{
        doctorCode = ""
      }


    this.insuranceDebtService.printConsultationForMedecinReport({
        minDate: minDate,
        maxDate: maxDate,
        doctor: doctorCode
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

        pdfModalRef.componentInstance.title = "Prise en charge par Medecin";
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
