export interface IConstante {
  id: number;
  poids?: number;
  taille?: number;
  tension?: string;
  temperature?: number;
  poul?: number;
  perimetre_cranien?: number;
  frequence_respiratoire?: number;
}

export class Constante {
  id: number;
  poids?: number;
  taille?: number;
  tension?: string;
  temperature?: number;
  poul?: number;
  perimetre_cranien?: number;
  frequence_respiratoire?: number;

  constructor(iConstante: IConstante) {
    this.id = iConstante.id;
    this.poids = iConstante.poids;
    this.taille = iConstante.taille;
    this.tension = iConstante.tension;
    this.temperature = iConstante.temperature;
    this.poul = iConstante.poul;
    this.perimetre_cranien = iConstante.perimetre_cranien;
    this.frequence_respiratoire = iConstante.frequence_respiratoire;
  }
}
