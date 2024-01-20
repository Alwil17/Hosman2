import { DatePipe } from "@angular/common";
import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  QueryList,
  ViewChild,
  ViewChildren,
} from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { firstValueFrom, forkJoin } from "rxjs";
import { WarningMessages } from "src/app/helpers/messages";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Appointment } from "src/app/models/medical-base/appointment.model";
import { AppointmentRequest } from "src/app/models/medical-base/requests/appointment-request.model";
import { DoctorService } from "src/app/services/secretariat/shared/doctor.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { AppointmentService } from "src/app/services/shared/appointment.service";
import { InputComponent } from "src/app/shared/form-inputs/input/input.component";
import { SelectComponent } from "src/app/shared/form-inputs/select/select.component";
import { ConfirmModalComponent } from "src/app/shared/modals/confirm-modal/confirm-modal.component";

@Component({
  selector: "app-appointment-form",
  templateUrl: "./appointment-form.component.html",
  styleUrls: ["./appointment-form.component.scss"],
})
export class AppointmentFormComponent implements OnInit, AfterViewInit {
  @ViewChild("firstField", { read: ElementRef })
  firstField!: ElementRef;

  @ViewChildren(InputComponent)
  inputFields!: QueryList<InputComponent>;

  @ViewChildren(SelectComponent)
  selectFields!: QueryList<SelectComponent>;

  // To set date min
  today = new Date().toLocaleDateString("fr-ca");

  // Appointement form controls
  doctorControl = new FormControl(null, Validators.required);
  patientLastnameControl = new FormControl(null, Validators.required);
  patientFirstnameControl = new FormControl(null, Validators.required);
  patientGenderControl = new FormControl(null);
  patientBirthdayControl = new FormControl(null);
  interveningDoctorControl = new FormControl(null, Validators.required);
  appointmentDateControl = new FormControl(null, Validators.required);
  appointmentTimeControl = new FormControl(null, Validators.required);
  appointmentReasonControl = new FormControl(null, Validators.required);

  // Appointment forms group
  appointmentForm!: FormGroup;
  isAppointmentFormSubmitted = false;

  doctors: SelectOption[] = [];
  appointmentReasons: SelectOption[] = [];
  genders = [
    { id: 1, text: "Masculin", short: "M" },
    { id: 2, text: "Féminin", short: "F" },
    { id: 3, text: "Indéterminé", short: "I" },
  ];

