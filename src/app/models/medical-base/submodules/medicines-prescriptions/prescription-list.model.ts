import { Prescription } from "./prescription.model";

export interface IPrescriptionList {
  id: number;
  reference: string;
  patient_ref: string;
  indicateur1?: string;
  indicateur2?: string;
  stocked?: boolean;
  prepositionned?: boolean;
  prescriptions: Prescription[];
  created_at: string;
  updated_at: string;
}

export class PrescriptionList {
  id: number;
  reference: string;
  patient_ref: string;
  indicateur1?: string;
  indicateur2?: string;
  stocked?: boolean;
  prepositionned?: boolean;
  prescriptions: Prescription[];
  created_at: string;
  updated_at: string;

  constructor(iPrescriptionList: IPrescriptionList) {
    this.id = iPrescriptionList.id;
    this.reference = iPrescriptionList.reference;
    this.patient_ref = iPrescriptionList.patient_ref;
    this.indicateur1 = iPrescriptionList.indicateur1;
    this.indicateur2 = iPrescriptionList.indicateur2;
    this.stocked = iPrescriptionList.stocked;
    this.prepositionned = iPrescriptionList.prepositionned;
    this.prescriptions = iPrescriptionList.prescriptions;
    this.created_at = iPrescriptionList.created_at;
    this.updated_at = iPrescriptionList.updated_at;
  }

  get medicines(): string {
    let medicines = "";
    this.prescriptions.forEach(
      (prescription) =>
        (medicines +=
          prescription.produit.nom +
          " " +
          prescription.conditionnement +
          " " +
          prescription.forme.dosage +
          "\n")
    );

    return medicines;
  }
}
