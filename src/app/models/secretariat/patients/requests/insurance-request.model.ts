import { InsuranceTypeRequest } from "./insurance-type-request.model";

export class InsuranceRequest {
  nom: string;
  type_assurance_id: number;
  email?: string;
  tel1?: string;
  tel2?: string;
  representant?: string;
  id?: number;

  constructor(
    nom: string,
    type_assurance_id: number,
    email?: string,
    tel1?: string,
    tel2?: string,
    representant?: string,
    id?: number,
  ) {
    this.nom = nom;
    this.type_assurance_id = type_assurance_id;
    this.email = email;
    this.tel1 = tel1;
    this.tel2 = tel2;
    this.representant = representant;
    this.id = id;
  }
}
