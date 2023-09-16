import { ActRequest } from "./act-request.model";

export interface ITariffRequest {
  libelle: string;
  code: string;
  description: string;
  tarif_non_assure: number;
  tarif_etr_non_assure: number;
  tarif_assur_locale: number;
  tarif_assur_hors_zone: number;
  acte: ActRequest;
}

export class TariffRequest {
  libelle: string;
  code: string;
  description: string;
  tarif_non_assure: number;
  tarif_etr_non_assure: number;
  tarif_assur_locale: number;
  tarif_assur_hors_zone: number;
  acte: ActRequest;

  constructor(iTariff: ITariffRequest) {
    this.libelle = iTariff.libelle;
    this.code = iTariff.code;
    this.description = iTariff.description;
    this.tarif_non_assure = iTariff.tarif_non_assure;
    this.tarif_etr_non_assure = iTariff.tarif_etr_non_assure;
    this.tarif_assur_locale = iTariff.tarif_assur_locale;
    this.tarif_assur_hors_zone = iTariff.tarif_assur_hors_zone;
    this.acte = iTariff.acte;
  }
}
