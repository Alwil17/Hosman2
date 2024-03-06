import { AddressResponse } from "./address-response.model";
import { BackgroundsResponse } from "./backgrounds-response.model";
import { ChronicDiseaseResponse } from "./chronic-disease-response.model";
import { CountryResponse } from "./country-response.model";
import { EmployerResponse } from "./employer-response.model";
import { InsuranceResponse } from "./insurance-response.model";
import { ParentResponse } from "./parent-response.model";
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
  taux_assurance?: number;
  date_debut_assurance?: Date;
  date_fin_assurance?: Date;
  // patient_assurance?: PatientInsuranceResponse;
  nationalite?: CountryResponse;
  lieu_naissance?: string;
  tel2?: string;
  type_piece?: string;
  no_piece?: string;
  profession?: ProfessionResponse;
  employeur?: EmployerResponse;

  // Visit/Medical base fileds
  maladies?: ChronicDiseaseResponse[];
  parents?: ParentResponse[];
  commentaire?: string;

  antecedant?: BackgroundsResponse;
}
