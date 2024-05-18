import { ExpenseRubricResponse } from "./expense-rubric-response.model";
import { PersonResponse } from "./person-response.model";

export interface ExpenseResponse {
  id: number;
  date_depense: Date;
  beneficiaire: PersonResponse;
  montant: number;
  motif: string;
  rubrique: ExpenseRubricResponse;
  accordeur: string;
  recu: number;
}
