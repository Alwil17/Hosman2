export interface IForm {
  id: number;
  presentation: string;
  dosage: string;
  conditionnement: string;
  prix: number;
}

export class Form {
  id: number;
  presentation: string;
  dosage: string;
  conditionnement: string;
  prix: number;

  constructor(iForm: IForm) {
    this.id = iForm.id;
    this.presentation = iForm.presentation;
    this.dosage = iForm.dosage;
    this.conditionnement = iForm.conditionnement;
    this.prix = iForm.prix;
  }
}
