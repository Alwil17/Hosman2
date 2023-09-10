import { StatusResponse } from "./responses/status-response.model";

export interface IStatus {
  id: number;
  nom: string;
  couleur?: string;
  indice: number;
}

export class Status {
  id: number;
  nom: string;
  couleur?: string;
  indice: number;

  constructor(iStatus: IStatus) {
    this.id = iStatus.id;
    this.nom = iStatus.nom;
    this.couleur = iStatus.couleur;
    this.indice = iStatus.indice;
  }

  static fromResponse(status: StatusResponse) {
    return new Status({
      id: status.id,
      nom: status.nom,
      couleur: status.couleur,
      indice: status.indice,
    });
  }
}
