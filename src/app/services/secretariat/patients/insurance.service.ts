import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { delay, map } from "rxjs/operators";
import { Insurance } from "src/app/models/secretariat/patients/insurance.model";
import { InsuranceResponse } from "src/app/models/secretariat/patients/responses/insurance-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "assurances";

@Injectable({
  providedIn: "root",
})
export class InsuranceService {
  insurances: Insurance[] = [];

  constructor(private http: HttpClient) {}

  getAll(): Observable<InsuranceResponse[]> {
    return this.http.get<InsuranceResponse[]>(apiEndpoint).pipe(
      map((insurances) => {
        this.insurances = insurances;

        return insurances;
      })
    );
  }

  // get(id: any): Observable<Patient> {
  //   return this.http.get(`${apiEndpoint}/${id}`);
  // }

  // create(data: any): Observable<any> {
  //   return this.http.post(apiEndpoint, data);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${apiEndpoint}/${id}`, data);
  // }
}
