import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Debt } from "src/app/models/secretariat/patients/debt.model";
import { DebtRequest, DebtSettlingRequest } from "src/app/models/secretariat/patients/requests/debt-request.model";
import { DebtResponse } from "src/app/models/secretariat/patients/responses/debt-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "creances";

@Injectable({
  providedIn: "root",
})
export class DebtService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Debt[]> {
    return this.http
      .get<DebtResponse[]>(apiEndpoint)
      .pipe(map((debts) => debts.map((debt) => Debt.fromResponse(debt))));
  }

  searchBy(criteria: {
    minDate: Date;
    maxDate?: Date;
    searchTerm?: string;
    criterion?: string;
  }): Observable<Debt[]> {
    let apiComplementary =
      "datemin=" + criteria.minDate.toLocaleDateString("fr-ca");

    if (criteria.maxDate) {
      apiComplementary +=
        "&datemax=" + criteria.maxDate.toLocaleDateString("fr-ca");
    }

    if (criteria.searchTerm && criteria.criterion) {
      if (criteria.criterion == "fullname") {
        apiComplementary += "&nom=";
      } else if (criteria.criterion == "reference") {
        apiComplementary += "&reference=";
      }

      apiComplementary += criteria.searchTerm;
    }

    // console.log(
    //   JSON.stringify(criteria, null, 2) +
    //     `${apiEndpoint}/search?${apiComplementary}`
    // );

    return this.http
      .get<DebtResponse[]>(`${apiEndpoint}/search?${apiComplementary}`)
      .pipe(
        map((debts) => {
          const mapped: Debt[] = debts.map((debt) => Debt.fromResponse(debt));

          return mapped;
        })
      );
  }

  settle(id: any, data: DebtSettlingRequest): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}/sold`, data);
  }
}
