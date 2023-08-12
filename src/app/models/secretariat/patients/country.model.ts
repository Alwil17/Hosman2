export class Country {
  id: number;
  nom: string;
  // slug?: string ???
  nationalite: string;
  code?: string;
  indicatif?: string;

  constructor(
    id: number,
    nom: string,
    nationalite: string,
    code?: string,
    indicatif?: string
  ) {
    this.id = id;
    this.nom = nom;
    this.nationalite = nationalite;
    this.code = code;
    this.indicatif = indicatif;
  }
}
