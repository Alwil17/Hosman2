export interface IAgencyRequest {
  nom: string;
  directeur?: string;
  email?: string;
  tel1?: string;
  tel2?: string;
  adresse?: string;
}

export class AgencyRequest {
  nom: string;
  directeur?: string;
  email?: string;
  tel1?: string;
  tel2?: string;
  adresse?: string;

  constructor(iAgencyRequest: IAgencyRequest) {
    this.nom = iAgencyRequest.nom;
    this.directeur = iAgencyRequest.directeur;
    this.email = iAgencyRequest.email;
    this.tel1 = iAgencyRequest.tel1;
    this.tel2 = iAgencyRequest.tel2;
    this.adresse = iAgencyRequest.adresse;
  }
}
