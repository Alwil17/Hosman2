// export interface IActivity {
//   id: number;
//   prestation: string;
//   prix: number;
//   ss_traitance: number;
//   new_tariff: number;
// }

// export interface IActivitySelect {
//   id: number;
//   rubrique: string;
//   activity: string;
//   prix: number;
//   quantite: number;
//   prix_total: number;
// }

export interface IActivity {
  id: any;
  designation: string;
  NA: number;
  AL_S: number;
  ENA: number;
  AHZ: number;
  description?: string;
}

export interface IPrestation {
  id: any;
  designation: string;
  price: number;
  description?: string;
}

export interface IPrestationSelect {
  id: any;
  rubrique: string;
  activity: string;
  price: number;
  quantity: number;
  total_price: number;
}
