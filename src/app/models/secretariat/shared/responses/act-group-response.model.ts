import { ActResponse } from "./act-response.model";

export interface ActGroupResponse {
  id: number;
  libelle: string;
  position: number;
  couleur: string;
  actes: ActResponse[];
}
