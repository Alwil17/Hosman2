import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { City } from "src/app/models/secretariat/patients/city.model";
import { CityResponse } from "src/app/models/secretariat/patients/responses/city-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "villes";

@Injectable({
  providedIn: "root",
})
export class CityService {
  cities: City[] = [];

  constructor(private http: HttpClient) {}

  getAll(): Observable<CityResponse[]> {
    return this.http.get<CityResponse[]>(apiEndpoint).pipe(
      map((cities) => {
        this.cities = cities;

        return cities;
      })
    );
  }
}
