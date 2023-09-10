import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Employer } from "src/app/models/secretariat/patients/employer.model";
import { EmployerResponse } from "src/app/models/secretariat/patients/responses/employer-response.model";

const baseUrl = "http://localhost:8081/employeurs";

@Injectable({
  providedIn: "root",
})
export class EmployerService {
  employers: Employer[] = [];

  constructor(private http: HttpClient) {}

  getAll(): Observable<EmployerResponse[]> {
    return this.http.get<EmployerResponse[]>(baseUrl).pipe(
      map((employers) => {
        this.employers = employers;

        return employers;
      })
    );
  }
}
