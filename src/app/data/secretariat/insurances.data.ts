import { Insurance } from "src/app/models/secretariat/patients/insurance.model";
import { INSURANCE_TYPES } from "./insurance-types.data";

const INSURANCES: Insurance[] = [
  {
    id: 1,
    nom: "ASCOMA",
    type_assurance: INSURANCE_TYPES[0],
    email: "ascoma@insurance.com",
    tel1: "99999999",
    tel2: "77777777",
    representant: "REP",
    slug: "",
  },
  {
    id: 2,
    nom: "GTA",
    type_assurance: INSURANCE_TYPES[0],
    email: "gta@insurance.com",
    tel1: "99999999",
    tel2: "77777777",
    representant: "REP",
    slug: "",
  },
  {
    id: 3,
    nom: "ETRA",
    type_assurance: INSURANCE_TYPES[1],
    email: "etra@insurance.com",
    tel1: "99999999",
    tel2: "77777777",
    representant: "REP",
    slug: "",
  },
  {
    id: 4,
    nom: "GRAS SAVOYE",
    type_assurance: INSURANCE_TYPES[1],
    email: "grassavoye@insurance.com",
    tel1: "99999999",
    tel2: "77777777",
    representant: "REP",
    slug: "",
  },
];

export { INSURANCES };
