import { ExpenseRubric } from "./expense-rubric.model";
import { Person } from "./person.model";
import { ExpenseResponse } from "./responses/expense-response.model";

export interface IExpense {
  id: number;
  date_depense: Date;
  beneficiaire: Person;
  montant: number;
  motif: string;
  rubrique: ExpenseRubric;
  accordeur: string;
  recu: number;
}

export class Expense {
  id: number;
  date_depense: Date;
  beneficiaire: Person;
  montant: number;
  motif: string;
  rubrique: ExpenseRubric;
  accordeur: string;
  recu: number;

  constructor(iExpense: IExpense) {
    this.id = iExpense.id;
    this.date_depense = iExpense.date_depense;
    this.beneficiaire = iExpense.beneficiaire;
    this.montant = iExpense.montant;
    this.motif = iExpense.motif;
    this.rubrique = iExpense.rubrique;
    this.accordeur = iExpense.accordeur;
    this.recu = iExpense.recu;
  }

  static fromResponse(expense: ExpenseResponse) {
    return new Expense({
      id: expense.id,
      date_depense: expense.date_depense,
      beneficiaire: Person.fromResponse(expense.beneficiaire),
      montant: expense.montant,
      motif: expense.motif,
      rubrique: ExpenseRubric.fromResponse(expense.rubrique),
      accordeur: expense.accordeur,
      recu: expense.recu,
    });
  }
}
