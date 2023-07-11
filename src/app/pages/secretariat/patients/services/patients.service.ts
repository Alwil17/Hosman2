import { Injectable } from "@angular/core";
import { IPatient } from "src/app/core/models/patient.model";

@Injectable({
  providedIn: "root",
})
export class PatientsService {
  activePatient: IPatient = {
    id: -1,
    reference: "",
    nom: "",
    prenoms: "",
    date_naissance: new Date(),
    sexe: "",
    is_assure: false,
    tel1: "",
    personToContact: "",
    date_entre: new Date(),
    adresse: "",
  };

  allPatients: IPatient[] = [];

  constructor() {}

  registerPatient(patient: IPatient) {
    this.activePatient = patient;

    this.allPatients = [...this.allPatients, patient];

    console.log(this.activePatient);
  }

  getActivePatient() {
    return { ...this.activePatient };
  }

  getAllPatients() {
    return [...this.allPatients];
  }
}
