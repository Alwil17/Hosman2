export interface ITariffRequest {
  tarif_non_assure: number;
  tarif_etr_non_assure: number;
  tarif_assur_locale: number;
  tarif_assur_hors_zone: number;
}

export class TariffRequest {
  tarif_non_assure: number;
  tarif_etr_non_assure: number;
  tarif_assur_locale: number;
  tarif_assur_hors_zone: number;

  constructor(iTariff: ITariffRequest) {
    this.tarif_non_assure = iTariff.tarif_non_assure;
    this.tarif_etr_non_assure = iTariff.tarif_etr_non_assure;
    this.tarif_assur_locale = iTariff.tarif_assur_locale;
    this.tarif_assur_hors_zone = iTariff.tarif_assur_hors_zone;
  }
}
