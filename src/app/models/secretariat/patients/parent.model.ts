import { Employer } from "./employer.model";
import { Insurance } from "./insurance.model";
import { Profession } from "./profession.model";
import { ParentResponse } from "./responses/parent-response.model";

export interface IParent {
  id: number;
  profession: Profession;
  employeur: Employer;
  assurance: Insurance;
  telephone?: string;
  sexe?: string;
  type?: string;
  annee_naissance?: number;
}
export class Parent {
  id: number;
  profession: Profession;
  employeur: Employer;
  assurance: Insurance;
  telephone?: string;
  sexe?: string;
  type?: string;
  annee_naissance?: number;

  constructor(iParent: IParent) {
    this.id = iParent.id;
    this.profession = iParent.profession;
    this.employeur = iParent.employeur;
    this.assurance = iParent.assurance;
    this.telephone = iParent.telephone;
    this.sexe = iParent.sexe;
    this.type = iParent.type;
    this.annee_naissance = iParent.annee_naissance;
  }

  static fromResponse(chronicDisease: ParentResponse): Parent {
    return new Parent({
      id: chronicDisease.id,
      profession: chronicDisease.profession,
      employeur: chronicDisease.employeur,
      assurance: chronicDisease.assurance,
      telephone: chronicDisease.telephone,
      sexe: chronicDisease.sexe,
      type: chronicDisease.type,
      annee_naissance: chronicDisease.annee_naissance,
    });
  }
}
