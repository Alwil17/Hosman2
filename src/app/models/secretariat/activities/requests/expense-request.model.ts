import { ExpenseRubricRequest } from "./expense-rubric-request.model";
import { PersonRequest } from "./person-request";

export interface IExpenseRequest {
  date_depense: Date;
  beneficiaire: PersonRequest;
  montant: number;
  motif: string;
  rubrique: ExpenseRubricRequest;
  // accordeur: string;
  recu: number;
}

export class ExpenseRequest {
  date_depense: Date;
  beneficiaire: PersonRequest;
  montant: number;
  motif: string;
  rubrique: ExpenseRubricRequest;
  // accordeur: string;
  recu: number;

  constructor(iExpense: IExpenseRequest) {
    this.date_depense = iExpense.date_depense;
    this.beneficiaire = iExpense.beneficiaire;
    this.montant = iExpense.montant;
    this.motif = iExpense.motif;
    this.rubrique = iExpense.rubrique;
    // this.accordeur = iExpense.accordeur;
    this.recu = iExpense.recu;
  }
}
