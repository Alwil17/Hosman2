import { AgencyRequest } from "./agency-request.model";
import { ContraIndicationRequest } from "./contra-indication-request.model";
import { DelegateRequest } from "./delegate-request.model";
import { FormRequest } from "./form-request.model";
import { IndicationRequest } from "./indication-request.model";
import { LaboratoryRequest } from "./laboratory-request.model";
import { PosologyRequest } from "./posology-request.model";
import { SideEffectRequest } from "./side-effect-request.model";

type ThClass = {
  classe_id: number;
};

export interface IProductRequest {
  nom: string;
  dci: string;
  infos?: string;
  autre?: string;
  agence?: AgencyRequest;
  laboratoire?: LaboratoryRequest;
  delegue?: DelegateRequest;
  indications?: IndicationRequest[];
  contre_indications?: ContraIndicationRequest[];
  effet_secondaires?: SideEffectRequest[];
  formes: FormRequest[];
  posologies?: PosologyRequest[];
  classes: ThClass[];
}

export class ProductRequest {
  nom: string;
  dci: string;
  infos?: string;
  autre?: string;
  agence?: AgencyRequest;
  laboratoire?: LaboratoryRequest;
  delegue?: DelegateRequest;
  indications?: IndicationRequest[];
  contre_indications?: ContraIndicationRequest[];
  effet_secondaires?: SideEffectRequest[];
  formes: FormRequest[];
  posologies?: PosologyRequest[];
  classes: ThClass[];

  constructor(iProductRequest: IProductRequest) {
    this.nom = iProductRequest.nom;
    this.dci = iProductRequest.dci;
    this.infos = iProductRequest.infos;
    this.autre = iProductRequest.autre;
    this.agence = iProductRequest.agence;
    this.laboratoire = iProductRequest.laboratoire;
    this.delegue = iProductRequest.delegue;
    this.indications = iProductRequest.indications;
    this.contre_indications = iProductRequest.contre_indications;
    this.effet_secondaires = iProductRequest.effet_secondaires;
    this.formes = iProductRequest.formes;
    this.posologies = iProductRequest.posologies;
    this.classes = iProductRequest.classes;
  }
}
