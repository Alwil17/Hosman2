export interface IFormRequest {
  presentation: string;
  dosage: string;
  conditionnement: string;
  prix: number;
  produit_id?: number;
}

export class FormRequest {
  presentation: string;
  dosage: string;
  conditionnement: string;
  prix: number;
  produit_id?: number;

  constructor(iFormRequest: IFormRequest) {
    this.presentation = iFormRequest.presentation;
    this.dosage = iFormRequest.dosage;
    this.conditionnement = iFormRequest.conditionnement;
    this.prix = iFormRequest.prix;
    this.produit_id = iFormRequest.produit_id;
  }
}
