import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { SectorRequest } from "src/app/models/secretariat/shared/requests/sector-request.model";
import { SectorResponse } from "src/app/models/secretariat/shared/responses/sector-response.model";
import { Sector } from "src/app/models/secretariat/shared/sector.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "secteurs";

@Injectable({
  providedIn: "root",
})
export class SectorService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Sector[]> {
    return this.http.get<SectorResponse[]>(apiEndpoint).pipe(
      map((sectors) => {
        const mapped: Sector[] = sectors.map((sector) =>
          Sector.fromResponse(sector)
        );

        return mapped;
      })
    );
  }

  // create(data: SectorRequest): Observable<any> {
  //   return this.http.post(apiEndpoint, data);
  // }

  // get(id: any): Observable<Expense> {
  //   return this.http.get(`${baseUrl}/${id}`);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${baseUrl}/${id}`, data);
  // }
}
