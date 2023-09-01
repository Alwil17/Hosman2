import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { delay } from "rxjs/operators";
import { CountryResponse } from "src/app/models/secretariat/patients/responses/country-response.model";

const baseUrl = "http://localhost:8081/pays";

@Injectable({
  providedIn: "root",
})
export class CountryService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<CountryResponse[]> {
    return this.http.get<CountryResponse[]>(baseUrl);
  }
}
