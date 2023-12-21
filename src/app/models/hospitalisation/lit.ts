import { Chambre, ChambreResponse } from './chambre'; // Assurez-vous d'importer le modèle de Chambre

export interface ILit {
  id: number;
  nom: string;
  slug: string;
  chambre: Chambre; // Utilisation du modèle Chambre
}

export interface LitResponse {
  id: number;
  nom: string;
  slug: string;
  chambre: ChambreResponse; // Utilisation du modèle ChambreResponse
}

export class Lit {
  id: number;
  nom: string;
  slug: string;
  chambre: Chambre; // Utilisation du modèle Chambre

  constructor(iLit: ILit) {
    this.id = iLit.id;
    this.nom = iLit.nom;
    this.slug = iLit.slug;
    this.chambre = new Chambre(iLit.chambre); // Initialisation de l'objet Chambre
  }

  static fromResponse(lit: LitResponse): Lit {
    return new Lit({
      id: lit.id,
      nom: lit.nom,
      slug: lit.slug,
      chambre: Chambre.fromResponse(lit.chambre), // Utilisation de la méthode fromResponse de Chambre
    });
  }
}