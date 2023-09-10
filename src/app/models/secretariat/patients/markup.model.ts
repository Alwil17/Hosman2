import { Patient } from "./patient.model";
import { MarkupResponse } from "./responses/markup-response.model";

export interface IMarkup {
  id: number;
  montant: number;
  motif?: string;
  date_operation: Date;
  // patient: Patient;
}

export class Markup {
  id: number;
  montant: number;
  motif?: string;
  date_operation: Date;
  // patient: Patient;

  constructor(iMarkup: IMarkup) {
    this.id = iMarkup.id;
    this.montant = iMarkup.montant;
    this.motif = iMarkup.motif;
    this.date_operation = iMarkup.date_operation;
    // this.patient = iMarkup.patient;
  }

  static fromResponse(markup: MarkupResponse): Markup {
    return new Markup({
      id: markup.id,
      montant: markup.montant,
      motif: markup.motif,
      date_operation: markup.date_operation,
      // patient: Patient.fromResponse(markup.patient),
    });
  }
}
