import { PatientResponse } from "./patient-response.model";
import { StatusResponse } from "./status-response.model";

export interface RemainderResponse {
  id: number;
  montant: number;
  date_operation: Date;
  // patient: PatientResponse;
  // facture
  etat: StatusResponse;
}
