import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { InsuranceDebt } from "src/app/models/secretariat/activities/insurance-debt.model";
import { InsuranceDebtResponse } from "src/app/models/secretariat/activities/responses/insurance-debt-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "factures/pecs";

@Injectable({
  providedIn: "root",
})
export class InsuranceDebtService {
  constructor(private http: HttpClient) {}

  // get(id: any): Observable<InsuranceDebt> {
  //   return this.http.get<InsuranceDebt>(`${apiEndpoint}/${id}`);
  // }

  searchBy(criteria: {
    minDate: Date;
    maxDate?: Date;
    insuranceType?: string;
    insuranceSlug?: string;
  }): Observable<InsuranceDebt[]> {
    let apiComplementary =
      "datemin=" + criteria.minDate.toLocaleDateString("fr-ca");

    if (criteria.maxDate) {
      apiComplementary +=
        "&datemax=" + criteria.maxDate.toLocaleDateString("fr-ca");
    }

    if (criteria.insuranceType) {
      apiComplementary += "&type=" + criteria.insuranceType;
    }

    if (criteria.insuranceSlug) {
      apiComplementary += "&slug=" + criteria.insuranceSlug;
    }

    return this.http
      .get<InsuranceDebtResponse[]>(`${apiEndpoint}/search?${apiComplementary}`)
      .pipe(
        map((insuranceDebts) => {
          const mapped: InsuranceDebt[] = insuranceDebts.map((insuranceDebt) =>
            InsuranceDebt.fromResponse(insuranceDebt)
          );

          return mapped;
        })
      );
  }

  loadPdf(criteria: {
    minDate: Date;
    maxDate?: Date;
    insuranceType?: string;
    insuranceSlug?: string;
  }): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set("Accept", "application/pdf");

    let apiComplementary =
      "datemin=" + criteria.minDate.toLocaleDateString("fr-ca");

    if (criteria.maxDate) {
      apiComplementary +=
        "&datemax=" + criteria.maxDate.toLocaleDateString("fr-ca");
    }

    if (criteria.insuranceType) {
      apiComplementary += "&type=" + criteria.insuranceType;
    }

    if (criteria.insuranceSlug) {
      apiComplementary += "&slug=" + criteria.insuranceSlug;
    }

    return this.http.post(`${apiEndpoint}/search?${apiComplementary}`, {
      headers: headers,
      responseType: "blob",
    });
  }
}
