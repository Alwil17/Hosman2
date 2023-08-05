import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { COUNTRIES } from "./countries.data";
import { PROFESSIONS } from "./professions.data";
import { INSURANCES } from "./insurances.data";
import { EMPLOYERS } from "./employers.data";
import { ADDRESSES } from "./addresses.data";
import { HAS_INSURANCES } from "./has-insurance.data";

const PATIENTS: Patient[] = [
  {
    id: 1,
    reference: "PAT1",
    nom: "CATASTROPHE",
    prenoms: "Climatique",
    date_naissance: new Date("1999-07-24"),
    sexe: "Masculin",
    lieu_naissance: "Lomé - Golfe",
    is_assure: false,
    tel1: "99686933",
    tel2: "00000000",
    personne_a_prevenir: "CATASTROPHE Papa - 90125500", // ???
    type_carte: "CNI",
    no_carte: "0003-154-1324",
    date_entre: new Date("2023-07-24"),
    pays_origine: COUNTRIES[0],
    profession: PROFESSIONS[0],
    // assurance: INSURANCES[0],
    employeur: EMPLOYERS[0],
    adresse: ADDRESSES[0],
    type_patient: HAS_INSURANCES[0],
  },
];

export { PATIENTS };
