import { InsuranceResponse } from "../../patients/responses/insurance-response.model";
import { InvoiceResponse } from "../../patients/responses/invoice-response.model";
import { PatientResponse } from "../../patients/responses/patient-response.model";
import { ActResponse } from "../../shared/responses/act-response.model";

export interface InsuranceDebtResponse {
  id: number;
  assurance: InsuranceResponse;
  patient: PatientResponse;
  acte: ActResponse;
  facture: InvoiceResponse;
  montant_pec: number;
}
