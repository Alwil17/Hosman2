import { IAddress } from "./address.model";
import { ICountry } from "./country.model";
import { IEmployer } from "./employer.model";
import { IHasInsurance } from "./has-insurance.model";
import { IInsurance } from "./insurance.model";
import { IProfession } from "./profession.model";

export interface IPatient {
  id: number;
  reference: string;
  nom: string;
  prenoms: string;
  date_naissance: Date;
  sexe: string;
  tel1: string;
  is_assure: boolean;
  personne_a_prevenir: string; // ???
  adresse: IAddress;
  date_entre: Date;
  lieu_naissance?: string;
  tel2?: string;
  type_carte?: string;
  no_carte?: string;
  pays_origine: ICountry;
  profession?: IProfession;
  // assurance?: IInsurance;
  employeur?: IEmployer;
  type_patient: IHasInsurance
}
