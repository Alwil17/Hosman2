import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { delay, map } from "rxjs/operators";
import { CITIES } from "src/app/data/secretariat/cities.data";
import { COUNTRIES } from "src/app/data/secretariat/countries.data";
import { EMPLOYERS } from "src/app/data/secretariat/employers.data";
import { HAS_INSURANCES } from "src/app/data/secretariat/has-insurance.data";
import { INSURANCES } from "src/app/data/secretariat/insurances.data";
import { NEIGHBORHOODS } from "src/app/data/secretariat/neighborhoods.data";
// import { PATIENT_INSURANCES } from "src/app/data/secretariat/patient-insurance.data";
import { PATIENTS } from "src/app/data/secretariat/patients.data";
import { PROFESSIONS } from "src/app/data/secretariat/professions.data";
import { Address } from "src/app/models/secretariat/patients/address.model";
import { HasInsuranceCode } from "src/app/models/secretariat/patients/has-insurance.model";
import { Insurance } from "src/app/models/secretariat/patients/insurance.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { PersonToContact } from "src/app/models/secretariat/patients/person-to-contact.model";
import { PatientRequest } from "src/app/models/secretariat/patients/requests/patient-request.model";
import { PatientResponse } from "src/app/models/secretariat/patients/responses/patient-response.model";
import { CityService } from "./city.service";
import { CountryService } from "./country.service";
import { EmployerService } from "./employer.service";
import { InsuranceTypeService } from "./insurance-type.service";
import { InsuranceService } from "./insurance.service";
import { NeighborhoodService } from "./neighborhood.service";
import { ProfessionService } from "./profession.service";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "patients";

@Injectable({
  providedIn: "root",
})
export class PatientService {
  private activePatient: Patient = PATIENTS[0];

  allPatients: Patient[] = PATIENTS;

  constructor(private http: HttpClient) {}

  searchBy(searchTerm: string, criterion: string): Observable<Patient[]> {
    let apiComplementary = "";

    if (criterion == "fullname") {
      apiComplementary = "nom";
    } else if (criterion == "firstname") {
      apiComplementary = "prenoms";
    } else if (criterion == "reference") {
      apiComplementary = "reference";
    } else if (criterion == "dob") {
      apiComplementary = "naissance";
    } else if (criterion == "doc") {
      apiComplementary = "entree";
    }

    if (searchTerm == "" || apiComplementary == "") {
      return of([]);
    }

    return this.http
      .get<PatientResponse[]>(
        `${apiEndpoint}/search?${apiComplementary}=${searchTerm}`
      )
      .pipe(
        map((patients) => {
          const mapped: Patient[] = patients.map((patient) =>
            Patient.fromResponse(patient)
          );

          return mapped;
        })
      );
  }

  // searchByFullname(fullname: string): Observable<Patient[]> {
  //   return this.http
  //     .get<PatientResponse[]>(`${apiEndpoint}/search?nom=${fullname}`)
  //     .pipe(
  //       map((patients) => {
  //         const mapped: Patient[] = patients.map((patient) =>
  //           Patient.fromResponse(patient)
  //         );

  //         return mapped;
  //       })
  //     );
  // }

  searchByReference(reference: string): Observable<Patient[]> {
    return this.http
      .get<PatientResponse[]>(`${apiEndpoint}/search?reference=${reference}`)
      .pipe(
        map((patients) => {
          const mapped: Patient[] = patients.map((patient) =>
            Patient.fromResponse(patient)
          );

          return mapped;
        })
      );
  }

  create(data: PatientRequest): Observable<any> {
    return this.http.post<any>(apiEndpoint, data);
  }

  getAll(): Observable<Patient[]> {
    return this.http.get<PatientResponse[]>(apiEndpoint).pipe(
      map((patients) => {
        const mapped: Patient[] = patients.map((patient) =>
          Patient.fromResponse(patient)
        );

        return mapped;
      })
    );
  }

  get(id: any): Observable<Patient> {
    return this.http
      .get<PatientResponse>(`${apiEndpoint}/${id}`)
      .pipe(map((patient) => Patient.fromResponse(patient)));
  }

  update(id: any, data: PatientRequest): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}`, data);
  }

  // getPatient(patientId: number) {
  //   return this.allPatients.find((value) => value.id == patientId);
  // }

  getActivePatient() {
    return new Patient(this.activePatient);
  }

  setActivePatient(patientId: number): Observable<void> {
    return this.get(patientId).pipe(
      map((patient) => {
        this.activePatient = patient;

        return;
      })
    );
  }

  getActivePatientType() {
    return this.activePatient.is_assure;
  }

  getActivePatientRate() {
    return this.activePatient?.taux_assurance ?? 0;
  }
}
