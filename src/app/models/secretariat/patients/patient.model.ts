import { Address } from "./address.model";
import { Country } from "./country.model";
import { Employer } from "./employer.model";
import { HasInsurance } from "./has-insurance.model";
import { Profession } from "./profession.model";

export class Patient {
  id: number;
  reference: string;
  nom: string;
  prenoms: string;
  date_naissance: Date;
  sexe: string;
  tel1: string;
  is_assure: boolean;
  personne_a_prevenir: string;
  adresse: Address;
  date_entre: Date;
  pays_origine: Country;
  type_patient: HasInsurance;
  lieu_naissance?: string;
  tel2?: string;
  type_carte?: string;
  no_carte?: string;
  profession?: Profession;
  // assurance?: IInsurance;
  employeur?: Employer;

  constructor(
    id: number,
    reference: string,
    nom: string,
    prenoms: string,
    date_naissance: Date,
    sexe: string,
    tel1: string,
    is_assure: boolean,
    personne_a_prevenir: string,
    adresse: Address,
    date_entre: Date,
    pays_origine: Country,
    type_patient: HasInsurance,
    lieu_naissance?: string,
    tel2?: string,
    type_carte?: string,
    no_carte?: string,
    profession?: Profession,
    employeur?: Employer
  ) {
    this.id = id;
    this.reference = reference;
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
