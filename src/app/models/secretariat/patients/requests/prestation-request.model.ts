import { DoctorRequest } from "../../shared/requests/doctor-request.model";

export interface IPrestationRequest {
  patient_id: number;
  demandeur?: DoctorRequest;
  consulteur_id: number;
  secteur_id?: number;
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
  consulteur_id: number;
  secteur_id?: number;
  date_prestation: Date;
  provenance?: string;
  tarifs: {
    tarif_id: number;
    quantite: number;
  }[];

  constructor(iPrestation: IPrestationRequest) {
    this.patient_id = iPrestation.patient_id;
    this.demandeur = iPrestation.demandeur;
    this.consulteur_id = iPrestation.consulteur_id;
    this.secteur_id = iPrestation.secteur_id;
    this.date_prestation = iPrestation.date_prestation;
    this.provenance = iPrestation.provenance;
    this.tarifs = iPrestation.tarifs;
  }
}
