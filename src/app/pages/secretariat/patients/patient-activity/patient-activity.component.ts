import {
  Component,
  ElementRef,
  OnInit,
  QueryList,
  TemplateRef,
  ViewChild,
  ViewChildren,
} from "@angular/core";
import { IActivity, IPrestation, IPrestationSelect } from "./activity.models";
import { FormControl, FormGroup, Validators } from "@angular/forms";

import { DatePipe, DecimalPipe } from "@angular/common";
import { FormsModule } from "@angular/forms";
import {
  NgbModal,
  NgbModalRef,
  NgbPaginationModule,
  NgbTypeaheadModule,
} from "@ng-bootstrap/ng-bootstrap";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { PatientInvoiceFormComponent } from "../patient-invoice-form/patient-invoice-form.component";
import {
  IPatient,
  Patient,
} from "src/app/models/secretariat/patients/patient.model";
import { Insurance } from "src/app/models/secretariat/patients/insurance.model";
// import { IPatientInsurance } from "src/app/models/secretariat/patients/patient-insurance.model";
import { Prestation } from "src/app/models/secretariat/patients/prestation.model";
import { SelectOption } from "src/app/models/extras/select.model";
import { SectorService } from "src/app/services/secretariat/shared/sector.service";
import { DoctorService } from "src/app/services/secretariat/shared/doctor.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { ActGroupService } from "src/app/services/secretariat/shared/act-group.service";
import { ActGroup } from "src/app/models/secretariat/shared/act-group.model";
import { TariffService } from "src/app/services/secretariat/shared/tariff.service";
import { Tariff } from "src/app/models/secretariat/shared/tariff.model";
import { InputComponent } from "src/app/shared/form-inputs/input/input.component";
import { SelectComponent } from "src/app/shared/form-inputs/select/select.component";
import { WarningMessages } from "src/app/helpers/messages";
import { PrestationService } from "src/app/services/secretariat/patients/prestation.service";
import { PrestationRequest } from "src/app/models/secretariat/patients/requests/prestation-request.model";
import { DoctorRequest } from "src/app/models/secretariat/shared/requests/doctor-request.model";
import { SimpleModalComponent } from "src/app/shared/modals/simple-modal/simple-modal.component";
import { CheckoutService } from "src/app/services/secretariat/activities/checkout.service";
import { PdfModalComponent } from "src/app/shared/modals/pdf-modal/pdf-modal.component";
import { PatientFormModalComponent } from "../patient-form-modal/patient-form-modal.component";

