import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { InsuranceType } from "src/app/models/secretariat/patients/insurance-type.model";
import { InsuranceTypeResponse } from "src/app/models/secretariat/patients/responses/insurance-type-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "typeAssurances";

@Injectable({
  providedIn: "root",
})
export class InsuranceTypeService {
  insuranceTypes: InsuranceType[] = [];

  constructor(private http: HttpClient) {}

  getAll(): Observable<InsuranceTypeResponse[]> {
    return this.http.get<InsuranceTypeResponse[]>(apiEndpoint).pipe(
      map((insuranceTypes) => {
        this.insuranceTypes = insuranceTypes;

        return insuranceTypes;
      })
    );
  }
}
