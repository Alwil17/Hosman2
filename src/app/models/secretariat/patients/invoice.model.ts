import { Debt } from "./debt.model";
import { Discount } from "./discount.model";
import { Markup } from "./markup.model";
import { Prestation } from "./prestation.model";
import { Remainder } from "./remainder.model";
import { InvoiceResponse } from "./responses/invoice-response.model";
import { Status } from "./status.model";

export interface IInvoice {
  id: number;
  reference: string;
  total: number;
  montant_pec: number;
  reduction: Discount;
  majoration: Markup;
  a_payer: number;
  creance: Debt;
  reliquat: Remainder;
  date_facture: Date;
  date_reglement: Date;
  etat: Status;
  exporte: number;
  prestation: Prestation;
  //   mode_payements: [
  //     {
  //       facture_id: number;
  //       mode_payement_id: number;
  //       montant: number;
  //     }
  //   ];
}

export class Invoice {
  id: number;
  reference: string;
  total: number;
  montant_pec: number;
  reduction: Discount;
  majoration: Markup;
  a_payer: number;
  creance: Debt;
  reliquat: Remainder;
  date_facture: Date;
  date_reglement: Date;
  etat: Status;
  exporte: number;
  prestation: Prestation;

  constructor(iInvoice: IInvoice) {
    this.id = iInvoice.id;
    this.reference = iInvoice.reference;
    this.total = iInvoice.total;
    this.montant_pec = iInvoice.montant_pec;
    this.reduction = iInvoice.reduction;
    this.majoration = iInvoice.majoration;
    this.a_payer = iInvoice.a_payer;
    this.creance = iInvoice.creance;
    this.reliquat = iInvoice.reliquat;
    this.date_facture = iInvoice.date_facture;
    this.date_reglement = iInvoice.date_reglement;
    this.etat = iInvoice.etat;
    this.exporte = iInvoice.exporte;
    this.prestation = iInvoice.prestation;
  }

  static fromResponse(invoice: InvoiceResponse) {
    return new Invoice({
      id: invoice.id,
      reference: invoice.reference,
      total: invoice.total,
      montant_pec: invoice.montant_pec,
      reduction: Discount.fromResponse(invoice.reduction),
      majoration: Markup.fromResponse(invoice.majoration),
      a_payer: invoice.a_payer,
      creance: Debt.fromResponse(invoice.creance), // invoice.creance,
      reliquat: Remainder.fromResponse(invoice.reliquat),
      date_facture: invoice.date_facture,
      date_reglement: invoice.date_reglement,
      etat: Status.fromResponse(invoice.etat),
      exporte: invoice.exporte,
      prestation: Prestation.fromResponse(invoice.prestation),
    });
  }
}
