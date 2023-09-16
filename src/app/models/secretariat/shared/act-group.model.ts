import { Act } from "./act.model";
import { ActGroupResponse } from "./responses/act-group-response.model";

export interface IActGroup {
  id: number;
  libelle: string;
  code: string;
  position: number;
  couleur: string;
  actes: Act[];
}

export class ActGroup {
  id: number;
  libelle: string;
  code: string;
  position: number;
  couleur: string;
  actes: Act[];

  constructor(iActGroup: IActGroup) {
    this.id = iActGroup.id;
    this.libelle = iActGroup.libelle;
    this.code = iActGroup.code;
    this.position = iActGroup.position;
    this.couleur = iActGroup.couleur;
    this.actes = iActGroup.actes;
  }

  static fromResponse(actGroup: ActGroupResponse) {
    return new ActGroup({
      id: actGroup.id,
      libelle: actGroup.libelle,
      code: actGroup.code,
      position: actGroup.position,
      couleur: actGroup.couleur,
      actes: actGroup.actes,
    });
  }
}
