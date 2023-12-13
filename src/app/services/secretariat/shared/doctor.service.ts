import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { map } from "rxjs/operators";
import { Doctor } from "src/app/models/secretariat/shared/doctor.model";
import { DoctorResponse } from "src/app/models/secretariat/shared/responses/doctor-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "medecins";

@Injectable({
  providedIn: "root",
})
export class DoctorService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Doctor[]> {
    return this.http.get<DoctorResponse[]>(apiEndpoint).pipe(
      map((doctors) => {
        const mapped: Doctor[] = doctors.map((doctor) =>
          Doctor.fromResponse(doctor)
        );

        return mapped;
      })
    );
  }

  // Make an enum for doctor type
  getByType(type: string): Observable<Doctor[]> {
    if (!type) {
      return of([]);
    }

    type = type.toLowerCase();

    return this.http.get<DoctorResponse[]>(`${apiEndpoint}/type/${type}`).pipe(
      map((doctors) => {
        console.log(doctors);

        const mapped: Doctor[] = doctors.map((doctor) =>
          Doctor.fromResponse(doctor)
        );

        return mapped;
      })
    );
  }

  // create(data: DoctorRequest): Observable<any> {
  //   return this.http.post(apiEndpoint, data);
  // }

  // get(id: any): Observable<Expense> {
  //   return this.http.get(`${baseUrl}/${id}`);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${baseUrl}/${id}`, data);
  // }
}
