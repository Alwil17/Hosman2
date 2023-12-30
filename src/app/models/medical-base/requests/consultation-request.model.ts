import { ActRequest } from "./act-request.model";
import { ConstanteRequest } from "./constante-request.model";
import { DiagnosticRequest } from "./diagnostic-request.model";
import { MotifRequest } from "./motif-request.model";

export interface IConsultationRequest {
  // reference: string;
  patient_ref: string;
  secteur_code: string;
  attente_num: number;
  date_consultation: Date;
  //   type: string;
  //   commentaire: string;
  constante?: ConstanteRequest;
  actes: ActRequest[];
  motifs: MotifRequest[];
  diagnostics?: DiagnosticRequest[];
  hdm?: string;
}

export class ConsultationRequest {
  //   reference: string;
  patient_ref: string;
  secteur_code: string;
  attente_num: number;
  date_consultation: Date;
  //   type: string;
  //   commentaire: string;
  constante?: ConstanteRequest;
  actes: ActRequest[];
  motifs: MotifRequest[];
  diagnostics?: DiagnosticRequest[];
  hdm?: string;

  constructor(iConsultationRequest: IConsultationRequest) {
    this.patient_ref = iConsultationRequest.patient_ref;
    this.secteur_code = iConsultationRequest.secteur_code;
    this.attente_num = iConsultationRequest.attente_num;
    this.date_consultation = iConsultationRequest.date_consultation;
    this.hdm = iConsultationRequest.hdm;
    this.constante = iConsultationRequest.constante;
    this.actes = iConsultationRequest.actes;
    this.motifs = iConsultationRequest.motifs;
    this.diagnostics = iConsultationRequest.diagnostics;
  }
}
