import { DebtResponse } from "./debt-response.model";
import { DiscountResponse } from "./discount-response.model";
import { MarkupResponse } from "./markup-response.model";
import { PrestationResponse } from "./prestation-response.model";
import { RemainderResponse } from "./remainder-response.model";
import { StatusResponse } from "./status-response.model";

export interface InvoiceResponse {
  id: number;
  reference: string;
  total: number;
  montant_pec: number;
  reduction: DiscountResponse;
  majoration: MarkupResponse;
  a_payer: number;
  creance: DebtResponse;
  reliquat: RemainderResponse;
  date_facture: Date;
  date_reglement: Date;
  prestation_id: number;
  etat: StatusResponse;
  exporte: number;
  prestation: PrestationResponse;
  mode_payements: {
    id: number;
    nom: string;
    slug: string;
  }[];
}
