export interface IAgency {
  id: number;
  nom: string;
  directeur?: string;
  email: string;
  tel1?: string;
  tel2?: string;
  adresse?: string;
  slug: string;
}

export class Agency {
  id: number;
  nom: string;
  directeur?: string;
  email: string;
  tel1?: string;
  tel2?: string;
  adresse?: string;
  slug: string;

  constructor(iAgency: IAgency) {
    this.id = iAgency.id;
    this.nom = iAgency.nom;
    this.directeur = iAgency.directeur;
    this.email = iAgency.email;
    this.tel1 = iAgency.tel1;
    this.tel2 = iAgency.tel2;
    this.adresse = iAgency.adresse;
    this.slug = iAgency.slug;
  }
}
