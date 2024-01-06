import { Insurance } from "../patients/insurance.model";
import { Invoice } from "../patients/invoice.model";
import { Patient } from "../patients/patient.model";
import { Act } from "../shared/act.model";
import { Tariff } from "../shared/tariff.model";
import { InsuranceDebtResponse } from "./responses/insurance-debt-response.model";

export interface IInsuranceDebt {
  id: number;
  assurance: Insurance;
  patient: Patient;
  tarif?: Tariff;
  facture: Invoice;
  montant_pec: number;
}

export class InsuranceDebt {
  id: number;
  assurance: Insurance;
  patient: Patient;
  tarif?: Tariff;
  facture: Invoice;
  montant_pec: number;

  constructor(iInsuranceDebt: IInsuranceDebt) {
    this.id = iInsuranceDebt.id;
    this.assurance = iInsuranceDebt.assurance;
    this.patient = iInsuranceDebt.patient;
    this.tarif = iInsuranceDebt.tarif;
    this.facture = iInsuranceDebt.facture;
    this.montant_pec = iInsuranceDebt.montant_pec;
  }

  static fromResponse(insuranceDebt: InsuranceDebtResponse) {
    return new InsuranceDebt({
      id: insuranceDebt.id,
      assurance: Insurance.fromResponse(insuranceDebt.assurance),
      patient: Patient.fromResponse(insuranceDebt.patient),
      tarif: insuranceDebt.tarif
        ? Tariff.fromResponse(insuranceDebt.tarif)
        : undefined,
      facture: Invoice.fromResponse(insuranceDebt.facture),
      montant_pec: insuranceDebt.montant_pec,
    });
  }
}
