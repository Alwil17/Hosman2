import { DoctorResponse } from "../../shared/responses/doctor-response.model";
import { SectorResponse } from "../../shared/responses/sector-response.model";
import { TariffResponse } from "../../shared/responses/tariff-response.model";
import { InvoiceResponse } from "./invoice-response.model";
import { PatientResponse } from "./patient-response.model";

export interface PrestationResponse {
  id: number;
  patient: PatientResponse;
  provenance?: string;
  demandeur?: DoctorResponse;
  consulteur?: DoctorResponse;
  secteur?: SectorResponse;
  date_prestation: Date;
  tarifs: TariffResponse[];
  // facture: InvoiceResponse;
}
