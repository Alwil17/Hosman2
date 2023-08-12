import { InsuranceTypeRequest } from "./insurance-type-request.model";

export class InsuranceRequest {
  nom: string;
  type_assurance: InsuranceTypeRequest;
  email?: string;
  tel1?: string;
  tel2?: string;
  representant?: string;
  id?: number;
  reference?: string;
  // taux ?

  constructor(
    nom: string,
    type_assurance: InsuranceTypeRequest,
    email?: string,
    tel1?: string,
    tel2?: string,
    representant?: string,
    id?: number,
    reference?: string
  ) {
    this.nom = nom;
    this.type_assurance = type_assurance;
    this.email = email;
    this.tel1 = tel1;
    this.tel2 = tel2;
    this.representant = representant;
    this.id = id;
    this.reference = reference;
  }
}
