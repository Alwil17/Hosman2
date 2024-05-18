export interface IStatusRequest {
  nom: string;
  couleur?: string;
  indice: number;
}

export class StatusRequest {
  nom: string;
  couleur?: string;
  indice: number;

  constructor(iStatus: IStatusRequest) {
    this.nom = iStatus.nom;
    this.couleur = iStatus.couleur;
    this.indice = iStatus.indice;
  }
}
