export interface IActRequest {
  acte: string;
}

export class ActRequest {
  acte: string;

  constructor(iActRequest: IActRequest) {
    this.acte = iActRequest.acte;
  }
}
