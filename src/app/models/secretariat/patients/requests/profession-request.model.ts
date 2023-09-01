export class ProfessionRequest {
  denomination: string;
  id?: number;

  constructor(denomination: string, id: number) {
    this.denomination = denomination;
    this.id = id;
  }
}
