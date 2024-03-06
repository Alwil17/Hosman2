import { BackgroundsResponse } from "./responses/backgrounds-response.model";

export interface IBackgrounds {
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
  is_reanime?: boolean;
  is_scolarise?: boolean;

  // common to adult and child patient
  nb_hospit?: number;
  hospits?: string[];
  allergies?: string;
}

export class Backgrounds {
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
  is_reanime?: boolean;
  is_scolarise?: boolean;

  // common to adult and child patient
  nb_hospit?: number;
  hospits?: string[];
  allergies?: string;

  constructor(iBackgrounds: IBackgrounds) {
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
    this.is_reanime = iBackgrounds.is_reanime;
    this.is_scolarise = iBackgrounds.is_scolarise;

    this.nb_hospit = iBackgrounds.nb_hospit;
    this.hospits = iBackgrounds.hospits;
    this.allergies = iBackgrounds.allergies;
  }

  static fromResponse(backgrounds: BackgroundsResponse): Backgrounds {
    return new Backgrounds({
      type: backgrounds.type,

      type_diabete: backgrounds.type_diabete,
      has_ugd: backgrounds.has_ugd,
      has_hta: backgrounds.has_hta,
      has_asthme: backgrounds.has_asthme,
      has_drepano: backgrounds.has_drepano,
      type_drepano: backgrounds.type_drepano,
      has_alcool: backgrounds.has_alcool,
      nb_tabac: backgrounds.nb_tabac,
      mesure_tabac: backgrounds.mesure_tabac,
      frequence_tabac: backgrounds.frequence_tabac,
      nb_medic: backgrounds.nb_medic,
      medics: backgrounds.medics,
      nb_chirurgie: backgrounds.nb_chirurgie,
      chirurgies: backgrounds.chirurgies,
      autre: backgrounds.autre,

      voie_accouch: backgrounds.voie_accouch,
      voie_cause: backgrounds.voie_cause,
      classe_scolarise: backgrounds.classe_scolarise,
      is_reanime: backgrounds.is_reanime,
      is_scolarise: backgrounds.is_scolarise,

      nb_hospit: backgrounds.nb_hospit,
      hospits: backgrounds.hospits,
      allergies: backgrounds.allergies,
    });
  }
}
