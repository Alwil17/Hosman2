import { AddressRequest } from "./address-request.model";
import { CountryRequest } from "./country-request.model";
import { EmployerRequest } from "./employer-request.model";
import { InsuranceRequest } from "./insurance-request.model";
import { PatientInsuranceRequest } from "./patient-insurance-request.model";
import { PersonToContactRequest } from "./person-to-contact-request.model";
import { ProfessionRequest } from "./profession-request.model";

export interface IPatientRequest {
  nom: string;
  prenoms: string;
  date_naissance: Date;
  sexe: string;
  tel1: string;
  is_assure: number;
  date_ajout: Date;
  personne_a_prevenir: PersonToContactRequest;
  adresse: AddressRequest;
  // pays_origine: CountryRequest;
  pays_origine_id: number;
  assurance?: InsuranceRequest;
  taux_assurance?: number;
  date_debut_assurance?: Date;
  date_fin_assurance?: Date;
  // patient_assurance?: PatientInsuranceRequest;
  // nationalite?: CountryRequest;
  nationalite_id?: number;
  lieu_naissance?: string;
  tel2?: string;
  type_piece?: string;
  no_piece?: string;
  // profession?: ProfessionRequest;
  // employeur?: EmployerRequest;
  profession?: ProfessionRequest;
  employeur?: EmployerRequest;
}
export class PatientRequest {
  nom: string;
  prenoms: string;
  date_naissance: Date;
  sexe: string;
  tel1: string;
  is_assure: number;
  date_ajout: Date;
  personne_a_prevenir: PersonToContactRequest;
  adresse: AddressRequest;
  // pays_origine: CountryRequest;
  pays_origine_id: number;
  assurance?: InsuranceRequest;
  taux_assurance?: number;
  date_debut_assurance?: Date;
  date_fin_assurance?: Date;
  // patient_assurance?: PatientInsuranceRequest;
  // nationalite?: CountryRequest;
  nationalite_id?: number;
  lieu_naissance?: string;
  tel2?: string;
  type_piece?: string;
  no_piece?: string;
  // profession?: ProfessionRequest;
  // employeur?: EmployerRequest;
  profession?: ProfessionRequest;
  employeur?: EmployerRequest;

  constructor(iPatientRequest: IPatientRequest) {
    this.nom = iPatientRequest.nom;
    this.prenoms = iPatientRequest.prenoms;
    this.date_naissance = iPatientRequest.date_naissance;
    this.sexe = iPatientRequest.sexe;
    this.tel1 = iPatientRequest.tel1;
    this.is_assure = iPatientRequest.is_assure;
    this.date_ajout = iPatientRequest.date_ajout;
    this.personne_a_prevenir = iPatientRequest.personne_a_prevenir;
    this.adresse = iPatientRequest.adresse;
    this.pays_origine_id = iPatientRequest.pays_origine_id;
    this.assurance = iPatientRequest.assurance;
    this.taux_assurance = iPatientRequest.taux_assurance;
    this.date_debut_assurance = iPatientRequest.date_debut_assurance;
    this.date_fin_assurance = iPatientRequest.date_fin_assurance;
    // this.patient_assurance = iPatientRequest.patient_assurance;
    this.nationalite_id = iPatientRequest.nationalite_id;
    this.lieu_naissance = iPatientRequest.lieu_naissance;
    this.tel2 = iPatientRequest.tel2;
    this.type_piece = iPatientRequest.type_piece;
    this.no_piece = iPatientRequest.no_piece;
    this.profession = iPatientRequest.profession;
    this.employeur = iPatientRequest.employeur;
  }
}
