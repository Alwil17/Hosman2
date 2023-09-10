import { ExpenseRubricResponse } from "./responses/expense-rubric-response.model";

export interface IExpenseRubric {
  id: number;
  nom: string;
}

export class ExpenseRubric {
  id: number;
  nom: string;

  constructor(iExpenseRubric: IExpenseRubric) {
    this.id = iExpenseRubric.id;
    this.nom = iExpenseRubric.nom;
  }

  static fromResponse(expenseRubric: ExpenseRubricResponse) {
    return new ExpenseRubric({
      id: expenseRubric.id,
      nom: expenseRubric.nom,
    });
  }
}
