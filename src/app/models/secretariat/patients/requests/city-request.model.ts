export class CityRequest {
  nom: string;
  id?: number;

  constructor(nom: string, id?: number) {
    this.nom = nom;
    this.id = id;
  }
}
