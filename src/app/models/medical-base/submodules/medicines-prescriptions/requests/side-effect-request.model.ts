export interface ISideEffectRequest {
  libelle: string;
  //   slug: string;
  produit_id?: number;
}

export class SideEffectRequest {
  libelle: string;
  //   slug: string;
  produit_id?: number;

  constructor(iSideEffectRequest: ISideEffectRequest) {
    this.libelle = iSideEffectRequest.libelle;
    // this.slug = iSideEffectRequest.slug;
    this.produit_id = iSideEffectRequest.produit_id;
  }
}
