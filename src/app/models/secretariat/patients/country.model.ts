import { CountryResponse } from "./responses/country-response.model";

export interface ICountry {
  id: number;
  nom: string;
  nationalite: string;
  code?: string;
  indicatif?: number;
}

export class Country {
  id: number;
  nom: string;
  nationalite: string;
  code?: string;
  indicatif?: number;

  constructor(iCountry: ICountry) {
    this.id = iCountry.id;
    this.nom = iCountry.nom;
    this.nationalite = iCountry.nationalite;
    this.code = iCountry.code;
    this.indicatif = iCountry.indicatif;
  }

  static fromResponse(country: CountryResponse) {
    return new Country({
      id: country.id,
      nom: country.nom,
      nationalite: country.nationalite,
      code: country.code,
      indicatif: country.indicatif,
    });
  }

  // constructor(
  //   id: number,
  //   nom: string,
  //   nationalite: string,
  //   code?: string,
  //   indicatif?: string
  // ) {
  //   this.id = id;
  //   this.nom = nom;
  //   this.nationalite = nationalite;
  //   this.code = code;
  //   this.indicatif = indicatif;
  // }
}
