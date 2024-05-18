export interface ITransfered {
  id?: number;
  date_op: Date;
  specialite: string;
  destination: string;
  comments?: string;
  accompagne?: string;
  motif: string;
  transport: string;
  consultation_id: number | null;
}

export class Transfered {
  id?: number;
  date_op: Date;
  specialite: string;
  destination: string;
  comments?: string;
  accompagne?: string;
  motif: string;
  transport: string;
  consultation_id: number | null;

  constructor(iTransfered: ITransfered) {
    this.id = iTransfered.id;
    this.date_op = iTransfered.date_op;
    this.specialite = iTransfered.specialite;
    this.destination = iTransfered.destination;
    this.comments = iTransfered.comments;
    this.accompagne = iTransfered.accompagne;
    this.motif = iTransfered.motif;
    this.transport = iTransfered.transport;
    this.consultation_id = iTransfered.consultation_id;
  }
}
