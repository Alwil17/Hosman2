export interface IIndication {
  libelle: string;
  slug: string;
}

export class Indication {
  libelle: string;
  slug: string;

  constructor(iIndication: IIndication) {
    this.libelle = iIndication.libelle;
    this.slug = iIndication.slug;
  }
}
