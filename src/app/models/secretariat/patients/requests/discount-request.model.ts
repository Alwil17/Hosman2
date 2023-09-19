export interface IDiscountRequest {
  montant: number;
  motif: string;
  date_operation: Date;
  // patient_id?: number;
}

export class DiscountRequest {
  montant: number;
  motif: string;
  date_operation: Date;
  // patient_id?: number;

  constructor(iDiscount: IDiscountRequest) {
    this.montant = iDiscount.montant;
    this.motif = iDiscount.motif;
    this.date_operation = iDiscount.date_operation;
    // this.patient_id = iDiscount.patient_id;
  }
}
