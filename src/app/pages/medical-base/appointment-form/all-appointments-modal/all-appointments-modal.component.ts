import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { DoctorAppointment } from "src/app/models/medical-base/doctor-appointment.model";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { AppointmentService } from "src/app/services/shared/appointment.service";

@Component({
  selector: "app-all-appointments-modal",
  templateUrl: "./all-appointments-modal.component.html",
  styleUrls: ["./all-appointments-modal.component.scss"],
})
export class AllAppointmentsModalComponent implements OnInit {
  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  startDateControl = new FormControl(this.today);
  endDateControl = new FormControl(this.today);

  appointmentsList: DoctorAppointment[] = [];

  constructor(
    public modal: NgbActiveModal,
    private appointmentService: AppointmentService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.refreshAppointmentsList();

    this.startDateControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshAppointmentsList();
      }
    });

    this.endDateControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshAppointmentsList();
      }
    });
  }

  refreshAppointmentsList() {
    const minDate = new Date(this.startDateControl.value);
    const maxDate = this.endDateControl.value
      ? new Date(this.endDateControl.value)
      : undefined;

    this.appointmentService
      .getDoctorsAppointmentsByPeriod({
        minDate: minDate,
        maxDate: maxDate,
      })
      .subscribe({
        next: (data) => {
          this.appointmentsList = data.map((value) => {
            const doctorAppointment = new DoctorAppointment({
              doctorFullname: value.doctorFullname,
              appointments: value.appointments.sort((a, b) => {
                if (a.date_rdv < b.date_rdv) {
                  return -1;
                }
                if (a.date_rdv === b.date_rdv) {
                  return 0;
                }
                if (a.date_rdv > b.date_rdv) {
                  return 1;
                }

                return 0;
              }),
            });

            return doctorAppointment;
          });

          console.log(JSON.stringify(data, null, 2));

          // console.log(this.insurancesDebtsList);

          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
          // });

          // this.insurancesDebtsList.forEach((value) => {
          //   this.actsAmountTotal += value.facture.total;
          //   this.paidAmountTotal +=
          //     value.facture.a_payer - value.facture.creance.montant;
          //   this.scAmountTotal += value.montant_pec;
          // });
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
}
