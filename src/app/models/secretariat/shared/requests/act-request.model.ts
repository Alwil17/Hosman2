export interface IActRequest {
  libelle: string;
  code: string;
  // groupe_id: number;
}

export class ActRequest {
  libelle: string;
  code: string;
  // groupe_id: number;

  constructor(iAct: IActRequest) {
    this.libelle = iAct.libelle;
    this.code = iAct.code;
    // this.groupe_id = iAct.groupe_id;
  }
}
