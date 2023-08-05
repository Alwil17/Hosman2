export class Profession {
  id: number;
  nom: string;
  // slug?: string ???

  constructor(id: number, nom: string) {
    this.id = id;
    this.nom = nom;
  }
}
