import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { InsuranceTypeResponse } from "src/app/models/secretariat/patients/responses/insurance-type-response.model";

const baseUrl = "http://localhost:8081/typeAssurances";

@Injectable({
  providedIn: "root",
})
export class InsuranceTypeService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<InsuranceTypeResponse[]> {
    return this.http.get<InsuranceTypeResponse[]>(baseUrl);
  }
}
