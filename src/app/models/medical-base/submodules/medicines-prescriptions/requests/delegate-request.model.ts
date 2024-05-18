import { LaboratoryRequest } from "./laboratory-request.model";

export interface IDelegateRequest {
  nom: string;
  prenoms: string;
  tel1: string;
  tel2?: string;
  email?: string;
  adresse?: string;
  laboratoire?: LaboratoryRequest;
}

export class DelegateRequest {
  nom: string;
  prenoms: string;
  tel1: string;
  tel2?: string;
  email?: string;
  adresse?: string;
  laboratoire?: LaboratoryRequest;

  constructor(iDelegateRequest: IDelegateRequest) {
    this.nom = iDelegateRequest.nom;
    this.prenoms = iDelegateRequest.prenoms;
    this.tel1 = iDelegateRequest.tel1;
    this.tel2 = iDelegateRequest.tel2;
    this.email = iDelegateRequest.email;
    this.adresse = iDelegateRequest.adresse;
    this.laboratoire = iDelegateRequest.laboratoire;
  }
}
