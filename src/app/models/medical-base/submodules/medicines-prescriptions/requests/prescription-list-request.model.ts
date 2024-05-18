import { PrescriptionRequest } from "./prescription-request.model";

export interface IPrescriptionListRequest {
  patient_ref: string;
  consultation_id: number;
  indicateur1?: string;
  indicateur2?: string;
  stocked?: boolean;
  prepositionned?: boolean;
  prescriptions: PrescriptionRequest[];
}

export class PrescriptionListRequest {
  patient_ref: string;
  consultation_id: number;
  indicateur1?: string;
  indicateur2?: string;
  stocked?: boolean;
  prepositionned?: boolean;
  prescriptions: PrescriptionRequest[];

  constructor(iPrescriptionList: IPrescriptionListRequest) {
    this.patient_ref = iPrescriptionList.patient_ref;
    this.consultation_id = iPrescriptionList.consultation_id;
    this.indicateur1 = iPrescriptionList.indicateur1;
    this.indicateur2 = iPrescriptionList.indicateur2;
    this.stocked = iPrescriptionList.stocked;
    this.prepositionned = iPrescriptionList.prepositionned;
    this.prescriptions = iPrescriptionList.prescriptions;
  }
}
