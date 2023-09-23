import { StatusResponse } from "./responses/status-response.model";

export interface IStatus {
  id: number;
  slug: string;
  nom: string;
  couleur: string;
  indice: number;
}

export class Status {
  id: number;
  nom: string;
  slug: string;
  couleur: string;
  indice: number;

  constructor(iStatus: IStatus) {
    this.id = iStatus.id;
    this.nom = iStatus.nom;
    this.slug = iStatus.slug;
    this.couleur = iStatus.couleur;
    this.indice = iStatus.indice;
  }

  static fromResponse(status: StatusResponse) {
    return new Status({
      id: status.id,
      nom: status.nom,
      slug: status.slug,
      couleur: status.couleur,
      indice: status.indice,
    });
  }
}
