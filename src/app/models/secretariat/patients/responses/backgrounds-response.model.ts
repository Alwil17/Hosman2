export interface BackgroundsResponse {
  type: string;

  // adult patient
  type_diabete?: string;
  has_ugd?: boolean;
  has_hta?: boolean;
  has_asthme?: boolean;
  has_drepano?: boolean;
  type_drepano?: string;
  has_alcool?: boolean;
  nb_tabac?: number;
  mesure_tabac?: string;
  frequence_tabac?: string;
  nb_medic?: number;
  medicaments?: string[];
  nb_chirurgie?: number;
  chirurgies?: string[];
  autre?: string;

  // child patient
  voie_accouch?: string;
  voie_cause?: string;
  classe_scolarise?: string;
  reanime?: boolean;
  scolarise?: boolean;

  // common to adult and child patient
  nb_hospit?: number;
  hospitalisations?: string[];
  allergies?: string;
}
