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
  taux?: number;
  date_debut?: Date;
  date_fin?: Date;
  // patient_assurance?: PatientInsuranceRequest;
  // nationalite?: CountryRequest;
  nationalite_id?: number;
  lieu_naissance?: string;
  tel2?: string;
  type_piece?: string;
  no_piece?: string;
  // profession?: ProfessionRequest;
  // employeur?: EmployerRequest;
  profession_id?: number;
  employeur_id?: number;
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
  taux?: number;
  date_debut?: Date;
  date_fin?: Date;
  // patient_assurance?: PatientInsuranceRequest;
  // nationalite?: CountryRequest;
  nationalite_id?: number;
  lieu_naissance?: string;
  tel2?: string;
  type_piece?: string;
  no_piece?: string;
  // profession?: ProfessionRequest;
  // employeur?: EmployerRequest;
  profession_id?: number;
  employeur_id?: number;

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
    this.taux = iPatientRequest.taux;
    this.date_debut = iPatientRequest.date_debut;
    this.date_fin = iPatientRequest.date_fin;
    // this.patient_assurance = iPatientRequest.patient_assurance;
    this.nationalite_id = iPatientRequest.nationalite_id;
    this.lieu_naissance = iPatientRequest.lieu_naissance;
    this.tel2 = iPatientRequest.tel2;
    this.type_piece = iPatientRequest.type_piece;
    this.no_piece = iPatientRequest.no_piece;
    this.profession_id = iPatientRequest.profession_id;
    this.employeur_id = iPatientRequest.employeur_id;
  }

  // constructor(
  //   reference: string,
  //   nom: string,
  //   prenoms: string,
  //   date_naissance: Date,
  //   sexe: string,
  //   tel1: string,
  //   is_assure: number,
  //   date_ajout: Date,
  //   personne_a_prevenir: PersonToContactRequest,
  //   adresse: AddressRequest,
  //   pays_origine: CountryRequest,
  //   assurance: InsuranceRequest,
  //   patient_assurance: PatientInsuranceRequest,
  //   lieu_naissance?: string,
  //   tel2?: string,
  //   type_piece?: string,
  //   no_piece?: string,
  //   profession?: ProfessionRequest,
  //   employeur?: EmployerRequest
  // ) {
  //   this.reference = reference;
  //   this.nom = nom;
  //   this.prenoms = prenoms;
  //   this.date_naissance = date_naissance;
  //   this.sexe = sexe;
  //   this.tel1 = tel1;
  //   this.is_assure = is_assure;
  //   this.date_ajout = date_ajout;
  //   this.personne_a_prevenir = personne_a_prevenir;
  //   this.adresse = adresse;
  //   this.pays_origine = pays_origine;
  //   this.assurance = assurance;
  //   this.patient_assurance = patient_assurance;
  //   this.lieu_naissance = lieu_naissance;
  //   this.tel2 = tel2;
  //   this.type_piece = type_piece;
  //   this.no_piece = no_piece;
  //   this.profession = profession;
  //   this.employeur = employeur;
  // }
}
