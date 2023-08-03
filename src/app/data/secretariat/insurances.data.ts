import { IInsurance } from "src/app/models/secretariat/patients/insurance.model";
import { INSURANCE_TYPES } from "./insurance-types.data";

const INSURANCES: IInsurance[] = [
  {
    id: 1,
    reference: "INS1",
    nom: "ASCOMA",
    type_assurance: INSURANCE_TYPES[0],
    // email: "ascoma@insurance.com",
    // tel1: "99999999",
    // tel2: "11111111",
    // representant?: string;
    // taux ?
  },
  {
    id: 2,
    reference: "INS2",
    nom: "GTA",
    type_assurance: INSURANCE_TYPES[0],
    // email: "ascoma@insurance.com",
    // tel1: "99999999",
    // tel2: "11111111",
    // representant?: string;
    // taux ?
  },
  {
    id: 3,
    reference: "INS3",
    nom: "INAM",
    type_assurance: INSURANCE_TYPES[1],
    // email: "ascoma@insurance.com",
    // tel1: "99999999",
    // tel2: "11111111",
    // representant?: string;
    // taux ?
  },
  {
    id: 4,
    reference: "INS4",
    nom: "GRAS SAVOYE",
    type_assurance: INSURANCE_TYPES[1],
    // email: "ascoma@insurance.com",
    // tel1: "99999999",
    // tel2: "11111111",
    // representant?: string;
    // taux ?
  },
];

export { INSURANCES };
