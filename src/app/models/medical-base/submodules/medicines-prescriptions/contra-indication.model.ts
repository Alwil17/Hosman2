export interface IContraIndication {
  libelle: string;
  slug: string;
}

export class ContraIndication {
  libelle: string;
  slug: string;

  constructor(iContraIndication: IContraIndication) {
    this.libelle = iContraIndication.libelle;
    this.slug = iContraIndication.slug;
  }
}
