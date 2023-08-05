import { CityResponse } from "./city-response.model";
import { NeighborhoodResponse } from "./neighborhood-response.model";

export interface AddressResponse {
  id: number;
  ville: CityResponse;
  quartier: NeighborhoodResponse;
  details?: string;
  // arrondissement?: string; // ???
  // no_number?: string; // ???
  // rue?: string; // ???
  // bp?: string; // ???
}
