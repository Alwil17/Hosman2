import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { WaitingListFilter } from "src/app/models/enums/waiting-list-filter.enum";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";
import { MedicalBaseRouterService } from "src/app/services/medical-base/router/medical-base-router.service";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { WaitingListService } from "src/app/services/secretariat/patients/waiting-list.service";
import { DoctorService } from "src/app/services/secretariat/shared/doctor.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { InvoiceDetailsModalComponent } from "./invoice-details-modal/invoice-details-modal.component";
import { Invoice } from "src/app/models/secretariat/patients/invoice.model";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { Observable, Subscription, repeat } from "rxjs";
import { CountdownComponent } from "ngx-countdown";

@Component({
  selector: "app-patient-waiting-list-page",
  templateUrl: "./patient-waiting-list-page.component.html",
  styleUrls: ["./patient-waiting-list-page.component.scss"],
})
export class PatientWaitingListPageComponent implements OnInit, OnDestroy {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  searchControl = new FormControl("");
  filterRadioControl = new FormControl("me");
  doctorControl = new FormControl();

  doctors!: SelectOption[];

  waitingList: WaitingListItem[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.waitingList.length;
  waitingListCut: WaitingListItem[] = [];

  viewFilter: WaitingListFilter = WaitingListFilter.ME;
  doctorFilter?: string;

  waitingListFilterBy$!: Subscription;

  @ViewChild("countdown", { static: false })
  private countdown!: CountdownComponent;
  isFirstCountdown = true;

  constructor(
    private waitingListService: WaitingListService,
    private toastService: ToastService,
    private medicalBaseRouter: MedicalBaseRouterService,
    // private patientService: PatientService,
    private doctorService: DoctorService,
    private modalService: NgbModal,
    private patientVisitService: PatientVisitService
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Liste d'attente", active: true },
    ];

    this.fetchSelectData();

    this.onInputsChanges();

    this.refreshWaitingList();
  }

  ngOnDestroy(): void {
    this.waitingListFilterBy$.unsubscribe();
  }

  goToPatientVisits(waitingListItem: WaitingListItem) {
    this.patientVisitService.selectWaitingListItem(waitingListItem);

    this.patientVisitService
      .startVisit(waitingListItem.patient.reference)
      .subscribe({
        next: async (data) => {
          console.log(data, "\nHere");

          await this.medicalBaseRouter.navigateToPatientVisitsSummary(
            waitingListItem.patient.id
          );
        },
        error: (e) => {
          console.error(e);

          this.toastService.show({
            delay: 10000,
            type: ToastType.Error,
          });
        },
      });

    // this.patientService.setActivePatient(patient.id).subscribe({
    //   next: () => this.medicalBaseRouter.navigateToPatientVisitsSummary(),
    //   error: (e) => console.log(e),
    // });
  }

  refreshWaitingList() {
    if (this.waitingListFilterBy$) {
      this.waitingListFilterBy$.unsubscribe();
    }

    this.waitingListFilterBy$ = this.waitingListService
      .filterBy({
        view: this.viewFilter,
        doctorRegistrationNumber: this.doctorFilter,
      })
      .pipe(repeat({ delay: 120000 }))
      .subscribe({
        next: (data) => {
          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
          // });

          this.waitingList = data.filter((value) =>
            (value.patient.nom + " " + value.patient.prenoms)
              .toLowerCase()
              .includes(String(this.searchControl.value).toLowerCase())
          );

          this.refreshWaitingListTable();

          if (this.isFirstCountdown) {
            this.countdown.begin();
            this.isFirstCountdown = false;
          } else {
            this.countdown.restart();

            // have to call begin() again, because "demand" is "true" in countown config option
            this.countdown.begin();
          }
        },
        error: (e) => {
          console.error(e);

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

  refreshWaitingListTable() {
    this.collectionSize = this.waitingList.length;

    this.waitingListCut = this.waitingList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  fetchSelectData() {
    this.doctorService.getAll().subscribe({
      next: (data) => {
        const mapped = data.map((doctor) => ({
          id: doctor.matricule,
          text: doctor.fullName,
        }));

        this.doctors = mapped;
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  onInputsChanges() {
    this.searchControl.valueChanges.subscribe((value) => {
      if (value != null) {
        this.refreshWaitingList();
      }
    });

    this.filterRadioControl.valueChanges.subscribe((value) => {
      if (value === "me") {
        this.viewFilter = WaitingListFilter.ME;
      } else if (value === "sector") {
        this.viewFilter = WaitingListFilter.SECTOR;
      } else if (value === "all") {
        this.viewFilter = WaitingListFilter.ALL;
      } else if (value === "doctor") {
        this.viewFilter = WaitingListFilter.DOCTOR;
      }

      this.refreshWaitingList();
    });

    this.doctorControl.valueChanges.subscribe((value) => {
      if (!value) {
        return;
      }

      this.doctorFilter = value.id;

      this.refreshWaitingList();
    });
  }

  openInvoiceDetailsModal(invoice: Invoice) {
    const invoiceModalRef = this.modalService.open(
      InvoiceDetailsModalComponent,
      {
        size: "md",
        centered: true,
        scrollable: true,
      }
    );

    invoiceModalRef.componentInstance.invoice = invoice;
  }

  // deleteWaitingListItem(id: any) {
  //   this.waitingListService.delete(id).subscribe({
  //     next: (data) => {
  //       this.toastService.show({
  //         messages: ["Patient retiré de la liste d'attente."],
  //         type: ToastType.Success,
  //       });

  //       this.refreshWaitingList();
  //     },
  //     error: (e) => {
  //       console.error(e);

  //       this.toastService.show({
  //         delay: 10000,
  //         type: ToastType.Error,
  //       });
  //     },
  //   });
  // }
}
