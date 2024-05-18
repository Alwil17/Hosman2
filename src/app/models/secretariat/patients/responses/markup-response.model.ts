import { PatientResponse } from "./patient-response.model";

export interface MarkupResponse {
  id: number;
  montant: number;
  motif?: string;
  date_operation: Date;
  // patient: PatientResponse
}
