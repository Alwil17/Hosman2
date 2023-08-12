export class EmployerRequest {
  nom: string;
  email?: string;
  tel1?: string;
  tel2?: string;
  id?: number;

  constructor(
    nom: string,
    email?: string,
    tel1?: string,
    tel2?: string,
    id?: number
  ) {
    this.nom = nom;
    this.email = email;
    this.tel1 = tel1;
    this.tel2 = tel2;
    this.id = id;
  }
}
