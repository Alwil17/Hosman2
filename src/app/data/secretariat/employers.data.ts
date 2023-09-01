import { Employer } from "src/app/models/secretariat/patients/employer.model";

const EMPLOYERS: Employer[] = [
  {
    id: 1,
    nom: "ARCEP",
    email: "mail@arcep.com",
    tel1: "22222222",
    tel2: "",
  },
  {
    id: 2,
    nom: "BIDC",
    email: "mail@bidc.com",
    tel1: "22222222",
    tel2: "21212121",
  },
  {
    id: 3,
    nom: "MOOV",
    email: "mail@moov.com",
    tel1: "21212121",
    tel2: "22222222",
  },
  {
    id: 4,
    nom: "TOGOCOM",
    email: "mail@togocom.com",
    tel1: "23232323",
    tel2: "",
  },
  // {
  //   id: 5,
  //   nom: "Autres",
  //    email: "mail@",
  //    tel1: "mail@",
  //    tel2: "mail@",
  // },
];

export { EMPLOYERS };
