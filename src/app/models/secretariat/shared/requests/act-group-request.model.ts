import { ActRequest } from "./act-request.model";

export interface IActGroupRequest {
  id: number;
  libelle: string;
  position: number;
  couleur: string;
  actes: ActRequest[];
}

export class ActGroupRequest {
  id: number;
  libelle: string;
  position: number;
  couleur: string;
  actes: ActRequest[];

  constructor(iActGroup: IActGroupRequest) {
    this.id = iActGroup.id;
    this.libelle = iActGroup.libelle;
    this.position = iActGroup.position;
    this.couleur = iActGroup.couleur;
    this.actes = iActGroup.actes;
  }
}
