import { CityRequest } from "./city-request.model";
import { NeighborhoodRequest } from "./neighborhood-request.model";

export class AddressRequest {
  ville_id: number;
  quartier_id: number;
  rue?: string;
  bp?: string;
  arrondissement?: string;
  no_maison?: string;

  constructor(
    ville_id: number,
    quartier_id: number,
    rue?: string,
    bp?: string,
    arrondissement?: string,
    no_maison?: string
  ) {
    this.ville_id = ville_id;
    this.quartier_id = quartier_id;
    this.rue = rue;
    this.bp = bp;
    this.arrondissement = arrondissement;
    this.no_maison = no_maison;
  }
}
