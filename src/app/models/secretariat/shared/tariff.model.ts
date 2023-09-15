import { TariffResponse } from "./responses/tariff-response.model";

export interface ITariff {
  id: number;
  tarif_non_assure: number;
  tarif_etr_non_assure: number;
  tarif_assur_locale: number;
  tarif_assur_hors_zone: number;
}

export class Tariff {
  id: number;
  tarif_non_assure: number;
  tarif_etr_non_assure: number;
  tarif_assur_locale: number;
  tarif_assur_hors_zone: number;

  constructor(iTariff: ITariff) {
    this.id = iTariff.id;
    this.tarif_non_assure = iTariff.tarif_non_assure;
    this.tarif_etr_non_assure = iTariff.tarif_etr_non_assure;
    this.tarif_assur_locale = iTariff.tarif_assur_locale;
    this.tarif_assur_hors_zone = iTariff.tarif_assur_hors_zone;
  }

  static fromResponse(tariff: TariffResponse) {
    return new Tariff({
      id: tariff.id,
      tarif_non_assure: tariff.tarif_non_assure,
      tarif_etr_non_assure: tariff.tarif_etr_non_assure,
      tarif_assur_locale: tariff.tarif_assur_locale,
      tarif_assur_hors_zone: tariff.tarif_assur_hors_zone,
    });
  }
}
