import { IAddress } from "./address.model";
import { ICountry } from "./country.model";
import { IEmployer } from "./employer.model";
import { IInsurance } from "./insurance.model";
import { IProfession } from "./profession.model";

export interface IPatient {
  id: number;
  reference: string;
  nom: string;
  prenoms: string;
  date_naissance: Date;
  sexe: string;
  lieu_naissance?: string;
  is_assure: boolean;
  tel1: string;
  tel2?: string;
  personToContact: string; // ???
  type_carte?: string;
  no_carte?: string;
  date_entre: Date;
  pays_origine?: ICountry | string;
  profession?: IProfession | string;
  assurance?: IInsurance | string;
  employer?: IEmployer;
  adresse: string;

}
