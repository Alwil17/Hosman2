import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CityResponse } from "src/app/models/secretariat/patients/responses/city-response.model";

const baseUrl = "http://localhost:8081/villes";

@Injectable({
  providedIn: "root",
})
export class CityService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<CityResponse[]> {
    return this.http.get<CityResponse[]>(baseUrl);
  }
}
