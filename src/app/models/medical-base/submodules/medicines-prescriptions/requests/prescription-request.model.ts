export interface IPrescriptionRequest {
  presentation: string;
  qte: number;
  conditionnement: string;
  dose_qte?: number;
  dose?: string;
  periode?: string;
  adverbe?: string;
  duree_qte?: number;
  duree?: string;
  note?: string;
  heures?: string[];

  produit_id: number;
  forme_id: number;
}

export class PrescriptionRequest {
  presentation: string;
  qte: number;
  conditionnement: string;
  dose_qte?: number;
  dose?: string;
  periode?: string;
  adverbe?: string;
  duree_qte?: number;
  duree?: string;
  note?: string;
  heures?: string[];

  produit_id: number;
  forme_id: number;

  constructor(iPrescription: IPrescriptionRequest) {
    this.presentation = iPrescription.presentation;
    this.qte = iPrescription.qte;
    this.conditionnement = iPrescription.conditionnement;
    this.dose_qte = iPrescription.dose_qte;
    this.dose = iPrescription.dose;
    this.periode = iPrescription.periode;
    this.adverbe = iPrescription.adverbe;
    this.duree_qte = iPrescription.duree_qte;
    this.duree = iPrescription.duree;
    this.note = iPrescription.note;
    this.heures = iPrescription.heures;

    this.produit_id = iPrescription.produit_id;
    this.forme_id = iPrescription.forme_id;
  }
}
