export interface ITherapeuticClass {
  id: number;
  nom: string;
  slug: string;
  couleur: string;
}

export class TherapeuticClass {
  id: number;
  nom: string;
  slug: string;
  couleur: string;

  constructor(iTherapeuticClass: TherapeuticClass) {
    this.id = iTherapeuticClass.id;
    this.nom = iTherapeuticClass.nom;
    this.slug = iTherapeuticClass.slug;
    this.couleur = iTherapeuticClass.couleur;
  }
}
