import { PatientInsuranceResponse } from "./responses/patient-insurance-response.model";

export interface IPatientInsurance {
  taux: number;
  date_debut?: Date;
  date_fin?: Date;
}

export class PatientInsurance {
  taux: number;
  date_debut?: Date;
  date_fin?: Date;

  constructor(iPatientInsurance: IPatientInsurance) {
    this.taux = iPatientInsurance.taux;
    this.date_debut = iPatientInsurance.date_debut;
    this.date_fin = iPatientInsurance.date_fin;
  }

  static fromResponse(patientInsurance: PatientInsuranceResponse) {
    return new PatientInsurance({
      taux: patientInsurance.taux,
      date_debut: patientInsurance.date_debut,
      date_fin: patientInsurance.date_fin,
    });
  }

  // constructor(taux: number, date_debut?: Date, date_fin?: Date) {
  //   this.taux = taux;
  //   this.date_debut = date_debut;
  //   this.date_fin = date_fin;
  // }
}
