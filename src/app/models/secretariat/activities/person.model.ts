import { PersonResponse } from "./responses/person-response.model";

export interface IPerson {
  id: number;
  nom: string;
  prenoms: string;
  tel1: string;
  tel2: string;
  type_piece: string;
  no_piece: string;
  adresse: string;
}

export class Person {
  id: number;
  nom: string;
  prenoms: string;
  tel1: string;
  tel2: string;
  type_piece: string;
  no_piece: string;
  adresse: string;

  constructor(iPerson: IPerson) {
    this.id = iPerson.id;
    this.nom = iPerson.nom;
    this.prenoms = iPerson.prenoms;
    this.tel1 = iPerson.tel1;
    this.tel2 = iPerson.tel2;
    this.type_piece = iPerson.type_piece;
    this.no_piece = iPerson.no_piece;
    this.adresse = iPerson.adresse;
  }

  static fromResponse(person: PersonResponse) {
    return new Person({
      id: person.id,
      nom: person.nom,
      prenoms: person.prenoms,
      tel1: person.tel1,
      tel2: person.tel2,
      type_piece: person.type_piece,
      no_piece: person.no_piece,
      adresse: person.adresse,
    });
  }

  get fullName() {
    return this.nom + " " + this.prenoms;
  }
}
