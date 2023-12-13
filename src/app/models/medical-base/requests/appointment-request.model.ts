export interface IAppointmentRequest {
  medecin_id: number;
  intervenant_id: number;
  patient_id: number;
  date_rdv: string;
  heure_rdv: string;
  objet: string;
}

export class AppointmentRequest {
  medecin_id: number;
  intervenant_id: number;
  patient_id: number;
  date_rdv: string;
  heure_rdv: string;
  objet: string;

  constructor(iAppointmentRequest: IAppointmentRequest) {
    this.medecin_id = iAppointmentRequest.medecin_id;
    this.intervenant_id = iAppointmentRequest.intervenant_id;
    this.patient_id = iAppointmentRequest.patient_id;
    this.date_rdv = iAppointmentRequest.date_rdv;
    this.heure_rdv = iAppointmentRequest.heure_rdv;
    this.objet = iAppointmentRequest.objet;
  }
}
