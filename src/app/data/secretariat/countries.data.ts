import { ICountry } from "src/app/models/secretariat/patients/country.model";

const COUNTRIES: ICountry[] = [
  {
    id: 1,
    nom: "BENIN",
    // slug?: string ???
    nationalite: "Béninoise",
    code: "BE",
    indicatif: "+229",
  },
  {
    id: 2,
    nom: "TOGO",
    // slug?: string ???
    nationalite: "Togolaise",
    code: "TG",
    indicatif: "+228",
  },
  {
    id: 3,
    nom: "GHANA",
    // slug?: string ???
    nationalite: "Ghanéenne",
    code: "GH",
    indicatif: "+228",
  },
  {
    id: 4,
    nom: "CÔTE D'IVOIRE",
    // slug?: string ???
    nationalite: "Ivoirienne",
    code: "CI",
    indicatif: "+228",
  },
  {
    id: 5,
    nom: "CAMEROUN",
    // slug?: string ???
    nationalite: "Camerounaise",
    code: "CAM",
    indicatif: "+228",
  },
  {
    id: 6,
    nom: "NIGERIA",
    // slug?: string ???
    nationalite: "Nigérianne",
    code: "NG",
    indicatif: "+228",
  },
];

export { COUNTRIES };
