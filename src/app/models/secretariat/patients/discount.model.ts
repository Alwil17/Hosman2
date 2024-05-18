import { Patient } from "./patient.model";
import { DiscountResponse } from "./responses/discount-response.model";

export interface IDiscount {
  id: number;
  montant: number;
  motif: string;
  date_operation: Date;
  // patient: Patient;
}

export class Discount {
  id: number;
  montant: number;
  motif: string;
  date_operation: Date;
  // patient: Patient;

  constructor(iDiscount: IDiscount) {
    this.id = iDiscount.id;
    this.montant = iDiscount.montant;
    this.motif = iDiscount.motif;
    this.date_operation = iDiscount.date_operation;
    // this.patient = iDiscount.patient;
  }

  static fromResponse(discount: DiscountResponse): Discount {
    return new Discount({
      id: discount.id,
      montant: discount.montant,
      motif: discount.motif,
      date_operation: discount.date_operation,
      // patient: Patient.fromResponse(discount.patient),
    });
  }
}
