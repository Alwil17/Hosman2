import { Form } from "./form.model";
import { Product } from "./product.model";

export interface IPrescription {
  id: number;
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

  produit: Product;
  forme: Form;
}

export class Prescription {
  id: number;
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

  produit: Product;
  forme: Form;

  constructor(iPrescription: IPrescription) {
    this.id = iPrescription.id;
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

    this.produit = iPrescription.produit;
    this.forme = iPrescription.forme;
  }
}
