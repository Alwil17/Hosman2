export class CountryRequest {
  nom: string;
  nationalite: string;
  code?: string;
  indicatif?: string;
  id?: number;

  constructor(
    nom: string,
    nationalite: string,
    code?: string,
    indicatif?: string,
    id?: number
  ) {
    this.nom = nom;
    this.nationalite = nationalite;
    this.code = code;
    this.indicatif = indicatif;
    this.id = id;
  }
}
