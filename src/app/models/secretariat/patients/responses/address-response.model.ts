import { CityResponse } from "./city-response.model";
import { NeighborhoodResponse } from "./neighborhood-response.model";

export interface AddressResponse {
  id: number;
  ville: CityResponse;
  quartier: NeighborhoodResponse;
  rue?: string;
  bp?: string;
  arrondissement?: string;
  no_maison?: string;
}
