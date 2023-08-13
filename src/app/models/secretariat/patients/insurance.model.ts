import { InsuranceType } from "./insurance-type.model";

export class Insurance {
  id: number;
  reference: string;
  nom: string;
  type_assurance: InsuranceType;
  email?: string;
  tel1?: string;
  tel2?: string;
  representant?: string;
  // taux ?

  constructor(
    id: number,
    reference: string,
    nom: string,
    type_assurance: InsuranceType,
    email?: string,
    tel1?: string,
    tel2?: string,
    representant?: string
  ) {
    this.id = id;
    this.reference = reference;
    this.nom = nom;
    this.type_assurance = type_assurance;
    this.email = email;
    this.tel1 = tel1;
    this.tel2 = tel2;
    this.representant = representant;
  }

  // static emptyInsurance() {
  //   return new Insurance(-1, "", "", new InsuranceType(-1, ""));
  // }
}
