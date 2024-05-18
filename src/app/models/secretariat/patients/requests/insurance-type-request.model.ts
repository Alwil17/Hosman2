export class InsuranceTypeRequest {
  id: number;
  nom: string;

  constructor(id: number, nom: string) {
    this.id = id;
    this.nom = nom;
  }
}
