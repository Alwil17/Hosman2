export interface ITherapeuticClassRequest {
  nom: string;
  //   slug: string;
  couleur: string;
}

export class TherapeuticClassRequest {
  nom: string;
  //   slug: string;
  couleur: string;

  constructor(iTherapeuticClassRequest: TherapeuticClassRequest) {
    this.nom = iTherapeuticClassRequest.nom;
    // this.slug = iTherapeuticClassRequest.slug;
    this.couleur = iTherapeuticClassRequest.couleur;
  }
}
