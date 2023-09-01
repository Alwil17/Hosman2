import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { delay } from "rxjs/operators";
import { CITIES } from "src/app/data/secretariat/cities.data";
import { COUNTRIES } from "src/app/data/secretariat/countries.data";
import { EMPLOYERS } from "src/app/data/secretariat/employers.data";
import { HAS_INSURANCES } from "src/app/data/secretariat/has-insurance.data";
import { INSURANCES } from "src/app/data/secretariat/insurances.data";
import { NEIGHBORHOODS } from "src/app/data/secretariat/neighborhoods.data";
// import { PATIENT_INSURANCES } from "src/app/data/secretariat/patient-insurance.data";
import { PATIENTS } from "src/app/data/secretariat/patients.data";
import { PROFESSIONS } from "src/app/data/secretariat/professions.data";
import { HasInsuranceCode } from "src/app/models/secretariat/patients/has-insurance.model";
import { Insurance } from "src/app/models/secretariat/patients/insurance.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { PatientRequest } from "src/app/models/secretariat/patients/requests/patient-request.model";
import { PatientResponse } from "src/app/models/secretariat/patients/responses/patient-response.model";

const baseUrl = "http://localhost:8081/patients";

@Injectable({
  providedIn: "root",
})
export class PatientService {
  private activePatient: Patient = PATIENTS[0];
  // {
  //   id: -1,
  //   reference: "",
  //   nom: "",
  //   prenoms: "",
  //   date_naissance: new Date(),
  //   sexe: "",
  //   is_assure: false,
  //   tel1: "",
  //   personne_a_prevenir: "",
  //   date_entre: new Date(),
  //   adresse: {
  //     id: -1,
  //     ville: CITIES[0],
  //     quartier: NEIGHBORHOODS[0],
  //   },
  //   pays_origine: COUNTRIES[0],
  //   type_patient: HAS_INSURANCES[0],
  // };

  allPatients: Patient[] = PATIENTS;

  // private patientInsurances: IPatientInsurance[] = PATIENT_INSURANCES;

  constructor(private http: HttpClient) {}

  registerPatient(
    patientRequest: PatientRequest
    // patientRequest: Patient,
    // insurance?: Insurance,
    // patientInsurance?: IPatientInsurance
  ): Observable<PatientResponse> {
    // PATIENT
    /*const patient = new Patient(patientRequest); // Patient.emptyPatient();

    patient.id = this.allPatients.length + 1;

    patient.reference = "PAT" + (this.allPatients.length + 1);

    patient.adresse.ville = CITIES.find(
      (city) => patientRequest.adresse.ville.id == city.id
    )!;

    patient.adresse.quartier = NEIGHBORHOODS.find(
      (neighborhood) => patientRequest.adresse.quartier.id == neighborhood.id
    )!;

    patient.pays_origine = COUNTRIES.find(
      (country) => patientRequest.pays_origine.id == country.id
    )!;

    patient.profession = PROFESSIONS.find(
      (profession) => patientRequest.profession?.id == profession.id
    );

    patient.employeur = EMPLOYERS.find(
      (employer) => patientRequest.employeur?.id == employer.id
    );*/

    // patientRequest.type_patient = HAS_INSURANCES.find(
    //   (hasInsurance) => patientRequest.type_patient.id == hasInsurance.id
    // )!;

    //INSURANCE
    /*if (patient.assurance) {
      patient.assurance = INSURANCES.find(
        (ins) => patient.assurance!.id == ins.id
      );
    }*/

    //PATIENT_INSURANCE
    // if (insurance && patientInsurance) {
    //   this.registerPatientInsurance({
    //     id: -1,
    //     patient_id: patientRequest.id,
    //     assurance_id: insurance!.id,
    //     taux: patientInsurance.taux,
    //     date_expiration: patientInsurance.date_expiration,
    //   });
    // }

    /*this.allPatients = [...this.allPatients, patient];

    this.activePatient = patient;

    console.log(patient);*/
    // console.log(insurance);

    // return of(patient).pipe(delay(3000));

    return this.http.post<PatientResponse>(baseUrl, patientRequest);
  }

  getPatient(patientId: number) {
    return this.allPatients.find((value) => value.id == patientId);
  }

  getActivePatient() {
    return new Patient(this.activePatient);
  }

  setActivePatient(patientId: number) {
    this.activePatient = this.allPatients.find(
      (patient) => patientId == patient.id
    )!;
  }

  getActivePatientType() {
    // console.log("active patient type", this.activePatient.type_patient.id);

    return this.activePatient.is_assure;
  }

  getActivePatientRate() {
    // console.log("active patient type", this.activePatient.type_patient.id);

    // const patientInsurance = this.patientInsurances.find(
    //   (value) => value.patient_id == this.activePatient.id
    // );

    return this.activePatient.patient_assurance?.taux ?? 0;
  }

  getAllPatients() {
    return [...this.allPatients];
  }

  // registerPatientInsurance(patientInsurance: IPatientInsurance) {
  //   patientInsurance.id = Math.floor(Math.random() * (1000000 - 1 + 1) + 1);
  //   this.patientInsurances = [...this.patientInsurances, patientInsurance];

  //   console.log(patientInsurance);
  // }

  // getPatientInsurance(patientId: number, insuranceId: number) {
  //   return this.patientInsurances.find(
  //     (value) =>
  //       value.patient_id == patientId && value.assurance_id == insuranceId
  //   );
  // }

  // getInsurance(patientId: number) {
  //   const patientInsurance = this.patientInsurances.find(
  //     (value) => value.patient_id == patientId
  //   );

  //   const insurance = INSURANCES.find(
  //     (ins) => patientInsurance?.assurance_id == ins.id
  //   );

  //   return insurance;
  // }

  // getAll(): Observable<Patient[]> {
  //   return this.http.get<Patient[]>(baseUrl);
  // }

  // get(id: any): Observable<Patient> {
  //   return this.http.get(`${baseUrl}/${id}`);
  // }

  // create(data: any): Observable<any> {
  //   return this.http.post(baseUrl, data);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${baseUrl}/${id}`, data);
  // }
}
