import { Lit } from "./lit";

export interface IChambre {
  id: number;
  nom: string;
  slug: string;
  lits: Lit[]
}

export interface ChambreResponse {
  id: number;
  nom: string;
  slug: string;
  lits: Lit[]
}

export class Chambre {
  id: number;
  nom: string;
  slug: string;
  lits: Lit[];

  constructor(iChambre: IChambre) {
    this.id = iChambre.id;
    this.nom = iChambre.nom;
    this.slug = iChambre.slug;
    this.lits = iChambre.lits;
  }

  static fromResponse(chambre: ChambreResponse): Chambre {
    return new Chambre({
      id: chambre.id,
      nom: chambre.nom,
      slug: chambre.slug,
      lits: chambre.lits
    });
  }
}
