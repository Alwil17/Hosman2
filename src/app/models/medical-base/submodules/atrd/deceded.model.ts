export interface IDeceded {
  id?: number;
  date_op: Date;
  motif: string;
  after_out?: boolean;
  reanimation?: boolean;
  comments?: string;
  consultation_id: number | null;
}

export class Deceded {
  id?: number;
  date_op: Date;
  motif: string;
  after_out?: boolean;
  reanimation?: boolean;
  comments?: string;
  consultation_id: number | null;

  constructor(iDeceded: IDeceded) {
    this.id = iDeceded.id;
    this.date_op = iDeceded.date_op;
    this.comments = iDeceded.comments;
    this.motif = iDeceded.motif;
    this.after_out = iDeceded.after_out;
    this.reanimation = iDeceded.reanimation;
    this.consultation_id = iDeceded.consultation_id;
  }
}
