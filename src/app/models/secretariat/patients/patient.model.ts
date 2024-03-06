import { calculateExactAge } from "src/app/helpers/age-calculator";
import { Address } from "./address.model";
import { Country } from "./country.model";
import { Employer } from "./employer.model";
import { PersonToContact } from "./person-to-contact.model";
import { Profession } from "./profession.model";
import { Insurance } from "./insurance.model";
import { PatientInsurance } from "./patient-insurance.model";
import { HAS_INSURANCES } from "src/app/data/secretariat/has-insurance.data";
import { PatientRequest } from "./requests/patient-request.model";
import { PatientResponse } from "./responses/patient-response.model";
import { City } from "./city.model";
import { Neighborhood } from "./neighborhood.model";
import { ChronicDisease } from "./chronic-disease.model";
import { Parent } from "./parent.model";
import { Backgrounds } from "./backgrounds.model";

export interface IPatient {
  id: number;
  reference: string;
  nom: string;
  prenoms: string;
  date_naissance: Date;
  sexe: string;
  tel1: string;
  is_assure: number;
  date_ajout: Date;
  personne_a_prevenir: PersonToContact;
  adresse: Address;
  pays_origine: Country;
  assurance?: Insurance;
  taux_assurance?: number;
  date_debut_assurance?: Date;
  date_fin_assurance?: Date;
  // patient_assurance?: PatientInsurance;
  nationalite?: Country;
  lieu_naissance?: string;
  tel2?: string;
  type_piece?: string;
  no_piece?: string;
  profession?: Profession;
  employeur?: Employer;

  // Visit/Medical base fileds
  maladies?: ChronicDisease[];
  parents?: Parent[];
  commentaire?: string;

  antecedant?: Backgrounds;
}
export class Patient {
  id: number;
  reference: string;
  nom: string;
  prenoms: string;
  date_naissance: Date;
  sexe: string;
  tel1: string;
  is_assure: number;
  date_ajout: Date;
  personne_a_prevenir: PersonToContact;
  adresse: Address;
  pays_origine: Country;
  assurance?: Insurance;
  taux_assurance?: number;
  date_debut_assurance?: Date;
  date_fin_assurance?: Date;
  // patient_assurance?: PatientInsurance;
  nationalite?: Country;
  lieu_naissance?: string;
  tel2?: string;
  type_piece?: string;
  no_piece?: string;
  profession?: Profession;
  employeur?: Employer;

  // Visit/Medical base fileds
  maladies?: ChronicDisease[];
  parents?: Parent[];
  commentaire?: string;

  antecedant?: Backgrounds;

  constructor(iPatient: IPatient) {
    this.id = iPatient.id;
    this.reference = iPatient.reference;
    this.nom = iPatient.nom;
    this.prenoms = iPatient.prenoms;
    this.date_naissance = iPatient.date_naissance;
    this.sexe = iPatient.sexe;
    this.tel1 = iPatient.tel1;
    this.is_assure = iPatient.is_assure;
    this.date_ajout = iPatient.date_ajout;
    this.personne_a_prevenir = iPatient.personne_a_prevenir;
    this.adresse = iPatient.adresse;
    this.pays_origine = iPatient.pays_origine;
    this.assurance = iPatient.assurance;
    this.taux_assurance = iPatient.taux_assurance;
    this.date_debut_assurance = iPatient.date_debut_assurance;
    this.date_fin_assurance = iPatient.date_fin_assurance;
    // this.patient_assurance = iPatient.patient_assurance;
    this.nationalite = iPatient.nationalite;
    this.lieu_naissance = iPatient.lieu_naissance;
    this.tel2 = iPatient.tel2;
    this.type_piece = iPatient.type_piece;
    this.no_piece = iPatient.no_piece;
    this.profession = iPatient.profession;
    this.employeur = iPatient.employeur;

    // Visit/Medical base fileds
    this.maladies = iPatient.maladies;
    this.parents = iPatient.parents;
    this.commentaire = iPatient.commentaire;
    this.antecedant = iPatient.antecedant;
  }

