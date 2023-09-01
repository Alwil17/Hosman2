import { PersonToContactResponse } from "./responses/person-to-contact-response.model";

export interface IPersonToContact {
  id: number;
  nom: string;
  prenoms: string;
  tel: string;
  adresse: string;
}

export class PersonToContact {
  id: number;
  nom: string;
  prenoms: string;
  tel: string;
  adresse: string;

  constructor(iPersonToContact: IPersonToContact) {
    this.id = iPersonToContact.id;
    this.nom = iPersonToContact.nom;
    this.prenoms = iPersonToContact.prenoms;
    this.tel = iPersonToContact.tel;
    this.adresse = iPersonToContact.adresse;
  }

  static fromResponse(personToContact: PersonToContactResponse) {
    return new PersonToContact({
      id: personToContact.id,
      nom: personToContact.nom,
      prenoms: personToContact.prenoms,
      tel: personToContact.tel,
      adresse: personToContact.adresse,
    });
  }

  toString() {
    return (
      this.nom + ", " + this.prenoms + ", " + this.tel + ", " + this.adresse
    );
  }

  // constructor(
  //   id: number,
  //   nom: string,
  //   prenoms: string,
  //   tel: string,
  //   adresse: string
  // ) {
  //   this.id = id;
  //   this.nom = nom;
  //   this.prenoms = prenoms;
  //   this.tel = tel;
  //   this.adresse = adresse;
  // }
}
