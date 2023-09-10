import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { City } from "src/app/models/secretariat/patients/city.model";
import { CityResponse } from "src/app/models/secretariat/patients/responses/city-response.model";

const baseUrl = "http://localhost:8081/villes";

@Injectable({
  providedIn: "root",
})
export class CityService {
  cities: City[] = [];

  constructor(private http: HttpClient) {}

  getAll(): Observable<CityResponse[]> {
    return this.http.get<CityResponse[]>(baseUrl).pipe(
      map((cities) => {
        this.cities = cities;

        return cities;
      })
    );
  }
}
