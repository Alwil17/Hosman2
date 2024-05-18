export interface ActsProForma {
  libelle: string;
  code: string;
  tarif_non_assure: number;
  tarif_etr_non_assure: number;
  tarif_assur_locale: number;
  tarif_assur_hors_zone: number;
  qte: number;
}

export interface ITariffProFormaRequest {
  patient: string;
  actes: ActsProForma[];
  is_assure: number;
}

export class TariffProFormaRequest {
  patient: string;
  actes: ActsProForma[];
  is_assure: number;

  constructor(iTariffProForma: ITariffProFormaRequest) {
    this.patient = iTariffProForma.patient;
    this.actes = iTariffProForma.actes;
    this.is_assure = iTariffProForma.is_assure;
  }
}
