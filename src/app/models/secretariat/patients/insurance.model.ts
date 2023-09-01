import { InsuranceType } from "./insurance-type.model";
import { InsuranceResponse } from "./responses/insurance-response.model";

export interface IInsurance {
  id: number;
  nom: string;
  type_assurance: InsuranceType;
  email?: string;
  tel1?: string;
  tel2?: string;
  representant?: string;
}

export class Insurance {
  id: number;
  nom: string;
  type_assurance: InsuranceType;
  email?: string;
  tel1?: string;
  tel2?: string;
  representant?: string;

  constructor(iInsurance: IInsurance) {
    this.id = iInsurance.id;
    this.nom = iInsurance.nom;
    this.type_assurance = iInsurance.type_assurance;
    this.email = iInsurance.email;
    this.tel1 = iInsurance.tel1;
    this.tel2 = iInsurance.tel2;
    this.representant = iInsurance.representant;
  }

  static fromResponse(insurance: InsuranceResponse) {
    return new Insurance({
      id: insurance.id,
      nom: insurance.nom,
      type_assurance: insurance.type_assurance,
      email: insurance.email,
      tel1: insurance.tel1,
      tel2: insurance.tel2,
      representant: insurance.representant,
    });
  }

  // constructor(
  //   id: number,
  //   reference: string,
  //   nom: string,
  //   type_assurance: InsuranceType,
  //   email?: string,
  //   tel1?: string,
  //   tel2?: string,
  //   representant?: string
  // ) {
  //   this.id = id;
  //   this.reference = reference;
  //   this.nom = nom;
  //   this.type_assurance = type_assurance;
  //   this.email = email;
  //   this.tel1 = tel1;
  //   this.tel2 = tel2;
  //   this.representant = representant;
  // }

  // static emptyInsurance() {
  //   return new Insurance(-1, "", "", new InsuranceType(-1, ""));
  // }
}
