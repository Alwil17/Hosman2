export class PatientInsuranceRequest {
  taux: number;
  date_debut?: Date;
  date_fin?: Date;

  constructor(taux: number, date_debut?: Date, date_fin?: Date) {
    this.taux = taux;
    this.date_debut = date_debut;
    this.date_fin = date_fin;
  }
}
