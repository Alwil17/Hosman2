export interface IBackgroundsRequest {
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
  medics?: string[];
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
  hospits?: string[];
  allergies?: string;
}

export class BackgroundsRequest {
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
  medics?: string[];
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
  hospits?: string[];
  allergies?: string;

  constructor(iBackgrounds: IBackgroundsRequest) {
    this.type = iBackgrounds.type;

    this.type_diabete = iBackgrounds.type_diabete;
    this.has_ugd = iBackgrounds.has_ugd;
    this.has_hta = iBackgrounds.has_hta;
    this.has_asthme = iBackgrounds.has_asthme;
    this.has_drepano = iBackgrounds.has_drepano;
    this.type_drepano = iBackgrounds.type_drepano;
    this.has_alcool = iBackgrounds.has_alcool;
    this.nb_tabac = iBackgrounds.nb_tabac;
    this.mesure_tabac = iBackgrounds.mesure_tabac;
    this.frequence_tabac = iBackgrounds.frequence_tabac;
    this.nb_medic = iBackgrounds.nb_medic;
    this.medics = iBackgrounds.medics;
    this.nb_chirurgie = iBackgrounds.nb_chirurgie;
    this.chirurgies = iBackgrounds.chirurgies;
    this.autre = iBackgrounds.autre;

    this.voie_accouch = iBackgrounds.voie_accouch;
    this.voie_cause = iBackgrounds.voie_cause;
    this.classe_scolarise = iBackgrounds.classe_scolarise;
    this.reanime = iBackgrounds.reanime;
    this.scolarise = iBackgrounds.scolarise;

    this.nb_hospit = iBackgrounds.nb_hospit;
    this.hospits = iBackgrounds.hospits;
    this.allergies = iBackgrounds.allergies;
  }
}
