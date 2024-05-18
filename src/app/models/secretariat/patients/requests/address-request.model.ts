import { CityRequest } from "./city-request.model";
import { NeighborhoodRequest } from "./neighborhood-request.model";

export class AddressRequest {
  ville: CityRequest;
  quartier: NeighborhoodRequest;
  rue?: string;
  bp?: string;
  arrondissement?: string;
  no_maison?: string;

  constructor(
    ville: CityRequest,
    quartier: NeighborhoodRequest,
    rue?: string,
    bp?: string,
    arrondissement?: string,
    no_maison?: string
  ) {
    this.ville = ville;
    this.quartier = quartier;
    this.rue = rue;
    this.bp = bp;
    this.arrondissement = arrondissement;
    this.no_maison = no_maison;
  }
}
