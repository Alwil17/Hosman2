export interface ICoefficientSocialRequest {
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

export class CoefficientSocialRequest {
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

  constructor(iCoefficientSocial: ICoefficientSocialRequest) {
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
}
