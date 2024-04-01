import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Neighborhood } from "src/app/models/secretariat/patients/neighborhood.model";
import { NeighborhoodResponse } from "src/app/models/secretariat/patients/responses/neighborhood-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "quartiers";

@Injectable({
  providedIn: "root",
})
export class NeighborhoodService {
  neighborhoods: Neighborhood[] = [];
  constructor(private http: HttpClient) {}

  getAll(): Observable<NeighborhoodResponse[]> {
    return this.http.get<NeighborhoodResponse[]>(apiEndpoint).pipe(
      map((neighborhoods) => {
        this.neighborhoods = neighborhoods;

        return neighborhoods;
      })
    );
  }
}
