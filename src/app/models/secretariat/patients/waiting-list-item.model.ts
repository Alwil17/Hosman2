import { calculateExactAge } from "src/app/helpers/age-calculator";
import { Patient } from "./patient.model";
import { Doctor } from "../shared/doctor.model";
import { Sector } from "../shared/sector.model";
import { Invoice } from "./invoice.model";

export interface IWaitingListItem {
  id: number;
  num_attente: number;
  ordre: number;
  attente: boolean;
  date_attente: Date;
  patient: Patient;
  medecin_consulteur?: Doctor;
  medecin?: string;
  medecin_receveur?: Doctor;
  receveur?: string;
  secteur?: Sector;
  secteur_code?: string;
  facture: Invoice;
}

export class WaitingListItem {
  id: number;
  num_attente: number;
  ordre: number;
  attente: boolean;
  date_attente: Date;
  patient: Patient;
  medecin_consulteur?: Doctor;
  medecin?: string;
  medecin_receveur?: Doctor;
  receveur?: string;
  secteur?: Sector;
  secteur_code?: string;
  facture: Invoice;

  constructor(iWaitingListItem: IWaitingListItem) {
    this.id = iWaitingListItem.id;
    this.num_attente = iWaitingListItem.num_attente;
    this.ordre = iWaitingListItem.ordre;
    this.attente = iWaitingListItem.attente;
    this.date_attente = iWaitingListItem.date_attente;
    this.patient = iWaitingListItem.patient;
    this.medecin_consulteur = iWaitingListItem.medecin_consulteur;
    this.medecin = iWaitingListItem.medecin;
    this.medecin_receveur = iWaitingListItem.medecin_receveur;
    this.receveur = iWaitingListItem.receveur;
    this.secteur = iWaitingListItem.secteur;
    this.secteur_code = iWaitingListItem.secteur_code;
    this.facture = iWaitingListItem.facture;
  }
}

// Models for consultation model
export interface IWaitingListItemShort {
  id: number;
  num_attente: number;
  ordre: number;
  attente: boolean;
  date_attente: Date;
}

export class WaitingListItemShort {
  id: number;
  num_attente: number;
  ordre: number;
  attente: boolean;
  date_attente: Date;

  constructor(iWaitingListItemShort: IWaitingListItemShort) {
    this.id = iWaitingListItemShort.id;
    this.num_attente = iWaitingListItemShort.num_attente;
    this.ordre = iWaitingListItemShort.ordre;
    this.attente = iWaitingListItemShort.attente;
    this.date_attente = iWaitingListItemShort.date_attente;
  }
}
