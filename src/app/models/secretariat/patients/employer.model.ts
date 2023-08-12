export class Employer {
  id: number;
  nom: string;
  email?: string;
  tel1?: string;
  tel2?: string;

  constructor(
    id: number,
    nom: string,
    email?: string,
    tel1?: string,
    tel2?: string
  ) {
    this.id = id;
    this.nom = nom;
    this.email = email;
    this.tel1 = tel1;
    this.tel2 = tel2;
  }
}
