export interface IActivity {
  id: number;
  prestation: string;
  prix: number;
  ss_traitance: number;
  new_tariff: number;
}

export interface IActivitySelect {
  id: number;
  rubrique: string;
  activity: string;
  prix: number;
  quantite: number;
  prix_total: number;
}
