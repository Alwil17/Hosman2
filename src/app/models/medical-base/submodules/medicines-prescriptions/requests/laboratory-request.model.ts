import { AgencyRequest } from "./agency-request.model";

export interface ILaboratoryRequest {
  nom: string;
  tel1: string;
  tel2: string;
  email: string;
  adresse: string;
  agence: AgencyRequest;
}

export class LaboratoryRequest {
  nom: string;
  tel1: string;
  tel2: string;
  email: string;
  adresse: string;
  agence: AgencyRequest;

  constructor(iLaboratoryRequest: ILaboratoryRequest) {
    this.nom = iLaboratoryRequest.nom;
    this.tel1 = iLaboratoryRequest.tel1;
    this.tel2 = iLaboratoryRequest.tel2;
    this.email = iLaboratoryRequest.email;
    this.adresse = iLaboratoryRequest.adresse;
    this.agence = iLaboratoryRequest.agence;
  }
}
