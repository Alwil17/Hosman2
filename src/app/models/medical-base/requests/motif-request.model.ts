export interface IMotifRequest {
  motif_id: number;
  caractere?: string;
}

export class MotifRequest {
  motif_id: number;
  caractere?: string;

  constructor(iMotifRequest: IMotifRequest) {
    this.motif_id = iMotifRequest.motif_id;
    this.caractere = iMotifRequest.caractere;
  }
}
