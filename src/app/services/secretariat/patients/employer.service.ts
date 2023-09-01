import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { EmployerResponse } from "src/app/models/secretariat/patients/responses/employer-response.model";

const baseUrl = "http://localhost:8081/employeurs";

@Injectable({
  providedIn: "root",
})
export class EmployerService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<EmployerResponse[]> {
    return this.http.get<EmployerResponse[]>(baseUrl);
  }
}
