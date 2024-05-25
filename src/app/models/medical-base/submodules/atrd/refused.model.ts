export interface IRefused {
  id?: number;
  date_op: Date;
  motif: string;
  has_decharge?: boolean;
  has_ordonnance?: boolean;
  comments?: string;
  consultation_id: number | null;
}

export class Refused {
  id?: number;
  date_op: Date;
  motif: string;
  has_decharge?: boolean;
  has_ordonnance?: boolean;
  comments?: string;
  consultation_id: number | null;

  constructor(iRefused: IRefused) {
    this.id = iRefused.id;
    this.date_op = iRefused.date_op;
    this.comments = iRefused.comments;
    this.motif = iRefused.motif;
    this.has_decharge = iRefused.has_decharge;
    this.has_ordonnance = iRefused.has_ordonnance;
    this.consultation_id = iRefused.consultation_id;
  }
}
