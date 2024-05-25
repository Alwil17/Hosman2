export interface IAddressed {
  id?: number;
  date_op: Date;
  specialite: string;
  med_ref: string;
  comments?: string;
  institution: string;
  motif: string;
  medical_letter?: boolean;
  medical_report?: boolean;
  transport: string;
  consultation_id: number | null;
}

export class Addressed {
  id?: number;
  date_op: Date;
  specialite: string;
  med_ref: string;
  comments?: string;
  institution: string;
  motif: string;
  medical_letter?: boolean;
  medical_report?: boolean;
  transport: string;
  consultation_id: number | null;

  constructor(iAddressed: IAddressed) {
    this.id = iAddressed.id;
    this.date_op = iAddressed.date_op;
    this.specialite = iAddressed.specialite;
    this.med_ref = iAddressed.med_ref;
    this.comments = iAddressed.comments;
    this.institution = iAddressed.institution;
    this.motif = iAddressed.motif;
    this.medical_letter = iAddressed.medical_letter;
    this.medical_report = iAddressed.medical_report;
    this.transport = iAddressed.transport;
    this.consultation_id = iAddressed.consultation_id;
  }
}
