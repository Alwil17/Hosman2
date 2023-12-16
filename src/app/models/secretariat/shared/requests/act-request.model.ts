import { ActGroupRequest } from "./act-group-request.model";

export interface IActRequest {
  libelle: string;
  code: string;
  groupe: ActGroupRequest;
}

export class ActRequest {
  libelle: string;
  code: string;
  groupe: ActGroupRequest;

  constructor(iAct: IActRequest) {
    this.libelle = iAct.libelle;
    this.code = iAct.code;
    this.groupe = iAct.groupe;
  }
}
