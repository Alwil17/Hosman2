import { ICity } from "./city.model";
import { INeighborhood } from "./neighborhood.model";

export interface IAddress {
  id?: number;
  arrondissement?: string; // ???
  no_number?: string; // ???
  rue?: string; // ???
  bp?: string; // ???
  ville?: ICity;
  quartier?: INeighborhood;
}
