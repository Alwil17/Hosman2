import { HasInsurance } from "../has-insurance.model";
import { AddressResponse } from "./address-response.model";
import { CountryResponse } from "./country-response.model";
import { EmployerResponse } from "./employer-response.model";
import { ProfessionResponse } from "./profession-response.model";

export interface PatientResponse {
  id: number;
  reference: string;
  nom: string;
  prenoms: string;
  date_naissance: Date;
  sexe: string;
  tel1: string;
  is_assure: boolean;
  personne_a_prevenir: string;
  adresse: AddressResponse;
  date_entre: Date;
  pays_origine: CountryResponse;
  type_patient: HasInsurance;
  lieu_naissance?: string;
  tel2?: string;
  type_carte?: string;
  no_carte?: string;
  profession?: ProfessionResponse;
  // assurance?: IInsurance;
  employeur?: EmployerResponse;
}
