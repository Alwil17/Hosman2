import { ActResponse } from "./act-response.model";

export interface ActGroupResponse {
  id: number;
  libelle: string;
  code: string;
  position: number;
  couleur: string;
  // actes: ActResponse[];
}
