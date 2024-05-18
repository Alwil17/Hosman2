import { NeighborhoodResponse } from "./responses/neighborhood-response.model";

export interface INeighborhood {
  id: number;
  nom: string;
}

export class Neighborhood {
  id: number;
  nom: string;

  constructor(iNeighborhood: INeighborhood) {
    this.id = iNeighborhood.id;
    this.nom = iNeighborhood.nom;
  }

  static fromResponse(iNeighborhood: NeighborhoodResponse) {
    return new Neighborhood({
      id: iNeighborhood.id,
      nom: iNeighborhood.nom,
    });
  }

  // constructor(id: number, nom: string) {
  //   this.id = id;
  //   this.nom = nom;
  // }
}
