import { PatientResponse } from "../../secretariat/patients/responses/patient-response.model";
import { StatusResponse } from "../../secretariat/patients/responses/status-response.model";
import { DoctorResponse } from "../../secretariat/shared/responses/doctor-response.model";

export interface AppointmentResponse {
  id: number;
  medecin: DoctorResponse;
  intervenant: DoctorResponse;
  patient: PatientResponse;
  etat: StatusResponse;
  date_rdv: Date;
  objet: string;
}
