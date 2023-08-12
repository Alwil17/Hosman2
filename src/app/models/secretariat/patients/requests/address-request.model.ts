import { CityRequest } from "./city-request.model";
import { NeighborhoodRequest } from "./neighborhood-request.model";

export class AddressRequest {
  ville: CityRequest;
  quartier: NeighborhoodRequest;
  details?: string;
  // arrondissement?: string; // ???
  // no_number?: string; // ???
  // rue?: string; // ???
  // bp?: string; // ???

  constructor(
    ville: CityRequest,
    quartier: NeighborhoodRequest,
    details?: string
  ) {
    this.ville = ville;
    this.quartier = quartier;
    this.details = details;
  }
}
