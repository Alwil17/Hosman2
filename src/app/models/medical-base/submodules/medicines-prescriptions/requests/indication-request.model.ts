export interface IIndicationRequest {
  libelle: string;
  //   slug: string;
  produit_id?: number;
}

export class IndicationRequest {
  libelle: string;
  //   slug: string;
  produit_id?: number;

  constructor(iIndicationRequest: IIndicationRequest) {
    this.libelle = iIndicationRequest.libelle;
    // this.slug = iIndicationRequest.slug;
    this.produit_id = iIndicationRequest.produit_id;
  }
}
