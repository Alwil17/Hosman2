import { AddressResponse } from "./address-response.model";
import { CountryResponse } from "./country-response.model";
import { EmployerResponse } from "./employer-response.model";
import { InsuranceResponse } from "./insurance-response.model";
import { PatientInsuranceResponse } from "./patient-insurance-response.model";
import { PersonToContactResponse } from "./person-to-contact-response.model";
import { ProfessionResponse } from "./profession-response.model";

export interface PatientResponse {
  id: number;
  reference: string;
  nom: string;
  prenoms: string;
  date_naissance: Date;
  sexe: string;
  tel1: string;
  is_assure: number;
  date_ajout: Date;
  personne_a_prevenir: PersonToContactResponse;
  adresse: AddressResponse;
  pays_origine: CountryResponse;
  assurance?: InsuranceResponse;
  patient_assurance?: PatientInsuranceResponse;
  nationalite?: CountryResponse;
  lieu_naissance?: string;
  tel2?: string;
  type_piece?: string;
  no_piece?: string;
  profession?: ProfessionResponse;
  employeur?: EmployerResponse;
}
