export interface IPhoneBookRequest {
  nom?: string;
  prenom?: string;
  profession?: string;
  secteur?: string;
  bureau?: string;
  tel1?: string;
  tel2?: string;
  domicile?: string;
  email?: string;
  bip?: string;
  no_poste?: string;
  categorie?: string;
}

export class PhoneBookRequest {
  nom?: string;
  prenom?: string;
  profession?: string;
  secteur?: string;
  bureau?: string;
  tel1?: string;
  tel2?: string;
  domicile?: string;
  email?: string;
  bip?: string;
  no_poste?: string;
  categorie?: string;

  constructor(iPhoneBookRequest: IPhoneBookRequest) {
    this.nom = iPhoneBookRequest.nom;
    this.prenom = iPhoneBookRequest.prenom;
    this.profession = iPhoneBookRequest.profession;
    this.secteur = iPhoneBookRequest.secteur;
    this.bureau = iPhoneBookRequest.bureau;
    this.tel1 = iPhoneBookRequest.tel1;
    this.tel2 = iPhoneBookRequest.tel2;
    this.domicile = iPhoneBookRequest.domicile;
    this.email = iPhoneBookRequest.email;
    this.bip = iPhoneBookRequest.bip;
    this.no_poste = iPhoneBookRequest.no_poste;
    this.categorie = iPhoneBookRequest.categorie;
  }
}
