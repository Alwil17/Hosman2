export interface ISideEffect {
  libelle: string;
  slug: string;
}

export class SideEffect {
  libelle: string;
  slug: string;

  constructor(iSideEffect: ISideEffect) {
    this.libelle = iSideEffect.libelle;
    this.slug = iSideEffect.slug;
  }
}
