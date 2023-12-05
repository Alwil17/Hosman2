import { DoctorRequest } from "../../shared/requests/doctor-request.model";

export interface IPrestationRequest {
  patient_id: number;
  demandeur?: DoctorRequest;
  consulteur: string;
  secteur_code?: string;
  date_prestation: Date;
  provenance?: string;
  tarifs: {
    tarif_id: number;
    quantite: number;
  }[];
}

export class PrestationRequest {
  patient_id: number;
  demandeur?: DoctorRequest;
  consulteur: string;
  secteur_code?: string;
  date_prestation: Date;
  provenance?: string;
  tarifs: {
    tarif_id: number;
    quantite: number;
  }[];

  constructor(iPrestation: IPrestationRequest) {
    this.patient_id = iPrestation.patient_id;
    this.demandeur = iPrestation.demandeur;
    this.consulteur = iPrestation.consulteur;
    this.secteur_code = iPrestation.secteur_code;
    this.date_prestation = iPrestation.date_prestation;
    this.provenance = iPrestation.provenance;
    this.tarifs = iPrestation.tarifs;
  }
}
