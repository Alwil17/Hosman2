import { City } from "./city.model";
import { Neighborhood } from "./neighborhood.model";

export class Address {
  id: number;
  ville: City;
  quartier: Neighborhood;
  details?: string;
  // arrondissement?: string; // ???
  // no_number?: string; // ???
  // rue?: string; // ???
  // bp?: string; // ???

  constructor(
    id: number,
    ville: City,
    quartier: Neighborhood,
    details?: string
  ) {
    this.id = id;
    this.ville = ville;
    this.quartier = quartier;
    this.details = details;
  }

  // static emptyAddress(): Address {
  //   return new Address(-1, new City(-1, ""), new Neighborhood(-1, ""));
  // }
}
