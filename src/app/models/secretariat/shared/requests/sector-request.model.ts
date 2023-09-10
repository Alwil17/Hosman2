export interface ISectorRequest {
  libelle: string;
  couleur: string;
  code: string;
}

export class SectorRequest {
  libelle: string;
  couleur: string;
  code: string;

  constructor(iSector: ISectorRequest) {
    this.libelle = iSector.libelle;
    this.couleur = iSector.couleur;
    this.code = iSector.code;
  }
}
