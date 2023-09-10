import { Patient } from "./patient.model";
import { RemainderResponse } from "./responses/remainder-response.model";
import { Status } from "./status.model";

export interface IRemainder {
  id: number;
  montant: number;
  date_operation: Date;
  // patient: Patient;
  // facture
  etat: Status;
}

export class Remainder {
  id: number;
  montant: number;
  date_operation: Date;
  // patient: Patient;
  // facture
  etat: Status;

  constructor(iRemainder: IRemainder) {
    this.id = iRemainder.id;
    this.montant = iRemainder.montant;
    this.date_operation = iRemainder.date_operation;
    // this.patient = iRemainder.patient
    this.etat = iRemainder.etat;
  }

  static fromResponse(remainder: RemainderResponse) {
    return new Remainder({
      id: remainder.id,
      montant: remainder.montant,
      date_operation: remainder.date_operation,
      // patient: remainder.patient
      etat: Status.fromResponse(remainder.etat),
    });
  }
}
