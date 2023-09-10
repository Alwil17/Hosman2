export interface IExpenseRubricRequest {
  // id: number;
  nom: string;
}

export class ExpenseRubricRequest {
  // id: number;
  nom: string;

  constructor(iExpenseRubric: IExpenseRubricRequest) {
    // this.id = iExpenseRubric.id;
    this.nom = iExpenseRubric.nom;
  }
}
