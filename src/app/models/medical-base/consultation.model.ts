import { Patient } from "../secretariat/patients/patient.model";
import { WaitingListItemShort } from "../secretariat/patients/waiting-list-item.model";
import { Sector } from "../secretariat/shared/sector.model";
import { Tariff } from "../secretariat/shared/tariff.model";
import { Constante } from "./constante.model";
import { Diagnostic } from "./diagnostic.model";
import { Motif } from "./motif.model";

export interface IConsultation {
  id: number;
  reference: string;
  date_consultation: Date;
  //   type: string,
  //   commentaire: string,
  patient_ref: string;
  patient: Patient;
  secteur_code: string;
  secteur: Sector;
  attente_num?: number;
  attente?: WaitingListItemShort;
  constante?: Constante;
  actes: Tariff[];
  motifs: Motif[];
  diagnostics?: Diagnostic[];
  hdm?: string;
}

export class Consultation {
  id: number;
  reference: string;
  date_consultation: Date;
  //   type: string,
  //   commentaire: string,
  patient_ref: string;
  patient: Patient;
  secteur_code: string;
  secteur: Sector;
  attente_num?: number;
  attente?: WaitingListItemShort;
  constante?: Constante;
  actes: Tariff[];
  motifs: Motif[];
  diagnostics?: Diagnostic[];
  hdm?: string;

  constructor(iConsultation: IConsultation) {
    this.id = iConsultation.id;
    this.reference = iConsultation.reference;
    this.date_consultation = iConsultation.date_consultation;
    this.hdm = iConsultation.hdm;
    this.patient_ref = iConsultation.patient_ref;
    this.patient = iConsultation.patient;
    this.secteur_code = iConsultation.secteur_code;
    this.secteur = iConsultation.secteur;
    this.attente_num = iConsultation.attente_num;
    this.attente = iConsultation.attente;
    this.constante = iConsultation.constante;
    this.actes = iConsultation.actes;
    this.motifs = iConsultation.motifs;
    this.diagnostics = iConsultation.diagnostics;
  }
}