  doctorSearchControl = new FormControl(null);
  appointmentsList: Appointment[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.appointmentsList.length;
  appointmentsListCut: Appointment[] = [];

  selectedAppointmentId?: number;

  constructor(
    private doctorService: DoctorService,
    private toastService: ToastService,
    private appointmentService: AppointmentService,
    private datePipe: DatePipe,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.appointmentForm = new FormGroup({
      doctorControl: this.doctorControl,
      patientLastnameControl: this.patientLastnameControl,
      patientFirstnameControl: this.patientFirstnameControl,
      patientGenderControl: this.patientGenderControl,
      patientBirthdayControl: this.patientBirthdayControl,
      interveningDoctorControl: this.interveningDoctorControl,
      appointmentDateControl: this.appointmentDateControl,
      appointmentTimeControl: this.appointmentTimeControl,
      appointmentReasonControl: this.appointmentReasonControl,
    });

    this.fetchSelectData();

    this.doctorSearchControl.valueChanges.subscribe((value) => {
      if (value) {
        this.refreshAppointmentsList();
      }
    });

    this.appointmentService
      .getDoctorsAppointmentsByPeriod({
        minDate: new Date("2024-01-18"),
        maxDate: new Date("2024-01-25"),
      })
      .subscribe({
        next: (data) => {
          console.log(JSON.stringify(data, null, 2));
        },
        error: (error) => {
          console.log(error);
        },
      });
  }

  ngAfterViewInit(): void {
    this.firstField.nativeElement.querySelector("input").focus();
  }

  fetchSelectData() {
    forkJoin({
      doctors: this.doctorService.getAll(),
      appointmentReasons: this.appointmentService.getAppointmentObjects(),
    }).subscribe({
      next: (data) => {
        this.doctors = data.doctors.map((value) => ({
          id: value.matricule,
          text: value.fullName,
        }));
        this.appointmentReasons = data.appointmentReasons.map((value) => ({
          id: -1,
          text: value,
        }));
      },
      error: (error) => {
        console.log(error);

        this.toastService.show({
          messages: ["Désolé, une erreur s'est produite."],
          type: ToastType.Error,
          delay: 10_000,
        });
      },
    });
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

  getAppointmentFormData() {
    const patientBirthday = this.patientBirthdayControl.value
      ? new Date(this.patientBirthdayControl.value)
      : undefined;

    const appointmantDate = new Date(
      this.appointmentDateControl.value
    ).toLocaleDateString("fr-ca");
    const appointmantTime = this.appointmentTimeControl.value;

    return new AppointmentRequest({
      medecin_ref: this.doctorControl.value.id,
      intervenant_ref: this.interveningDoctorControl.value.id,
      patient_nom: this.patientLastnameControl.value,
      patient_prenoms: this.patientFirstnameControl.value,
      patient_sexe: this.patientGenderControl.value?.short ?? undefined,
      patient_naiss: patientBirthday,
      date_rdv: appointmantDate,
      heure_rdv: appointmantTime,
      objet: this.appointmentReasonControl.value.text,
    });
  }

  refreshAppointmentsList() {
    const selectedDoctorMatricule = this.doctorSearchControl.value.id;

    this.appointmentService
      .getAppointmentsByDoctorMatricule(selectedDoctorMatricule)
      .subscribe({
        next: (data) => {
          console.log(JSON.stringify(data, null, 2));

          this.appointmentsList = data;

          this.refreshAppointmentsTable();
        },
        error: (error) => {
          console.log(error);

          this.toastService.show({
            messages: ["Désolé, une erreur s'est produite."],
            type: ToastType.Error,
            delay: 10_000,
          });
        },
      });
  }

  refreshAppointmentsTable() {
    this.collectionSize = this.appointmentsList.length;

    this.appointmentsListCut = this.appointmentsList.slice(
      (this.page - 1) * this.pageSize,
      (this.page - 1) * this.pageSize + this.pageSize
    );
  }

  emptyFormFields() {
    this.appointmentForm.setValue({
      doctorControl: null,
      patientLastnameControl: null,
      patientFirstnameControl: null,
      patientGenderControl: null,
      patientBirthdayControl: null,
      interveningDoctorControl: null,
      appointmentDateControl: null,
      appointmentTimeControl: null,
      appointmentReasonControl: null,
    });
  }

  resetForm() {
    this.selectedAppointmentId = undefined;

    this.isAppointmentFormSubmitted = false;

    this.emptyFormFields();

    this.fetchSelectData();

    // Make it so validation error class won't appear
    this.appointmentForm.markAsPristine();
    this.appointmentForm.markAsUntouched();
  }

  // Will set fields value to the selected appointment for modification purposes
  selectAppointment(appointment: Appointment) {
    this.selectedAppointmentId = appointment.id;

    let time = this.datePipe.transform(appointment.date_rdv, "HH:mm");

    this.appointmentForm.setValue({
      doctorControl:
        this.doctors.find(
          (value) => value.id === appointment.medecin.matricule
        ) ?? null,
      patientLastnameControl: appointment.patient_nom,
      patientFirstnameControl: appointment.patient_prenoms,
      patientGenderControl:
        this.genders.find(
          (value) =>
            value.short.toLowerCase() ===
            appointment.patient_sexe?.toLowerCase()
        ) ?? null,
      patientBirthdayControl: appointment.patient_naiss
        ? new Date(appointment.patient_naiss).toLocaleDateString("fr-ca")
        : null,
      interveningDoctorControl:
        this.doctors.find(
          (value) => value.id === appointment.intervenant.matricule
        ) ?? null,
      appointmentDateControl: new Date(appointment.date_rdv).toLocaleDateString(
        "fr-ca"
      ),
      appointmentTimeControl: time,
      appointmentReasonControl:
        this.appointmentReasons.find(
          (value) =>
            value.text.toLowerCase() === appointment.objet.toLowerCase()
        ) ?? null,
    });
  }

  // CRUD / HTTP REQUESTS METHODS ----------------------------------------------------------------------------------------------------------
  registerAppointment() {
    this.isAppointmentFormSubmitted = true;

    if (this.appointmentForm.invalid) {
      const notificationMessages = this.getInvalidFields();

      this.toastService.show({
        messages: notificationMessages,
        type: ToastType.Warning,
      });

      return;
    }

    const appointmentData = this.getAppointmentFormData();

    console.log(JSON.stringify(appointmentData, null, 2));

    this.appointmentService.create(appointmentData).subscribe({
      next: async (data) => {
        console.log(data, "\nHere");

        this.toastService.show({
          messages: ["Le rendez-vous a été enregistré avec succès."],
          type: ToastType.Success,
        });

        this.refreshAppointmentsList();

        this.resetForm();
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Désolé, une erreur s'est produite."],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  registerModifications() {
    this.isAppointmentFormSubmitted = true;

    if (this.appointmentForm.invalid) {
      const notificationMessages = this.getInvalidFields();

      this.toastService.show({
        messages: notificationMessages,
        type: ToastType.Warning,
      });

      return;
    }

    const appointmentData = this.getAppointmentFormData();

    console.log(JSON.stringify(appointmentData, null, 2));

    this.appointmentService
      .update(this.selectedAppointmentId, appointmentData)
      .subscribe({
        next: async (data) => {
          this.toastService.show({
            messages: ["Le rendez-vous a été modifié avec succès."],
            type: ToastType.Success,
          });

          this.refreshAppointmentsList();

          this.resetForm();
        },
        error: (e) => {
          console.error(e);

          this.toastService.show({
            messages: ["Désolé, une erreur s'est produite."],
            delay: 10000,
            type: ToastType.Error,
          });
        },
      });
  }

  async cancelAppointment(appointment: Appointment) {
    const appointmentId = appointment.id;

    // OPEN CONFIRMATION MODAL
    const confirmModalRef = this.modalService.open(ConfirmModalComponent, {
      size: "md",
      centered: true,
      keyboard: false,
      backdrop: "static",
      // scrollable: true,
    });

    confirmModalRef.componentInstance.message =
      "Êtes-vous sûr de vouloir annuler le rendez-vous ?";
    confirmModalRef.componentInstance.subMessage =
      "Cette opération est irréversible.";
    confirmModalRef.componentInstance.isDangerButton = true;

    const isConfirmed = await firstValueFrom(
      confirmModalRef.componentInstance.isConfirmed.asObservable()
    );

    // CLOSE CONFIRMATION MODAL
    confirmModalRef.close();

    // CHECK IF USER CONFIRMED OR NOT
    if (!isConfirmed) {
      return;
    }

    this.appointmentService.cancelAppointment(appointmentId).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Le rendez-vous a été annulé avec succès."],
          type: ToastType.Success,
        });

        this.refreshAppointmentsTable();
      },
      error: (error) => {
        console.log(error);

        this.toastService.show({
          messages: ["Désolé, une erreur s'est produite."],
          type: ToastType.Error,
          delay: 10_000,
        });
      },
    });
  }
}
