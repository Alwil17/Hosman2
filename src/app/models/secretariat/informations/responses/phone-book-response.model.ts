import { PhoneBookGroupResponse } from "./phone-book-group-response.model";

export interface PhoneBookResponse {
  id: number;
  nom?: string;
  prenom?: string;
  profession?: string;
  secteur?: string;
  bureau?: string;
  tel1: string;
  tel2?: string;
  domicile?: string;
  email?: string;
  bip?: string;
  no_poste?: string;
  categorie_slug: string;
  categorie?: PhoneBookGroupResponse;
}
