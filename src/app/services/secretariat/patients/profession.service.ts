import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Profession } from "src/app/models/secretariat/patients/profession.model";
import { ProfessionResponse } from "src/app/models/secretariat/patients/responses/profession-response.model";

const baseUrl = "http://localhost:8081/professions";

@Injectable({
  providedIn: "root",
})
export class ProfessionService {
  professions: Profession[] = [];

  constructor(private http: HttpClient) {}

  getAll(): Observable<ProfessionResponse[]> {
    return this.http.get<ProfessionResponse[]>(baseUrl).pipe(
      map((professions) => {
        this.professions = professions;

        return professions;
      })
    );
  }
}
