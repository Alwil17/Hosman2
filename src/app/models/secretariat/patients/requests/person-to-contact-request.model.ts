export class PersonToContactRequest {
  nom: string;
  prenoms: string;
  tel: string;
  adresse: string;

  constructor(nom: string, prenoms: string, tel: string, adresse: string) {
    this.nom = nom;
    this.prenoms = prenoms;
    this.tel = tel;
    this.adresse = adresse;
  }
}
