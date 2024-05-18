import { Invoice } from "./invoice.model";
import { Patient } from "./patient.model";
import { DebtResponse } from "./responses/debt-response.model";
import { Status } from "./status.model";

export interface IDebt {
  id: number;
  facture_ref?: string;
  montant: number;
  date_operation: Date;
  date_retrait: Date;
  patient: Patient;
  etat: Status;
}

export class Debt {
  id: number;
  facture_ref?: string;
  montant: number;
  date_operation: Date;
  date_retrait: Date;
  patient: Patient;
  etat: Status;

  constructor(iDebt: IDebt) {
    this.id = iDebt.id;
    this.facture_ref = iDebt.facture_ref;
    this.montant = iDebt.montant;
    this.date_operation = iDebt.date_operation;
    this.date_retrait = iDebt.date_retrait;
    this.patient = iDebt.patient;
    this.etat = iDebt.etat;
  }

  static fromResponse(debt: DebtResponse) {
    return new Debt({
      id: debt.id,
      facture_ref: debt.facture_ref,
      montant: debt.montant,
      date_operation: debt.date_operation,
      date_retrait: debt.date_retrait,
      patient: Patient.fromResponse(debt.patient),
      etat: Status.fromResponse(debt.etat),
    });
  }
}
