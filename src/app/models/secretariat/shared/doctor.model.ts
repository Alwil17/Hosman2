import { calculateExactAge } from "src/app/helpers/age-calculator";

import { Sector } from "./sector.model";
import { DoctorResponse } from "./responses/doctor-response.model";

export interface IDoctor {
  id: number;
  matricule: string;
  nom: string;
  prenoms: string;
  //   date_naissance: Date;
  //   sexe: string;
  //   tel1: string;
  //   sector: Sector;
  //   tel2?: string;
  //   email?: string;
  //   lieu_naissance?: string;
  //   type_piece?: string;
  //   no_piece?: string;
}

export class Doctor {
  id: number;
  matricule: string;
  nom: string;
  prenoms: string;
  //   date_naissance: Date;
  //   sexe: string;
  //   tel1: string;
  //   sector: Sector;
  //   tel2?: string;
  //   email?: string;
  //   lieu_naissance?: string;
  //   type_piece?: string;
  //   no_piece?: string;

  constructor(iDoctor: IDoctor) {
    this.id = iDoctor.id;
    this.matricule = iDoctor.matricule;
    this.nom = iDoctor.nom;
    this.prenoms = iDoctor.prenoms;
  }

  static fromResponse(doctor: DoctorResponse): Doctor {
    return new Doctor({
      id: doctor.id,
      matricule: doctor.matricule,
      nom: doctor.nom,
      prenoms: doctor.prenoms,
    });
  }

  get fullName() {
    return this.nom + " " + this.prenoms;
  }

  //   get age() {
  //     return calculateExactAge(new Date(this.date_naissance));
  //   }
}
