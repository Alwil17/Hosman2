export interface IDebtRequest {
  montant: number;
  date_operation: Date;
  // patient_id?: number;
  etat_id: number;
}

export class DebtRequest {
  montant: number;
  date_operation: Date;
  // patient_id?: number;
  etat_id: number;

  constructor(iDebt: IDebtRequest) {
    this.montant = iDebt.montant;
    this.date_operation = iDebt.date_operation;
    // this.patient_id = iDebt.patient_id;
    this.etat_id = iDebt.etat_id;
  }
}
