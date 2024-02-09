import { Laboratory } from "./laboratory.model";

export interface IDelegate {
  id: number;
  nom: string;
  prenoms: string;
  tel1: string;
  tel2: string;
  email: string;
  adresse: string;
  laboratoire: Laboratory;
}

export class Delegate {
  id: number;
  nom: string;
  prenoms: string;
  tel1: string;
  tel2: string;
  email: string;
  adresse: string;
  laboratoire: Laboratory;

  constructor(iDelegate: IDelegate) {
    this.id = iDelegate.id;
    this.nom = iDelegate.nom;
    this.prenoms = iDelegate.prenoms;
    this.tel1 = iDelegate.tel1;
    this.tel2 = iDelegate.tel2;
    this.email = iDelegate.email;
    this.adresse = iDelegate.adresse;
    this.laboratoire = iDelegate.laboratoire;
  }
}
