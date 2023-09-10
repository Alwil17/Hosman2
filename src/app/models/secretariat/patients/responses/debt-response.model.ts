import { InvoiceResponse } from "./invoice-response.model";
import { PatientResponse } from "./patient-response.model";
import { StatusResponse } from "./status-response.model";

export interface DebtResponse {
  id: number;
  montant: number;
  date_operation: Date;
  patient: PatientResponse;
  facture: InvoiceResponse;
  etat: StatusResponse;
}
