export interface IPrestationRequest {
  patient_id: number;
  demandeur_id?: number;
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
  demandeur_id?: number;
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
    this.demandeur_id = iPrestation.demandeur_id;
    this.consulteur_id = iPrestation.consulteur_id;
    this.secteur_id = iPrestation.secteur_id;
    this.date_prestation = iPrestation.date_prestation;
    this.provenance = iPrestation.provenance;
    this.tarifs = iPrestation.tarifs;
  }
}
