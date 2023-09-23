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
  medecin: Doctor;
  receveur?: Doctor;
  secteur: Sector;
  facture: Invoice;
}

export class WaitingListItem {
  id: number;
  num_attente: number;
  ordre: number;
  attente: boolean;
  date_attente: Date;
  patient: Patient;
  medecin: Doctor;
  receveur?: Doctor;
  secteur: Sector;
  facture: Invoice;

  constructor(iWaitingListItem: IWaitingListItem) {
    this.id = iWaitingListItem.id;
    this.num_attente = iWaitingListItem.num_attente;
    this.ordre = iWaitingListItem.ordre;
    this.attente = iWaitingListItem.attente;
    this.date_attente = iWaitingListItem.date_attente;
    this.patient = iWaitingListItem.patient;
    this.medecin = iWaitingListItem.medecin;
    this.receveur = iWaitingListItem.receveur;
    this.secteur = iWaitingListItem.secteur;
    this.facture = iWaitingListItem.facture;
  }
}
