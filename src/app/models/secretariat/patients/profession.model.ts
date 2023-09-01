import { ProfessionResponse } from "./responses/profession-response.model";

export interface IProfession {
  id: number;
  denomination: string;
}

export class Profession {
  id: number;
  denomination: string;

  constructor(iProfession: IProfession) {
    this.id = iProfession.id;
    this.denomination = iProfession.denomination;
  }

  static fromResponse(profession: ProfessionResponse) {
    return new Profession({
      id: profession.id,
      denomination: profession.denomination,
    });
  }

  // constructor(id: number, denomination: string) {
  //   this.id = id;
  //   this.denomination = denomination;
  // }
}