@Component({
  selector: "app-patient-activity",
  templateUrl: "./patient-activity.component.html",
  styleUrls: ["./patient-activity.component.scss"],
})
export class PatientActivityComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  isPatientInfoCollapsed = true;

  isMedicalProceduresSelected = true;

  // To set date min
  today = new Date().toLocaleDateString("fr-ca");

  // Activity form controls
  sectorControl = new FormControl(null, [Validators.required]);
  consultingDoctorControl = new FormControl(null, [Validators.required]);

  doctorTypeControl = new FormControl({ value: null, disabled: true }, [
    Validators.required,
  ]);
  doctorControl = new FormControl({ value: null, disabled: true }, [
    Validators.required,
  ]);
  performedByControl = new FormControl({ value: null, disabled: true });

  activityDateControl = new FormControl(this.today, [Validators.required]);
  quantityControl = new FormControl(1, [Validators.required]);
  originControl = new FormControl({ value: "PISJO", disabled: true });

  // Activity form group
  activityForm: FormGroup = new FormGroup({});
  isActivityFormSubmitted = false;

  table1: IPrestation[] = [];

  table2: IPrestationSelect[] = [];

  table1Page = 1;
  table1PageSize = 10;
  table1CollectionSize = this.table1.length;
  activities: IPrestation[] = [];

  table2Page = 1;
  table2PageSize = 10;
  table2CollectionSize = this.table2.length;
  activitiesSelect: IPrestationSelect[] = [];

  selectedPatient!: Patient;

  selectedPrestationIndex = "";

  sectors!: SelectOption[];

  consultingDoctors!: SelectOption[];

  doctorTypes = [
    { id: 1, text: "Interne" },
    { id: 2, text: "Externe" },
  ];

  doctors!: SelectOption[];

  performedBys!: SelectOption[];

  @ViewChildren(InputComponent)
  inputFields!: QueryList<InputComponent>;

  @ViewChildren(SelectComponent)
  selectFields!: QueryList<SelectComponent>;

  table2Total = 0;

  constructor(
    public patientService: PatientService,
    private datePipe: DatePipe,
    private sectorService: SectorService,
    private doctorService: DoctorService,
    private modalService: NgbModal,
    private toastService: ToastService,
    private actGroupService: ActGroupService,
    private tariffService: TariffService,
    private prestationService: PrestationService,
    private checkoutService: CheckoutService
  ) {
    this.selectedPatient = patientService.getActivePatient();

    this.generateSummary();

    this.actGroupService.getAll().subscribe({
      next: (data) => {
        this.actGroups = data;

        this.selectedPrestationIndex = data[0].code;
        this.tariffService.getByGroupCode(data[0].code).subscribe({
          next: (data) => {
            this.selectedGroupTariffs = data;

            this.refreshTable1();
          },
          error: (e) => {
            console.log(e);
          },
        });
      },
      error: (e) => {
        console.log(e);
      },
    });

    // this.table1 = (this.actGroups[0].items as IActivity[]).map((item) => {
    //   let patientPrice = 0;
    //   if (this.patientService.getActivePatientType() == 1) {
    //     patientPrice = item.NA;
    //   } else if (this.patientService.getActivePatientType() == 2) {
    //     patientPrice = item.ENA;
    //   } else if (this.patientService.getActivePatientType() == 3) {
    //     patientPrice = item.AL_S;
    //   } else {
    //     patientPrice = item.AHZ;
    //   }

    //   return {
    //     id: item.id,
    //     designation: item.designation,
    //     price: patientPrice,
    //     description: item.description,
    //   };
    // }) as IPrestation[];

    // this.table1CollectionSize = this.table1.length;

    this.refreshActivities();
  }

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Patients" },
      { label: "Activité", active: true },
    ];

    this.activityForm = new FormGroup({
      sectorControl: this.sectorControl,
      consultingDoctorControl: this.consultingDoctorControl,

      doctorTypeControl: this.doctorTypeControl,
      doctorControl: this.doctorControl,
      performedByControl: this.performedByControl,

      activityDateControl: this.activityDateControl,
      quantityControl: this.quantityControl,
      originControl: this.originControl,
    });

    this.onChanges();

    this.fetchSelectData();
  }

  docAddable = false;
  onChanges() {
    this.doctorTypeControl.valueChanges.subscribe((value) => {
      console.log(value);

      this.doctorService.getByType(value?.text).subscribe({
        next: (data) => {
          this.doctors = data.map((doctor) => ({
            id: doctor.matricule,
            text: doctor.fullName,
          }));
        },
        error: (error) => {
          console.error(error);
        },
      });

      if (value && value.text == "Externe") {
        this.docAddable = true;

        this.originControl.setValue(null);
        this.originControl.enable();
      } else {
        this.docAddable = false;

        this.originControl.setValue("PISJO");
        this.originControl.disable();
      }

      this.doctorControl.setValue(null);
    });
  }

  fetchSelectData() {
    this.sectorService.getAll().subscribe({
      next: (data) => {
        this.sectors = data.map((sector) => ({
          id: sector.code,
          text: sector.libelle,
        }));
      },
      error: (error) => {
        console.error(error);
      },
    });

    this.doctorService.getAll().subscribe({
      next: (data) => {
        const mapped = data.map((doctor) => ({
          id: doctor.matricule,
          text: doctor.fullName,
        }));

        this.consultingDoctors = mapped;
        this.doctors = mapped;
        this.performedBys = mapped;
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  updateTable(actGroup: ActGroup) {
    const code = actGroup.code;

    this.selectedPrestationIndex = code;

    if (actGroup.code == "GRP001") {
      this.isMedicalProceduresSelected = true;

      // Enabling medical procedures controls
      this.sectorControl.addValidators([Validators.required]);
      this.sectorControl.updateValueAndValidity();
      this.sectorControl.enable();

      this.consultingDoctorControl.addValidators([Validators.required]);
      this.consultingDoctorControl.updateValueAndValidity();
      this.consultingDoctorControl.enable();

      // Disabling non-medical procedures controls
      this.doctorTypeControl.clearValidators();
      this.doctorTypeControl.updateValueAndValidity({ emitEvent: false });
      this.doctorTypeControl.disable({ emitEvent: false });

      this.doctorControl.clearValidators();
      this.doctorControl.updateValueAndValidity();
      this.doctorControl.disable();

      // this.performedByControl.clearValidators();
      // this.performedByControl.updateValueAndValidity();
      this.performedByControl.disable();

      // Disabling origin control
      // this.originControl.clearValidators();
      // this.originControl.updateValueAndValidity();
      this.originControl.setValue("PISJO");
      this.originControl.disable();
    } else {
      this.isMedicalProceduresSelected = false;

      // Disabling medical procedures controls
      this.sectorControl.clearValidators();
      this.sectorControl.updateValueAndValidity();
      this.sectorControl.disable();

      this.consultingDoctorControl.clearValidators();
      this.consultingDoctorControl.updateValueAndValidity();
      this.consultingDoctorControl.disable();

      // Enabling non-medical procedures controls
      this.doctorTypeControl.addValidators([Validators.required]);
      this.doctorTypeControl.updateValueAndValidity({ emitEvent: false });
      this.doctorTypeControl.enable({ emitEvent: false });

      this.doctorControl.addValidators([Validators.required]);
      this.doctorControl.updateValueAndValidity();
      this.doctorControl.enable();

      // this.performedByControl.addValidators([Validators.required]);
      // this.performedByControl.updateValueAndValidity();
      this.performedByControl.enable();

      // Enabling origin control
      // this.originControl.addValidators([Validators.required]);
      // this.originControl.updateValueAndValidity();
      this.originControl.setValue(null);
      this.originControl.enable();
    }

    this.tariffService.getByGroupCode(code).subscribe({
      next: (data) => {
        this.selectedGroupTariffs = data;

        this.refreshTable1();
      },
      error: (e) => {
        console.log(e);
      },
    });

    // this.table1 = (
    //   this.actGroups[selectedPrestationType].items as IActivity[]
    // ).map((item) => {
    //   let patientPrice = 0;
    //   if (this.patientService.getActivePatientType() == 1) {
    //     patientPrice = item.NA;
    //   } else if (this.patientService.getActivePatientType() == 2) {
    //     patientPrice = item.ENA;
    //   } else if (this.patientService.getActivePatientType() == 3) {
    //     patientPrice = item.AL_S;
    //   } else {
    //     patientPrice = item.AHZ;
    //   }

    //   return {
    //     id: item.id,
    //     designation: item.designation,
    //     price: patientPrice,
    //     description: item.description,
    //   };
    // }) as IPrestation[];

    // this.table1Page = 1;
    // this.table1CollectionSize = this.table1.length;

    // this.refreshActivities();
  }

  refreshTable1() {
    this.table1 = this.selectedGroupTariffs.map((item) => {
      let patientPrice = 0;
      if (this.patientService.getActivePatientType() == 0) {
        patientPrice = item.tarif_non_assure;
      } else if (this.patientService.getActivePatientType() == 1) {
        patientPrice = item.tarif_etr_non_assure;
      } else if (this.patientService.getActivePatientType() == 2) {
        patientPrice = item.tarif_assur_locale;
      } else {
        patientPrice = item.tarif_assur_hors_zone;
      }

      return {
        id: item.id,
        designation: item.libelle,
        price: patientPrice,
        description: item.description,
      };
    }) as IPrestation[];

    this.table1Page = 1;
    this.table1CollectionSize = this.table1.length;

    this.refreshActivities();
  }

  refreshActivities() {
    this.activities = this.table1;
    // .map((item, i) => ({ id: i + 1, ...item }))
    // .slice(
    //   (this.table1Page - 1) * this.table1PageSize,
    //   (this.table1Page - 1) * this.table1PageSize + this.table1PageSize
    // );
  }

  // Refresh the second table
  refreshActivitiesSelect() {
    this.table2Total = 0;

    // Calculate second table total
    this.table2.forEach((value) => {
      this.table2Total += value.total_price;
    });

    this.activitiesSelect = this.table2;
    // .map((item, i) => ({ id: i + 1, ...item }))
    // .slice(
    //   (this.table2Page - 1) * this.table2PageSize,
    //   (this.table2Page - 1) * this.table2PageSize + this.table2PageSize
    // );
  }

  searchTerm = "";
  searchActs() {
    const searchedActs = this.searchTerm
      ? this.table1.filter((act) => {
          return act.designation
            .toLowerCase()
            .includes(this.searchTerm.toLowerCase());

          // let isFound = false;

          // if (
          //   act.designation
          //     .toLowerCase()
          //     .includes(this.searchTerm.toLowerCase())
          // ) {
          //   isFound = true;
          // } else if (act.description) {
          //   isFound = act.description
          //     .toLowerCase()
          //     .includes(this.searchTerm.toLowerCase());
          // }

          // return isFound;
        })
      : this.table1;

    this.table1CollectionSize = searchedActs.length;

    this.activities = searchedActs;
    // .slice(
    //   (this.table1Page - 1) * this.table1PageSize,
    //   (this.table1Page - 1) * this.table1PageSize + this.table1PageSize
    // );
  }

  add(item: IPrestation) {
    console.log(item);

    const item2: IPrestationSelect = {
      id: item.id,
      rubrique: this.actGroups.find(
        (value) => this.selectedPrestationIndex == value.code
      )!.libelle, //this.selectedGroupTariffs.find((value) => item.id == value.id)!.description,
      prestation: item.designation,
      price: item.price,
      quantity: this.quantityControl.value,
      total_price: item.price * this.quantityControl.value,
    };
    var index = this.table2.findIndex((value) => value.id == item.id);

    if (index === -1) {
      this.table2 = [...this.table2, item2];
    }

    this.table2CollectionSize = this.table2.length;
    this.refreshActivitiesSelect();
  }

  remove(item: IPrestationSelect) {
    console.log(item);

    this.table2 = [
      ...this.table2.filter((value) => {
        return value.id !== item.id;
      }),
    ];

    this.table2CollectionSize = this.table2.length;
    this.refreshActivitiesSelect();
  }

  selectedGroupTariffs: Tariff[] = [];

  actGroups: ActGroup[] = [];

  summary = {
    title: "",
    fullName: "",
    birth: "",
    // age: "",
    birthPlace: "",
    profession: "",
    // nationality: "",
    // insuranceRate: "",
    // insurance: "",
    // insuranceEnd: "",
    tel1: "",
    tel2: "",
    personToContact: "",
  };

  generateSummary() {
    this.summary = {
      title:
        this.patientService.getActivePatient().sexe === "Masculin"
          ? "Monsieur"
          : "Mademoiselle",
      fullName:
        this.patientService.getActivePatient().nom +
        " " +
        this.patientService.getActivePatient().prenoms,
      birth: this.datePipe.transform(
        this.patientService.getActivePatient().date_naissance,
        "dd/MM/yyyy"
      )!,
      // age: this.ageControl.value as string,
      birthPlace: this.patientService.getActivePatient().lieu_naissance
        ? this.patientService.getActivePatient().lieu_naissance!
        : "",
      profession: this.patientService.getActivePatient().profession
        ? this.patientService.getActivePatient().profession!.denomination
        : "",
      // nationality: this.patientService.getActivePatient().pays_origine ? this.patientService.getActivePatient().pays_origine as string : "",
      // insuranceRate: this.patientService.getActivePatient().assurance..value
      //   ? this.insuranceRateControl.value
      //   : "",
      // insurance: this.insuranceControl.value ? this.insuranceControl.value : "",
      // insuranceEnd: this.insuranceEndControl.value
      //   ? this.datePipe.transform(this.insuranceEndControl.value, "dd/MM/yyyy")!
      //   : "",

      tel1: this.patientService.getActivePatient().tel1
        ? this.patientService.getActivePatient().tel1
        : "",
      tel2: this.patientService.getActivePatient().tel2
        ? this.patientService.getActivePatient().tel2!
        : "",

      personToContact: this.patientService
        .getActivePatient()
        .personne_a_prevenir.toString(),
      // ? this.patientService.getActivePatient().personne_a_prevenir
      // : "",
    };
  }

  getInvalidFields() {
    const invalidInputs: string[] = [];
    this.inputFields.forEach((input) => {
      if (input.control.invalid) {
        invalidInputs.push("- " + input.label);
      }
    });

    const invalidSelects: string[] = [];
    this.selectFields.forEach((select) => {
      if (select.control.invalid) {
        invalidSelects.push("- " + select.label);
      }
    });

    let notificationMessages: string[] = [];
    if (invalidInputs.length !== 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_INPUT_FIELDS,
        ...invalidInputs
      );
    }

    if (notificationMessages.length !== 0) {
      notificationMessages.push("");
    }

    if (invalidSelects.length !== 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_SELECT_FIELDS,
        ...invalidSelects
      );
    }

    return notificationMessages;
  }

  openInvoiceModal() {
    this.isActivityFormSubmitted = true;

    if (this.activityForm.invalid) {
      const notificationMessages = this.getInvalidFields();

      this.toastService.show({
        messages: notificationMessages,
        type: ToastType.Warning,
      });

      return;
    }

    if (this.table2.length === 0) {
      this.toastService.show({
        messages: [
          "Veuillez choisir au moins un élément dans le tableau de gauche.",
        ],
        type: ToastType.Warning,
      });

      return;
    }

    let doctor: DoctorRequest | undefined = undefined;
    if (!this.isMedicalProceduresSelected) {
      const doctorText = this.doctorControl.value?.text as string;
      const spaceIndex = doctorText.indexOf(" ");

      const doctorRegistrationNumber =
        this.doctorControl.value?.id == -1
          ? undefined
          : (this.doctorControl.value?.id as string);

      if (!doctorRegistrationNumber && spaceIndex !== -1) {
        const lastname = doctorText.substring(0, spaceIndex);
        const firstname = doctorText.substring(spaceIndex + 1);
        doctor = new DoctorRequest({
          matricule: doctorRegistrationNumber, // Should be "undefined" if new doctor
          nom: lastname,
          prenoms: firstname,
          type: this.doctorTypeControl.value.text,
        });
      } else {
        doctor = new DoctorRequest({
          matricule: doctorRegistrationNumber,
          nom: "",
          prenoms: "",
          type: this.doctorTypeControl.value.text,
        });
      }

      console.log(JSON.stringify(doctor, null, 2));
    }

    const prestation = new PrestationRequest({
      patient_id: this.patientService.getActivePatient().id,

      consulteur:
        this.consultingDoctorControl.value?.id ??
        this.performedByControl.value?.id ??
        undefined,

      date_prestation: new Date(this.activityDateControl.value),
      tarifs: this.table2.map((item) => ({
        tarif_id: item.id,
        quantite: item.quantity,
      })),
      demandeur: doctor,
      secteur_code: this.sectorControl.value?.id,
      provenance: this.originControl.value,
    });

    console.log(JSON.stringify(prestation, null, 2));

    this.prestationService.generatePreInvoiceInfos(prestation).subscribe({
      next: (data) => {
        const invoiceModalRef = this.modalService.open(
          PatientInvoiceFormComponent,
          {
            size: "xl",
            centered: true,
            scrollable: true,
            backdrop: "static",
          }
        );

        invoiceModalRef.componentInstance.patientActivities = this.table2;
        invoiceModalRef.componentInstance.preInvoiceInfos = data;
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });

    // const prestation = new Prestation(
    //   1,
    //   this.sectorControl.value
    //     ? this.sectors[parseInt(this.sectorControl.value.id)].text
    //     : "",
    //   this.consultingDoctorControl.value
    //     ? this.consultingDoctors[
    //         parseInt(this.consultingDoctorControl.value.id)
    //       ].text
    //     : "",
    //   new Date(),
    //   this.originControl.value ?? "PISJO",
    //   this.doctorTypeControl.value
    //     ? this.doctorTypes[parseInt(this.doctorTypeControl.value.id)].text
    //     : "",
    //   this.doctorControl.value
    //     ? this.doctors[parseInt(this.doctorControl.value.id)].text
    //     : "",
    //   this.performedByControl.value
    //     ? this.performedBys[parseInt(this.performedByControl.value.id)].text
    //     : ""
    // );
  }

  openPatientModificationModal() {
    const patientModifyModalRef = this.modalService.open(
      PatientFormModalComponent,
      {
        size: "xl",
        centered: true,
        // scrollable: true,
        backdrop: "static",
        keyboard: false,
      }
    );

    patientModifyModalRef.componentInstance.title =
      "Modifier les informations du patient";
    patientModifyModalRef.componentInstance.patientInfos = this.selectedPatient;

    patientModifyModalRef.componentInstance.isPatientModified.subscribe(
      (isPatientModified: boolean) => {
        console.log("Patient modified : " + isPatientModified);

        this.selectedPatient = this.patientService.getActivePatient();

        if (isPatientModified) {
          patientModifyModalRef.close();
        }
      }
    );
  }

  // @ViewChild("activityFirstField", { read: ElementRef })
  // activityFirstField!: ElementRef;

  scrollTo(element: any) {
    element.scrollIntoView({
      behavior: "smooth",
      block: "start",
      inline: "start",
    });
  }
}