  // static emptyPatient(): Patient {
  //   return new Patient({
  //     id: -1,
  //     reference: "",
  //     nom: "",
  //     prenoms: "",
  //     date_naissance: new Date(),
  //     sexe: "",
  //     tel1: "",
  //     is_assure: 0,
  //     date_ajout: new Date(),
  //     personne_a_prevenir: new PersonToContact({
  //       id: -1,
  //       nom: "",
  //       prenoms: "",
  //       tel: "",
  //       adresse: "",
  //     }),
  //     adresse: new Address({
  //       id: -1,
  //       ville: new City({ id: -1, nom: "" }),
  //       quartier: new Neighborhood({ id: -1, nom: "" }),
  //     }),
  //     pays_origine: new Country({
  //       id: -1,
  //       nom: "",
  //       nationalite: "",
  //     }),
  //   });
  // }

  static fromResponse(patient: PatientResponse): Patient {
    return new Patient({
      id: patient.id,
      reference: patient.reference,
      nom: patient.nom,
      prenoms: patient.prenoms,
      date_naissance: patient.date_naissance,
      sexe: patient.sexe,
      tel1: patient.tel1,
      is_assure: patient.is_assure,
      date_ajout: patient.date_ajout,
      personne_a_prevenir: PersonToContact.fromResponse(
        patient.personne_a_prevenir
      ),
      adresse: Address.fromResponse(patient.adresse),
      pays_origine: Country.fromResponse(patient.pays_origine),
      assurance: patient.assurance
        ? Insurance.fromResponse(patient.assurance)
        : undefined,
      taux_assurance: patient.taux_assurance,
      date_debut_assurance: patient.date_debut_assurance,
      date_fin_assurance: patient.date_fin_assurance,
      // patient_assurance: patient.patient_assurance
      //   ? PatientInsurance.fromResponse(patient.patient_assurance)
      //   : undefined,
      lieu_naissance: patient.lieu_naissance,
      tel2: patient.tel2,
      type_piece: patient.type_piece,
      no_piece: patient.no_piece,
      profession: patient.profession
        ? Profession.fromResponse(patient.profession)
        : undefined,
      employeur: patient.employeur
        ? Employer.fromResponse(patient.employeur)
        : undefined,

      // Visit/Medical base fileds
      maladies: patient.maladies ? patient.maladies : undefined,
      parents: patient.parents ? patient.parents : undefined,
      commentaire: patient.commentaire,

      antecedant: patient.antecedant
        ? Backgrounds.fromResponse(patient.antecedant)
        : undefined,
    });
  }

  get age() {
    return calculateExactAge(new Date(this.date_naissance));
  }

  get hasInsurance() {
    return this.assurance ? true : false;

    // return this.is_assure === HAS_INSURANCES.no_local.code ||
    //   this.is_assure === HAS_INSURANCES.no_foreigner.code
    //   ? false
    //   : true;
  }

  // constructor(
  //   id: number,
  //   reference: string,
  //   nom: string,
  //   prenoms: string,
  //   date_naissance: Date,
  //   sexe: string,
  //   tel1: string,
  //   is_assure: number,
  //   date_ajout: Date,
  //   personne_a_prevenir: PersonToContact,
  //   adresse: Address,
  //   pays_origine: Country,
  //   assurance?: Insurance,
  //   patient_assurance?: PatientInsurance,
  //   lieu_naissance?: string,
  //   tel2?: string,
  //   type_piece?: string,
  //   no_piece?: string,
  //   profession?: Profession,
  //   employeur?: Employer
  // ) {
  //   this.id = id;
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

  // static fromIPatient(patient: IPatient): Patient {
  //   return new Patient(
  //     patient.id,
  //     patient.reference,
  //     patient.nom,
  //     patient.prenoms,
  //     patient.date_naissance,
  //     patient.sexe,
  //     patient.tel1,
  //     patient.is_assure,
  //     patient.date_ajout,
  //     patient.personne_a_prevenir,
  //     patient.adresse,
  //     patient.pays_origine,
  //     patient.assurance,
  //     patient.patient_assurance,
  //     patient.lieu_naissance,
  //     patient.tel2,
  //     patient.type_piece,
  //     patient.no_piece,
  //     patient.profession,
  //     patient.employeur
  //   );
  // }
}
