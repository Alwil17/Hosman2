import { SectorRequest } from "./sector-request.model";

export interface IDoctorRequest {
  matricule?: string;
  nom: string;
  prenoms: string;
  type: string;
  //   date_naissance: Date;
  //   sexe: string;
  //   tel1: string;
  // sector: SectorRequest;
  //   tel2?: string;
  //   email?: string;
  //   lieu_naissance?: string;
  //   type_piece?: string;
  //   no_piece?: string;
}

export class DoctorRequest {
  matricule?: string;
  nom: string;
  prenoms: string;
  type: string;
  //   date_naissance: Date;
  //   sexe: string;
  //   tel1: string;
  // sector: SectorRequest;
  //   tel2?: string;
  //   email?: string;
  //   lieu_naissance?: string;
  //   type_piece?: string;
  //   no_piece?: string;

  constructor(iDoctor: IDoctorRequest) {
    this.matricule = iDoctor.matricule;
    this.nom = iDoctor.nom;
    this.prenoms = iDoctor.prenoms;
    this.type = iDoctor.type;
  }
}
