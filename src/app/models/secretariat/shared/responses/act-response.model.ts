import { ActGroupResponse } from "./act-group-response.model";

export interface ActResponse {
  id: number;
  libelle: string;
  code: string;
  groupe: ActGroupResponse;
}
