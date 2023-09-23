export interface IPaymentMode {
  id: number;
  nom: string;
  slug: string;
}

export class PaymentMode {
  id: number;
  nom: string;
  slug: string;

  constructor(iPaymentMode: IPaymentMode) {
    this.id = iPaymentMode.id;
    this.nom = iPaymentMode.nom;
    this.slug = iPaymentMode.slug;
  }
}
