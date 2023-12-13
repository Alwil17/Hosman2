import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { AppointmentService } from "src/app/services/shared/appointment.service";

@Component({
  selector: "app-appointment-form",
  templateUrl: "./appointment-form.component.html",
  styleUrls: ["./appointment-form.component.scss"],
})
export class AppointmentFormComponent implements OnInit {
  // To set date min
  today = new Date().toLocaleDateString("fr-ca");

  // Appointement form controls
  doctorNameControl = new FormControl(null);
  patientLastnameControl = new FormControl(null);
  patientFirstnameControl = new FormControl(null);
  patientGenderControl = new FormControl(null);
  patientBirthdayControl = new FormControl(null);
  interveningDoctorControl = new FormControl(null);
  appointmentDateControl = new FormControl(null);
  appointmentTimeControl = new FormControl(null);
  appointmentReasonControl = new FormControl(null);

  // Appointment forms group
  appointmentForm!: FormGroup;

  constructor(private appointmentService: AppointmentService) {}

  ngOnInit(): void {
    this.appointmentForm = new FormGroup({
      doctorNameControl: this.doctorNameControl,
      patientLastnameControl: this.patientLastnameControl,
      patientFirstnameControl: this.patientFirstnameControl,
      patientGenderControl: this.patientGenderControl,
      patientBirthdayControl: this.patientBirthdayControl,
      interveningDoctorControl: this.interveningDoctorControl,
      appointmentDateControl: this.appointmentDateControl,
      appointmentTimeControl: this.appointmentTimeControl,
      appointmentReasonControl: this.appointmentReasonControl,
    });
  }
}
