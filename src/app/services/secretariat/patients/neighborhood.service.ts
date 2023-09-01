import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { NeighborhoodResponse } from "src/app/models/secretariat/patients/responses/neighborhood-response.model";

const baseUrl = "http://localhost:8081/quartiers";

@Injectable({
  providedIn: "root",
})
export class NeighborhoodService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<NeighborhoodResponse[]> {
    return this.http.get<NeighborhoodResponse[]>(baseUrl);
  }
}
