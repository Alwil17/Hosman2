export interface IPosologyRequest {
  libelle: string;
  //   slug: string;
  type: string;
  produit_id?: number;
}

export class PosologyRequest {
  libelle: string;
  //   slug: string;
  type: string;
  produit_id?: number;

  constructor(iPosologyRequest: IPosologyRequest) {
    this.libelle = iPosologyRequest.libelle;
    // this.slug = iPosologyRequest.slug;
    this.type = iPosologyRequest.type;
    this.produit_id = iPosologyRequest.produit_id;
  }
}
