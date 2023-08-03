export interface IPatientInsurance {
  id: number;
  patient_id: number;
  assurance_id: number;
  taux: number;
  date_debut?: Date;
  date_expiration: Date;
}
