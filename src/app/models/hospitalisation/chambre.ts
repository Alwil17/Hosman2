export interface IChambre {
  id: number;
  nom: string;
  slug: string;
}

export interface ChambreResponse {
  id: number;
  nom: string;
  slug: string;
}

export class Chambre {
  id: number;
  nom: string;
  slug: string;

  constructor(iChambre: IChambre) {
    this.id = iChambre.id;
    this.nom = iChambre.nom;
    this.slug = iChambre.slug;
  }

  static fromResponse(chambre: ChambreResponse): Chambre {
    return new Chambre({
      id: chambre.id,
      nom: chambre.nom,
      slug: chambre.slug,
    });
  }
}
