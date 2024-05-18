import { SectorResponse } from "./responses/sector-response.model";

export interface ISector {
  id: number;
  libelle: string;
  couleur: string;
  code: string;
}

export class Sector {
  id: number;
  libelle: string;
  couleur: string;
  code: string;

  constructor(iSector: ISector) {
    this.id = iSector.id;
    this.libelle = iSector.libelle;
    this.couleur = iSector.couleur;
    this.code = iSector.code;
  }

  static fromResponse(sector: SectorResponse) {
    return new Sector({
      id: sector.id,
      libelle: sector.libelle,
      couleur: sector.couleur,
      code: sector.code,
    });
  }
}
