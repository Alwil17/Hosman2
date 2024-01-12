import { PhoneBookGroup } from "./phone-book-group.model";
import { PhoneBookResponse } from "./responses/phone-book-response.model";

export interface IPhoneBook {
  id: number;
  nom?: string;
  prenom?: string;
  profession?: string;
  secteur?: string;
  bureau?: string;
  tel1: string;
  tel2?: string;
  domicile?: string;
  email?: string;
  bip?: string;
  no_poste?: string;
  categorie_slug: string;
  categorie: PhoneBookGroup;
}

export class PhoneBook {
  id: number;
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
  categorie_slug: string;
  categorie: PhoneBookGroup;

  constructor(iPhoneBook: IPhoneBook) {
    this.id = iPhoneBook.id;
    this.nom = iPhoneBook.nom;
    this.prenom = iPhoneBook.prenom;
    this.profession = iPhoneBook.profession;
    this.secteur = iPhoneBook.secteur;
    this.bureau = iPhoneBook.bureau;
    this.tel1 = iPhoneBook.tel1;
    this.tel2 = iPhoneBook.tel2;
    this.domicile = iPhoneBook.domicile;
    this.email = iPhoneBook.email;
    this.bip = iPhoneBook.bip;
    this.no_poste = iPhoneBook.no_poste;
    this.categorie_slug = iPhoneBook.categorie_slug;
    this.categorie = iPhoneBook.categorie;
  }

  static fromResponse(phoneBook: PhoneBookResponse): PhoneBook {
    return new PhoneBook({
      id: phoneBook.id,
      nom: phoneBook.nom,
      prenom: phoneBook.prenom,
      profession: phoneBook.profession,
      secteur: phoneBook.secteur,
      bureau: phoneBook.bureau,
      tel1: phoneBook.tel1,
      tel2: phoneBook.tel2,
      domicile: phoneBook.domicile,
      email: phoneBook.email,
      bip: phoneBook.bip,
      no_poste: phoneBook.no_poste,
      categorie_slug: phoneBook.categorie_slug,
      categorie: PhoneBookGroup.fromResponse(phoneBook.categorie),
    });
  }
}
