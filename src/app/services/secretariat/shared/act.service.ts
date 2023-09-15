import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { Act } from "src/app/models/secretariat/shared/act.model";
import { ActResponse } from "src/app/models/secretariat/shared/responses/act-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "medecins";

@Injectable({
  providedIn: "root",
})
export class ActService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Act[]> {
    return this.http.get<ActResponse[]>(apiEndpoint).pipe(
      map((acts) => {
        const mapped: Act[] = acts.map((act) => Act.fromResponse(act));

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
