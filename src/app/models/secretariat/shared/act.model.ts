import { ActResponse } from "./responses/act-response.model";

export interface IAct {
  id: number;
  libelle: string;
  code: string;
}

export class Act {
  id: number;
  libelle: string;
  code: string;

  constructor(iAct: IAct) {
    this.id = iAct.id;
    this.libelle = iAct.libelle;
    this.code = iAct.code;
  }

  static fromResponse(act: ActResponse) {
    return new Act({
      id: act.id,
      libelle: act.libelle,
      code: act.code,
    });
  }
}
