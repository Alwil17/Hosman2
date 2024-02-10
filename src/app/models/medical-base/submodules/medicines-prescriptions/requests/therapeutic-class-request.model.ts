export interface ITherapeuticClassRequest {
  nom: string;
  //   slug: string;
  couleur?: string;
}

export class TherapeuticClassRequest {
  nom: string;
  //   slug: string;
  couleur?: string;

  constructor(iTherapeuticClassRequest: ITherapeuticClassRequest) {
    this.nom = iTherapeuticClassRequest.nom;
    // this.slug = iTherapeuticClassRequest.slug;
    this.couleur = iTherapeuticClassRequest.couleur;
  }
}

export interface ITherapeuticClassProductRequest {
  classe_id: number;
}

export class TherapeuticClassProductRequest {
  classe_id: number;

  constructor(
    iTherapeuticClassProductRequest: ITherapeuticClassProductRequest
  ) {
    this.classe_id = iTherapeuticClassProductRequest.classe_id;
  }
}
