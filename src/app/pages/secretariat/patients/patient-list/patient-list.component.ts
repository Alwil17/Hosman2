import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { FormControl } from "@angular/forms";
import { SelectOption } from "src/app/models/extras/select.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { SecretariatRouterService } from "src/app/services/secretariat/router/secretariat-router.service";
import { PatientFormModalComponent } from "../patient-form-modal/patient-form-modal.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { LoadingSpinnerService } from "src/app/services/secretariat/shared/loading-spinner.service";

@Component({
  selector: "app-patient-list",
  templateUrl: "./patient-list.component.html",
  styleUrls: ["./patient-list.component.scss"],
})
export class PatientListComponent implements OnInit, AfterViewInit {
  @Input()
  isInMedicalBase = false;

  @Output()
  doubleClickedPatient = new EventEmitter<Patient>();

  isTextTerm = true;

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

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  searchControl = new FormControl("");
  dateTermControl = new FormControl(this.today);

  searchCriterionControl = new FormControl(this.searchCriteria[0]);

  searchedPatients: Patient[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.searchedPatients.length;
  patients: Patient[] = [];

  constructor(
    private secretariatRouter: SecretariatRouterService,
    private patientService: PatientService,
    private modalService: NgbModal,
    private loadingSpinnerService: LoadingSpinnerService
  ) {
    // this.patientService.getAll().subscribe({
    //   next: (data) => {
    //     this.allPatients = data;
    //     // this.refreshPatients();
    //   },
    //   error: (error) => {
    //     console.error(error);
    //   },
    // });
  }

  @ViewChild("firstField", { read: ElementRef })
  firstField!: ElementRef;

  ngAfterViewInit(): void {
    this.firstField.nativeElement.querySelector("input").focus();
  }

  ngOnInit(): void {
    this.searchCriterionControl.valueChanges.subscribe((value) => {
      if (
        value.id == this.searchCriteria[0].id ||
        value.id == this.searchCriteria[1].id ||
        value.id == this.searchCriteria[2].id
      ) {
        this.isTextTerm = true;
      } else {
        this.isTextTerm = false;
      }

      this.searchPatients();
    });

    this.searchControl.valueChanges.subscribe((value) => {
      if (value != null) {
        this.searchPatients();
      }
    });

    this.dateTermControl.valueChanges.subscribe((value) => {
      console.log(value);

      this.searchPatients();
    });
  }

  searchPatients() {
    // const filteredPatients = this.searchTerm
    //   ? this.allPatients.filter((patient) =>
    //       (patient.nom + " " + patient.prenoms)
    //         .toLowerCase()
    //         .startsWith(this.searchTerm.toLowerCase())
    //     )
    //   : [];

    // this.loadingSpinnerService.hideLoadingSpinner()

    let searchTerm = "";
    if (this.isTextTerm) {
      searchTerm = this.searchControl.value
        ? String(this.searchControl.value)
        : "";
    } else {
      searchTerm = this.dateTermControl.value;
      // new Date(this.dateTermControl.value).toLocaleDateString("fr-ca");
    }

    this.patientService
      .searchBy(searchTerm, this.searchCriterionControl.value?.id)
      .subscribe({
        next: (data) => {
          this.searchedPatients = data;

          this.refreshPatients();
        },
        error: (error) => {
          console.error(error);
        },
      });

    // this.collectionSize = this.searchedPatients.length;

    // this.patients = this.searchedPatients.slice(
    //   (this.page - 1) * this.pageSize,
    //   (this.page - 1) * this.pageSize + this.pageSize
    // );
  }

  refreshPatients() {
    this.collectionSize = this.searchedPatients.length;

    this.patients = this.searchedPatients.slice(
      (this.page - 1) * this.pageSize,
      (this.page - 1) * this.pageSize + this.pageSize
    );
  }

  async goToPatientActivity(patient: Patient) {
    await this.secretariatRouter.navigateToPatientActivity(patient.id);

    // this.patientService.setActivePatient(patient.id).subscribe({
    //   next: async (data) => {
    //   },
    //   error: (e) => {
    //     console.error(e);
    //   },
    // });

    // console.log(JSON.stringify(patient, null, 2));
  }

  async goToPatientNew() {
    await this.secretariatRouter.navigateToPatientCreate();
  }

  onRowItemDoubleClick(patient: Patient) {
    if (!this.isInMedicalBase) {
      this.goToPatientActivity(patient);
    } else {
      this.doubleClickedPatient.emit(patient);
    }
  }

  openPatientModificationModal(patient: Patient) {
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
    patientModifyModalRef.componentInstance.patientInfos = patient;

    patientModifyModalRef.componentInstance.isPatientModified.subscribe(
      (isPatientModified: boolean) => {
        console.log("Patient modified : " + isPatientModified);

        if (isPatientModified) {
          patientModifyModalRef.close();
          this.searchPatients();
        }
      }
    );
  }

  openPatientNewModal() {
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
      "Enregistrer un nouveau patient";

    patientModifyModalRef.componentInstance.showSimpleCreateButtons = true;

    patientModifyModalRef.componentInstance.isPatientCreated.subscribe(
      (isPatientCreated: boolean) => {
        console.log("Patient created : " + isPatientCreated);

        if (isPatientCreated) {
          patientModifyModalRef.close();
          this.searchPatients();
        }
      }
    );
  }
}
