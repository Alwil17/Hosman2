export interface IContraIndicationRequest {
  libelle: string;
  // slug: string;
  produit_id?: number;
}

export class ContraIndicationRequest {
  libelle: string;
  // slug: string;
  produit_id?: number;

  constructor(iContraIndicationRequest: IContraIndicationRequest) {
    this.libelle = iContraIndicationRequest.libelle;
    // this.slug = iContraIndicationRequest.slug;
    this.produit_id = iContraIndicationRequest.produit_id;
  }
}
