export interface IForm {
  presentation: string;
  dosage: string;
  conditionnement: string;
  prix: number;
}

export class Form {
  presentation: string;
  dosage: string;
  conditionnement: string;
  prix: number;

  constructor(iForm: IForm) {
    this.presentation = iForm.presentation;
    this.dosage = iForm.dosage;
    this.conditionnement = iForm.conditionnement;
    this.prix = iForm.prix;
  }
}
