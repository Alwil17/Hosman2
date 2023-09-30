import { InvoiceResponse } from "./invoice-response.model";
import { PatientResponse } from "./patient-response.model";
import { StatusResponse } from "./status-response.model";

export interface DebtResponse {
  id: number;
  facture_ref?: string;
  montant: number;
  date_operation: Date;
  date_retrait: Date;
  patient: PatientResponse;
  etat: StatusResponse;
}
