import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { delay } from "rxjs/operators";
import { InsuranceResponse } from "src/app/models/secretariat/patients/responses/insurance-response.model";

const baseUrl = "http://localhost:8081/assurances";

@Injectable({
  providedIn: "root",
})
export class InsuranceService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<InsuranceResponse[]> {
    return this.http.get<InsuranceResponse[]>(baseUrl);
  }

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
