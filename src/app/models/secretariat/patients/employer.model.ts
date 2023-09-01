import { EmployerResponse } from "./responses/employer-response.model";

export interface IEmployer {
  id: number;
  nom: string;
  email?: string;
  tel1?: string;
  tel2?: string;
}

export class Employer {
  id: number;
  nom: string;
  email?: string;
  tel1?: string;
  tel2?: string;

  constructor(iEmployer: IEmployer) {
    this.id = iEmployer.id;
    this.nom = iEmployer.nom;
    this.email = iEmployer.email;
    this.tel1 = iEmployer.tel1;
    this.tel2 = iEmployer.tel2;
  }

  static fromResponse(employer: EmployerResponse) {
    return new Employer({
      id: employer.id,
      nom: employer.nom,
      email: employer.email,
      tel1: employer.tel1,
      tel2: employer.tel2,
    });
  }

  // constructor(
  //   id: number,
  //   nom: string,
  //   email?: string,
  //   tel1?: string,
  //   tel2?: string
  // ) {
  //   this.id = id;
  //   this.nom = nom;
  //   this.email = email;
  //   this.tel1 = tel1;
  //   this.tel2 = tel2;
  // }
}
