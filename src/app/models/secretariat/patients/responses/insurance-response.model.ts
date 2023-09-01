import { InsuranceTypeResponse } from "./insurance-type-response.model";

export interface InsuranceResponse {
  id: number;
  nom: string;
  email?: string;
  tel1?: string;
  tel2?: string;
  representant?: string;
  type_assurance: InsuranceTypeResponse;
}
