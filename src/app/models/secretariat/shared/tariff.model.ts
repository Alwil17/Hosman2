import { Act } from "./act.model";
import { TariffResponse } from "./responses/tariff-response.model";

export interface ITariff {
  id: number;
  libelle: string;
  code: string;
  description: string;
  tarif_non_assure: number;
  tarif_etr_non_assure: number;
  tarif_assur_locale: number;
  tarif_assur_hors_zone: number;
  acte: Act;
}

export class Tariff {
  id: number;
  libelle: string;
  code: string;
  description: string;
  tarif_non_assure: number;
  tarif_etr_non_assure: number;
  tarif_assur_locale: number;
  tarif_assur_hors_zone: number;
  acte: Act;

  constructor(iTariff: ITariff) {
    this.id = iTariff.id;
    this.libelle = iTariff.libelle;
    this.code = iTariff.code;
    this.description = iTariff.description;
    this.tarif_non_assure = iTariff.tarif_non_assure;
    this.tarif_etr_non_assure = iTariff.tarif_etr_non_assure;
    this.tarif_assur_locale = iTariff.tarif_assur_locale;
    this.tarif_assur_hors_zone = iTariff.tarif_assur_hors_zone;
    this.acte = iTariff.acte;
  }

  static fromResponse(tariff: TariffResponse) {
    return new Tariff({
      id: tariff.id,
      libelle: tariff.libelle,
      code: tariff.code,
      description: tariff.description,
      tarif_non_assure: tariff.tarif_non_assure,
      tarif_etr_non_assure: tariff.tarif_etr_non_assure,
      tarif_assur_locale: tariff.tarif_assur_locale,
      tarif_assur_hors_zone: tariff.tarif_assur_hors_zone,
      acte: Act.fromResponse(tariff.acte),
    });
  }
}
