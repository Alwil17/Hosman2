import { PhoneBookGroupResponse } from "./responses/phone-book-group-response.model";

export interface IPhoneBookGroup {
  id: number;
  nom: string;
  slug: string;
}

export class PhoneBookGroup {
  id: number;
  nom: string;
  slug: string;

  constructor(iPhoneBookGroup: IPhoneBookGroup) {
    this.id = iPhoneBookGroup.id;
    this.nom = iPhoneBookGroup.nom;
    this.slug = iPhoneBookGroup.slug;
  }

  static fromResponse(phoneBookGroup: PhoneBookGroupResponse): PhoneBookGroup {
    return new PhoneBookGroup({
      id: phoneBookGroup.id,
      nom: phoneBookGroup.nom,
      slug: phoneBookGroup.slug,
    });
  }
}
