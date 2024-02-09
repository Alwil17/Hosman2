export interface IPosology {
  libelle: string;
  slug: string;
  type: string;
}

export class Posology {
  libelle: string;
  slug: string;
  type: string;

  constructor(iPosology: IPosology) {
    this.libelle = iPosology.libelle;
    this.slug = iPosology.slug;
    this.type = iPosology.type;
  }
}
