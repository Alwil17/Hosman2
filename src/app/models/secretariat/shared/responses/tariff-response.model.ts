import { ActResponse } from "./act-response.model";

export interface TariffResponse {
  id: number;
  libelle: string;
  code: string;
  description: string;
  tarif_non_assure: number;
  tarif_etr_non_assure: number;
  tarif_assur_locale: number;
  tarif_assur_hors_zone: number;
  acte: ActResponse;
}
