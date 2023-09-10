import { Invoice } from "./invoice.model";
import { Patient } from "./patient.model";
import { DebtResponse } from "./responses/debt-response.model";
import { Status } from "./status.model";

export interface IDebt {
  id: number;
  montant: number;
  date_operation: Date;
  // patient: Patient;
  // facture: Invoice;
  etat: Status;
}

export class Debt {
  id: number;
  montant: number;
  date_operation: Date;
  // patient: Patient;
  // facture: Invoice;
  etat: Status;

  constructor(iDebt: IDebt) {
    this.id = iDebt.id;
    this.montant = iDebt.montant;
    this.date_operation = iDebt.date_operation;
    // this.patient = iDebt.patient
    // this.facture = iDebt.facture;
    this.etat = iDebt.etat;
  }

  static fromResponse(debt: DebtResponse) {
    return new Debt({
      id: debt.id,
      montant: debt.montant,
      date_operation: debt.date_operation,
      // patient: Patient.fromResponse(debt.patient),
      // facture: Invoice.fromResponse(debt.facture),
      etat: Status.fromResponse(debt.etat),
    });
  }
}
