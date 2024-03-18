export interface IParentRequest {
  profession: string;
  employeur: string;
  assurance: number;
  telephone?: string;
  sexe?: string;
  type?: string;
  annee_naissance?: number;
}
export class ParentRequest {
  profession: string;
  employeur: string;
  assurance: number;
  telephone?: string;
  sexe?: string;
  type?: string;
  annee_naissance?: number;

  constructor(iParent: IParentRequest) {
    this.profession = iParent.profession;
    this.employeur = iParent.employeur;
    this.assurance = iParent.assurance;
    this.telephone = iParent.telephone;
    this.sexe = iParent.sexe;
    this.type = iParent.type;
    this.annee_naissance = iParent.annee_naissance;
  }
}
