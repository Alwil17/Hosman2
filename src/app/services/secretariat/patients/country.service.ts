import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { delay, map } from "rxjs/operators";
import { Country } from "src/app/models/secretariat/patients/country.model";
import { CountryResponse } from "src/app/models/secretariat/patients/responses/country-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "pays";

@Injectable({
  providedIn: "root",
})
export class CountryService {
  countries: Country[] = [];

  constructor(private http: HttpClient) {}

  getAll(): Observable<CountryResponse[]> {
    return this.http.get<CountryResponse[]>(apiEndpoint).pipe(
      map((countries) => {
        this.countries = countries;

        return countries;
      })
    );
  }
}
