import { ActGroup } from "./act-group.model";
import { ActResponse } from "./responses/act-response.model";

export interface IAct {
  id: number;
  libelle: string;
  code: string;
  groupe: ActGroup;
}

export class Act {
  id: number;
  libelle: string;
  code: string;
  groupe: ActGroup;

  constructor(iAct: IAct) {
    this.id = iAct.id;
    this.libelle = iAct.libelle;
    this.code = iAct.code;
    this.groupe = iAct.groupe;
  }

  static fromResponse(act: ActResponse) {
    return new Act({
      id: act.id,
      libelle: act.libelle,
      code: act.code,
      groupe: act.groupe,
    });
  }
}
