export interface IPersonRequest {
  nom?: string;
  prenoms?: string;
  tel1?: string;
  tel2?: string;
  type_piece?: string;
  no_piece?: string;
  adresse?: string;
}

export class PersonRequest {
  nom?: string;
  prenoms?: string;
  tel1?: string;
  tel2?: string;
  type_piece?: string;
  no_piece?: string;
  adresse?: string;

  constructor(iPerson: IPersonRequest) {
    this.nom = iPerson.nom;
    this.prenoms = iPerson.prenoms;
    this.tel1 = iPerson.tel1;
    this.tel2 = iPerson.tel2;
    this.type_piece = iPerson.type_piece;
    this.no_piece = iPerson.no_piece;
    this.adresse = iPerson.adresse;
  }
}
