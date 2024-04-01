import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { map } from "rxjs/operators";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { PatientRequest } from "src/app/models/secretariat/patients/requests/patient-request.model";
import { PatientResponse } from "src/app/models/secretariat/patients/responses/patient-response.model";
import { environment } from "src/environments/environment";
import { PatientVisitInfoRequest } from "src/app/models/secretariat/patients/requests/patient-visit-info-request.model";
import { Consultation } from "src/app/models/medical-base/consultation.model";

const apiEndpoint = environment.baseUrl + "patients";

const medicalBaseEndpoint = environment.medical_base + "consultations";

@Injectable({
  providedIn: "root",
})
export class PatientService {
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

  updateVisitInfo(id: any, data: PatientVisitInfoRequest): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}/bm`, data);
  }

  getConsultationsByPatientReference(
    patientReference: string
  ): Observable<Consultation[]> {
    console.log(medicalBaseEndpoint + "/patient/" + patientReference);

    return this.http.get<Consultation[]>(
      medicalBaseEndpoint + "/patient/" + patientReference
    );
  }
}
