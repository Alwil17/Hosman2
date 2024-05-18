export interface IMarkupRequest {
  montant: number;
  motif?: string;
  date_operation: Date;
  // patient_id?: number;
}

export class MarkupRequest {
  montant: number;
  motif?: string;
  date_operation: Date;
  // patient_id?: number;

  constructor(iMarkup: IMarkupRequest) {
    this.montant = iMarkup.montant;
    this.motif = iMarkup.motif;
    this.motif = iMarkup.motif;
    this.date_operation = iMarkup.date_operation;
    // this.patient_id = iMarkup.patient_id;
  }
}
