import { Address } from "src/app/models/secretariat/patients/address.model";
import { CITIES } from "./cities.data";
import { NEIGHBORHOODS } from "./neighborhoods.data";

const ADDRESSES: Address[] = [
  {
    id: 1,
    ville: CITIES[0],
    quartier: NEIGHBORHOODS[0],
    rue: "Somewhere",
    bp: "01BP01",
    arrondissement: "5ème",
    no_maison: "1234",
  },
];

export { ADDRESSES };
