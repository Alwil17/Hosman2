import { PatientResponse } from "../../secretariat/patients/responses/patient-response.model";
import { StatusResponse } from "../../secretariat/patients/responses/status-response.model";
import { DoctorResponse } from "../../secretariat/shared/responses/doctor-response.model";

export interface AppointmentResponse {
  id: number;
  date_rdv: Date;
  medecin_ref: string;
  medecin: DoctorResponse;
  intervenant_ref: string;
  intervenant: DoctorResponse;
  patient_nom: string;
  patient_prenoms: string;
  patient_sexe?: string;
  patient_naiss?: Date;
  objet: string;
  // etat: StatusResponse;
}
