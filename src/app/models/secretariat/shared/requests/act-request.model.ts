export interface IActRequest {
  libelle: string;
  code: string;
}

export class ActRequest {
  libelle: string;
  code: string;

  constructor(iAct: IActRequest) {
    this.libelle = iAct.libelle;
    this.code = iAct.code;
  }
}
