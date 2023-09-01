import { CityResponse } from "./responses/city-response.model";

export interface ICity {
  id: number;
  nom: string;
}

export class City {
  id: number;
  nom: string;

  constructor(iCity: ICity) {
    this.id = iCity.id;
    this.nom = iCity.nom;
  }

  static fromResponse(city: CityResponse) {
    return new City({
      id: city.id,
      nom: city.nom,
    });
  }

  // constructor(id: number, nom: string) {
  //   this.id = id;
  //   this.nom = nom;
  // }
}
