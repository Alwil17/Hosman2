import { Agency } from "./agency.model";
import { ContraIndication } from "./contra-indication.model";
import { Delegate } from "./delegate.model";
import { Indication } from "./indication.model";
import { Laboratory } from "./laboratory.model";
import { SideEffect } from "./side-effect.model";
import { Posology } from "./posology.model";
import { TherapeuticClass } from "./therapeutic-class.model";
import { Form } from "./form.model";

export interface IProduct {
  id: number;
  nom: string;
  dci: string;
  infos?: string;
  autre?: string;
  agence?: Agency;
  laboratoire?: Laboratory;
  delegue?: Delegate;
  indications?: Indication[];
  contre_indications?: ContraIndication[];
  effet_secondaires?: SideEffect[];
  formes: Form[];
  posologies?: Posology[];
  classes: TherapeuticClass[];
}

export class Product {
  id: number;
  nom: string;
  dci: string;
  infos?: string;
  autre?: string;
  agence?: Agency;
  laboratoire?: Laboratory;
  delegue?: Delegate;
  indications?: Indication[];
  contre_indications?: ContraIndication[];
  effet_secondaires?: SideEffect[];
  formes: Form[];
  posologies?: Posology[];
  classes: TherapeuticClass[];

  constructor(iProduct: IProduct) {
    this.id = iProduct.id;
    this.nom = iProduct.nom;
    this.dci = iProduct.dci;
    this.infos = iProduct.infos;
    this.autre = iProduct.autre;
    this.agence = iProduct.agence;
    this.laboratoire = iProduct.laboratoire;
    this.delegue = iProduct.delegue;
    this.indications = iProduct.indications;
    this.contre_indications = iProduct.contre_indications;
    this.effet_secondaires = iProduct.effet_secondaires;
    this.formes = iProduct.formes;
    this.posologies = iProduct.posologies;
    this.classes = iProduct.classes;
  }
}
