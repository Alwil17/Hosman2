import { Injectable } from "@angular/core";
import { IPatient } from "src/app/models/secretariat/patients/patient.model";

// const baseUrl = 'http://localhost:8080/api/...';

@Injectable({
  providedIn: "root",
})
export class PatientService {
  private activePatient: IPatient = {
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

  constructor() // private http: HttpClient
  {}

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

  // getAll(): Observable<IPatient[]> {
  //   return this.http.get<IPatient[]>(baseUrl);
  // }

  // get(id: any): Observable<IPatient> {
  //   return this.http.get(`${baseUrl}/${id}`);
  // }

  // create(data: any): Observable<any> {
  //   return this.http.post(baseUrl, data);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${baseUrl}/${id}`, data);
  // }
}
