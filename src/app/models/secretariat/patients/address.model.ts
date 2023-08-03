import { ICity } from "./city.model";
import { INeighborhood } from "./neighborhood.model";

export interface IAddress {
  id: number;
  ville: ICity;
  quartier: INeighborhood;
  details?: string;
  // arrondissement?: string; // ???
  // no_number?: string; // ???
  // rue?: string; // ???
  // bp?: string; // ???
}
