import { InsuranceResponse } from "../../patients/responses/insurance-response.model";
import { InvoiceResponse } from "../../patients/responses/invoice-response.model";
import { PatientResponse } from "../../patients/responses/patient-response.model";
import { ActResponse } from "../../shared/responses/act-response.model";
import { TariffResponse } from "../../shared/responses/tariff-response.model";

export interface InsuranceDebtResponse {
  id: number;
  assurance: InsuranceResponse;
  patient: PatientResponse;
  tarif?: TariffResponse;
  facture: InvoiceResponse;
  montant_pec: number;
}
