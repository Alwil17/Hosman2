import { PersonToContact } from "src/app/models/secretariat/patients/person-to-contact.model";

const PERSONTOCONTACTS: PersonToContact[] = [
  new PersonToContact({
    id: 1,
    nom: "CATASTROPHE",
    prenoms: "Papa",
    tel: "90125500",
    adresse: "Rue de l'OCAM",
  }),
];

export { PERSONTOCONTACTS };
