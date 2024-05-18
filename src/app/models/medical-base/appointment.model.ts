import { Patient } from "../secretariat/patients/patient.model";
import { Status } from "../secretariat/patients/status.model";
import { Doctor } from "../secretariat/shared/doctor.model";
import { AppointmentResponse } from "./responses/appointment-response.model";

export interface IAppointment {
  id: number;
  date_rdv: Date;
  medecin_ref: string;
  medecin: Doctor;
  intervenant_ref: string;
  intervenant: Doctor;
  patient_nom: string;
  patient_prenoms: string;
  patient_sexe?: string;
  patient_naiss?: Date;
  objet: string;
  // etat: Status;
}

export class Appointment {
  id: number;
  date_rdv: Date;
  medecin_ref: string;
  medecin: Doctor;
  intervenant_ref: string;
  intervenant: Doctor;
  patient_nom: string;
  patient_prenoms: string;
  patient_sexe?: string;
  patient_naiss?: Date;
  objet: string;
  // etat: Status;

  constructor(iAppointment: IAppointment) {
    this.id = iAppointment.id;
    this.date_rdv = iAppointment.date_rdv;
    this.medecin_ref = iAppointment.medecin_ref;
    this.medecin = iAppointment.medecin;
    this.intervenant_ref = iAppointment.intervenant_ref;
    this.intervenant = iAppointment.intervenant;
    this.patient_nom = iAppointment.patient_nom;
    this.patient_prenoms = iAppointment.patient_prenoms;
    this.patient_sexe = iAppointment.patient_sexe;
    this.patient_naiss = iAppointment.patient_naiss;
    this.objet = iAppointment.objet;
  }

  static fromResponse(appointment: AppointmentResponse): Appointment {
    return new Appointment({
      id: appointment.id,
      date_rdv: appointment.date_rdv,
      medecin_ref: appointment.medecin_ref,
      medecin: Doctor.fromResponse(appointment.medecin),
      intervenant_ref: appointment.intervenant_ref,
      intervenant: Doctor.fromResponse(appointment.intervenant),
      patient_nom: appointment.patient_nom,
      patient_prenoms: appointment.patient_prenoms,
      patient_sexe: appointment.patient_sexe,
      patient_naiss: appointment.patient_naiss,
      objet: appointment.objet,
      // etat: Status.fromResponse(appointment.etat),
    });
  }
}
