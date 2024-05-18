import { Agency } from "./agency.model";

export interface ILaboratory {
  id: number;
  nom: string;
  tel1?: string;
  tel2?: string;
  email?: string;
  adresse?: string;
  agence?: Agency;
  slug: string;
}

export class Laboratory {
  id: number;
  nom: string;
  tel1?: string;
  tel2?: string;
  email?: string;
  adresse?: string;
  agence?: Agency;
  slug: string;

  constructor(iLaboratory: ILaboratory) {
    this.id = iLaboratory.id;
    this.nom = iLaboratory.nom;
    this.tel1 = iLaboratory.tel1;
    this.tel2 = iLaboratory.tel2;
    this.email = iLaboratory.email;
    this.adresse = iLaboratory.adresse;
    this.agence = iLaboratory.agence;
    this.slug = iLaboratory.slug;
  }
}
