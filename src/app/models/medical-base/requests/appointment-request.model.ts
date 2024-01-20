export interface IAppointmentRequest {
  medecin_ref: string;
  intervenant_ref: string;
  patient_nom: string;
  patient_prenoms: string;
  patient_sexe?: string;
  patient_naiss?: Date;
  date_rdv: string;
  heure_rdv: string;
  objet: string;
}

export class AppointmentRequest {
  medecin_ref: string;
  intervenant_ref: string;
  patient_nom: string;
  patient_prenoms: string;
  patient_sexe?: string;
  patient_naiss?: Date;
  date_rdv: string;
  heure_rdv: string;
  objet: string;

  constructor(iAppointmentRequest: IAppointmentRequest) {
    this.medecin_ref = iAppointmentRequest.medecin_ref;
    this.intervenant_ref = iAppointmentRequest.intervenant_ref;
    this.patient_nom = iAppointmentRequest.patient_nom;
    this.patient_prenoms = iAppointmentRequest.patient_prenoms;
    this.patient_sexe = iAppointmentRequest.patient_sexe;
    this.patient_naiss = iAppointmentRequest.patient_naiss;
    this.date_rdv = iAppointmentRequest.date_rdv;
    this.heure_rdv = iAppointmentRequest.heure_rdv;
    this.objet = iAppointmentRequest.objet;
  }
}
