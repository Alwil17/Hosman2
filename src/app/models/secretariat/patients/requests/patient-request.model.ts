import { HasInsurance } from "../has-insurance.model";
import { AddressRequest } from "./address-request.model";
import { CountryRequest } from "./country-request.model";
import { EmployerRequest } from "./employer-request.model";
import { ProfessionRequest } from "./profession-request.model";

export class PatientRequest {
  nom: string;
  prenoms: string;
  date_naissance: Date;
  sexe: string;
  tel1: string;
  is_assure: boolean;
  personne_a_prevenir: string;
  adresse: AddressRequest;
  date_entre: Date;
  pays_origine: CountryRequest;
  type_patient: HasInsurance;
  lieu_naissance?: string;
  tel2?: string;
  type_carte?: string;
  no_carte?: string;
  profession?: ProfessionRequest;
  employeur?: EmployerRequest;

  constructor(
    nom: string,
    prenoms: string,
    date_naissance: Date,
    sexe: string,
    tel1: string,
    is_assure: boolean,
    personne_a_prevenir: string,
    adresse: AddressRequest,
    date_entre: Date,
    pays_origine: CountryRequest,
    type_patient: HasInsurance,
    lieu_naissance?: string,
    tel2?: string,
    type_carte?: string,
    no_carte?: string,
    profession?: ProfessionRequest,
    employeur?: EmployerRequest
  ) {
    this.nom = nom;
    this.prenoms = prenoms;
    this.date_naissance = date_naissance;
    this.sexe = sexe;
    this.tel1 = tel1;
    this.is_assure = is_assure;
    this.personne_a_prevenir = personne_a_prevenir;
    this.adresse = adresse;
    this.date_entre = date_entre;
    this.pays_origine = pays_origine;
    this.type_patient = type_patient;
    this.lieu_naissance = lieu_naissance;
    this.tel2 = tel2;
    this.type_carte = type_carte;
    this.no_carte = no_carte;
    this.profession = profession;
    this.employeur = employeur;
  }
}
