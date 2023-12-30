export interface IConstanteRequest {
  poids?: number;
  taille?: number;
  tension?: string;
  temperature?: number;
  poul?: number;
  perimetre_cranien?: number;
  frequence_respiratoire?: number;
}

export class ConstanteRequest {
  poids?: number;
  taille?: number;
  tension?: string;
  temperature?: number;
  poul?: number;
  perimetre_cranien?: number;
  frequence_respiratoire?: number;

  constructor(iConstanteRequest: IConstanteRequest) {
    this.poids = iConstanteRequest.poids;
    this.taille = iConstanteRequest.taille;
    this.tension = iConstanteRequest.tension;
    this.temperature = iConstanteRequest.temperature;
    this.poul = iConstanteRequest.poul;
    this.perimetre_cranien = iConstanteRequest.perimetre_cranien;
    this.frequence_respiratoire = iConstanteRequest.frequence_respiratoire;
  }
}
