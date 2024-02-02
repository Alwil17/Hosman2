import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { TariffProFormaRequest } from "src/app/models/secretariat/shared/requests/tariff-pro-forma-request.model";
import { TariffResponse } from "src/app/models/secretariat/shared/responses/tariff-response.model";
import { Tariff } from "src/app/models/secretariat/shared/tariff.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "tarifs";

@Injectable({
  providedIn: "root",
})
export class TariffService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Tariff[]> {
    return this.http.get<TariffResponse[]>(apiEndpoint).pipe(
      map((tariffs) => {
        const mapped: Tariff[] = tariffs.map((tariff) =>
          Tariff.fromResponse(tariff)
        );

        return mapped;
      })
    );
  }

  getByGroupCode(code: string): Observable<Tariff[]> {
    let apiComplementary = "groupe=" + code;

    return this.http
      .get<TariffResponse[]>(`${apiEndpoint}/search?${apiComplementary}`)
      .pipe(
        map((tariffs) => {
          const mapped: Tariff[] = tariffs.map((tariff) =>
            Tariff.fromResponse(tariff)
          );

          return mapped;
        })
      );
  }

  searchBy(criteria: {
    searchTerm: string;
    actGroupCode?: string;
  }): Observable<Tariff[]> {
    let apiComplementary = "acte=" + criteria.searchTerm;

    if (criteria.actGroupCode) {
      apiComplementary =
        "groupe=" + criteria.actGroupCode + "&" + apiComplementary;
    }

    console.log(`${apiEndpoint}/search?${apiComplementary}`);

    return this.http
      .get<TariffResponse[]>(`${apiEndpoint}/search?${apiComplementary}`)
      .pipe(
        map((tariffs) => {
          const mapped: Tariff[] = tariffs.map((tariff) =>
            Tariff.fromResponse(tariff)
          );

          return mapped;
        })
      );
  }

  loadProFormaPdf(tariffProForma: TariffProFormaRequest): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set("Accept", "application/pdf");

    return this.http.post(`${apiEndpoint}/proformat`, tariffProForma, {
      headers: headers,
      responseType: "blob",
    });
  }

  // create(data: DoctorRequest): Observable<any> {
  //   return this.http.post(apiEndpoint, data);
  // }

  // get(id: any): Observable<Expense> {
  //   return this.http.get(`${baseUrl}/${id}`);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${baseUrl}/${id}`, data);
  // }
}
