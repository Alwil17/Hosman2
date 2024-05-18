export interface IChronicDiseaseRequest {
  maladie: string;
}
export class ChronicDiseaseRequest {
  maladie: string;

  constructor(iChronicDisease: IChronicDiseaseRequest) {
    this.maladie = iChronicDisease.maladie;
  }
}
