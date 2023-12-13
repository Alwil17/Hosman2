import { Patient } from "../secretariat/patients/patient.model";
import { Status } from "../secretariat/patients/status.model";
import { Doctor } from "../secretariat/shared/doctor.model";
import { AppointmentResponse } from "./responses/appointment-response.model";

export interface IAppointment {
  id: number;
  medecin: Doctor;
  intervenant: Doctor;
  patient: Patient;
  etat: Status;
  date_rdv: Date;
  objet: string;
}

export class Appointment {
  id: number;
  medecin: Doctor;
  intervenant: Doctor;
  patient: Patient;
  etat: Status;
  date_rdv: Date;
  objet: string;

  constructor(iAppointment: IAppointment) {
    this.id = iAppointment.id;
    this.medecin = iAppointment.medecin;
    this.intervenant = iAppointment.intervenant;
    this.patient = iAppointment.patient;
    this.etat = iAppointment.etat;
    this.date_rdv = iAppointment.date_rdv;
    this.objet = iAppointment.objet;
  }

  static fromResponse(appointment: AppointmentResponse): Appointment {
    return new Appointment({
      id: appointment.id,
      medecin: Doctor.fromResponse(appointment.medecin),
      intervenant: Doctor.fromResponse(appointment.intervenant),
      patient: Patient.fromResponse(appointment.patient),
      etat: Status.fromResponse(appointment.etat),
      date_rdv: appointment.date_rdv,
      objet: appointment.objet,
    });
  }
}
