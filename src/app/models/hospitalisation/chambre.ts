export interface IChambre {
    id: number;
    nom: string
  }

  export interface ChambreResponse {
    id: number;
    nom: string
  }  
  
  export class Chambre {
    id: number;
    nom: string
  
    constructor(iChambre: IChambre) {
      this.id = iChambre.id;
      this.nom = iChambre.nom;
    }
  
    static fromResponse(chambre: ChambreResponse): Chambre {
      return new Chambre({
        id: chambre.id,
        nom: chambre.nom
      });
    }
}