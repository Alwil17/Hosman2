import { CoefficientSocialResponse } from "./responses/coefficient-social-response.model";

export interface ICoefficientSocial {
  nbe: number;
  nim: number;
  nip: number;
  smf: number;
  mni: number;
  mf: number;
  pf: number;
  ass: number;
  imv: number;
  coef?: number;
  commentaire?: string;
}

export class CoefficientSocial {
  nbe: number;
  nim: number;
  nip: number;
  smf: number;
  mni: number;
  mf: number;
  pf: number;
  ass: number;
  imv: number;
  coef?: number;
  commentaire?: string;

  constructor(iCoefficientSocial: ICoefficientSocial) {
    this.nbe = iCoefficientSocial.nbe;
    this.nim = iCoefficientSocial.nim;
    this.nip = iCoefficientSocial.nip;
    this.smf = iCoefficientSocial.smf;
    this.mni = iCoefficientSocial.mni;
    this.mf = iCoefficientSocial.mf;
    this.pf = iCoefficientSocial.pf;
    this.ass = iCoefficientSocial.ass;
    this.imv = iCoefficientSocial.imv;
    this.coef = iCoefficientSocial.coef;
    this.commentaire = iCoefficientSocial.commentaire;
  }

  static fromResponse(
    coefficientSocial: CoefficientSocialResponse
  ): CoefficientSocial {
    return new CoefficientSocial({
      nbe: coefficientSocial.nbe,
      nim: coefficientSocial.nim,
      nip: coefficientSocial.nip,
      smf: coefficientSocial.smf,
      mni: coefficientSocial.mni,
      mf: coefficientSocial.mf,
      pf: coefficientSocial.pf,
      ass: coefficientSocial.ass,
      imv: coefficientSocial.imv,
      coef: coefficientSocial.coef,
      commentaire: coefficientSocial.commentaire,
    });
  }
}
