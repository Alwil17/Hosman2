import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { TariffResponse } from "src/app/models/secretariat/shared/responses/tariff-response.model";
import { Tariff } from "src/app/models/secretariat/shared/tariff.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "medecins";

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
