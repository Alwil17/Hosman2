import { DebtRequest } from "./debt-request.model";
import { DiscountRequest } from "./discount-request.model";
import { MarkupRequest } from "./markup-request.model";
import { RemainderRequest } from "./remainder-request.model";

export interface IInvoiceRequest {
  //   reference: string;
  total: number;
  montant_pec?: number;
  reduction: DiscountRequest;
  majoration: MarkupRequest;
  a_payer: number;
  creance: DebtRequest;
  reliquat: RemainderRequest;
  date_facture: Date;
  date_reglement: Date;
  etat_id: number;
  patient_id: number;
  exporte?: number;
  mode_payements: {}[];
}

export class InvoiceRequest {
  total: number;
  montant_pec?: number;
  reduction: DiscountRequest;
  majoration: MarkupRequest;
  a_payer: number;
  creance: DebtRequest;
  reliquat: RemainderRequest;
  date_facture: Date;
  date_reglement: Date;
  etat_id: number;
  patient_id: number;
  exporte?: number;
  mode_payements: {}[];

  constructor(iInvoice: IInvoiceRequest) {
    this.total = iInvoice.total;
    this.montant_pec = iInvoice.montant_pec;
    this.reduction = iInvoice.reduction;
    this.majoration = iInvoice.majoration;
    this.a_payer = iInvoice.a_payer;
    this.creance = iInvoice.creance;
    this.reliquat = iInvoice.reliquat;
    this.date_facture = iInvoice.date_facture;
    this.date_reglement = iInvoice.date_reglement;
    this.etat_id = iInvoice.etat_id;
    this.patient_id = iInvoice.patient_id;
    this.exporte = iInvoice.exporte;
    this.mode_payements = iInvoice.mode_payements;
  }
}
