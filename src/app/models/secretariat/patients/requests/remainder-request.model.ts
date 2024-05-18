export interface IRemainderRequest {
  montant: number;
  date_operation: Date;
  // patient_id?: number;
  etat_id: number;
}

export class RemainderRequest {
  montant: number;
  date_operation: Date;
  // patient_id?: number;
  etat_id: number;

  constructor(iRemainder: IRemainderRequest) {
    this.montant = iRemainder.montant;
    this.date_operation = iRemainder.date_operation;
    // this.patient_id = iRemainder.patient_id;
    this.etat_id = iRemainder.etat_id;
  }
}
