import { Appointment } from "./appointment.model";
import { DoctorAppointmentResponse } from "./responses/doctor-appointment-response.model";

export interface IDoctorAppointment {
  doctorFullname: string;
  appointments: Appointment[];
}

export class DoctorAppointment {
  doctorFullname: string;
  appointments: Appointment[];

  constructor(iDoctorAppointment: IDoctorAppointment) {
    this.doctorFullname = iDoctorAppointment.doctorFullname;
    this.appointments = iDoctorAppointment.appointments;
  }
}
