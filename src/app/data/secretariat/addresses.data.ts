import { Address } from "src/app/models/secretariat/patients/address.model";
import { CITIES } from "./cities.data";
import { NEIGHBORHOODS } from "./neighborhoods.data";

const ADDRESSES: Address[] = [
  {
    id: 1,
    ville: CITIES[0],
    quartier: NEIGHBORHOODS[0],
    details: "Vit à Paris",
    //   arrondissement?: string, // ???
    //   no_number?: string, // ???
    //   rue?: string, // ???
    //   bp?: string, // ???
  },
];

export { ADDRESSES };
