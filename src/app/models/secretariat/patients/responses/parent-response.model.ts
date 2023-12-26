import { EmployerResponse } from "./employer-response.model";
import { InsuranceResponse } from "./insurance-response.model";
import { ProfessionResponse } from "./profession-response.model";

export interface ParentResponse {
  id: number;
  profession: ProfessionResponse;
  employeur: EmployerResponse;
  assurance: InsuranceResponse;
  telephone?: string;
  sexe?: string;
  type?: string;
  annee_naissance?: number;
}
