import { ChronicDiseaseResponse } from "./responses/chronic-disease-response.model";

export interface IChronicDisease {
  id: number;
  nom: string;
}
export class ChronicDisease {
  id: number;
  nom: string;

  constructor(iChronicDisease: IChronicDisease) {
    this.id = iChronicDisease.id;
    this.nom = iChronicDisease.nom;
  }

  static fromResponse(chronicDisease: ChronicDiseaseResponse): ChronicDisease {
    return new ChronicDisease({
      id: chronicDisease.id,
      nom: chronicDisease.nom,
    });
  }
}
