import { IInsuranceType } from "./insurance-type.model";

export interface IInsurance {
  id: number;
  reference: string;
  nom: string;
  email?: string;
  tel1?: string;
  tel2?: string;
  representant?: string;
  type_assurance: IInsuranceType;
  // taux ?
}
