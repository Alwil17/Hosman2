export class CountryRequest {
  nom: string;
  nationalite: string;
  code?: string;
  indicatif?: number;
  id?: number;

  constructor(
    nom: string,
    nationalite: string,
    code?: string,
    indicatif?: number,
    id?: number
  ) {
    this.nom = nom;
    this.nationalite = nationalite;
    this.code = code;
    this.indicatif = indicatif;
    this.id = id;
  }
}
