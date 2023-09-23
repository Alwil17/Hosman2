import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { StatusResponse } from "src/app/models/secretariat/patients/responses/status-response.model";
import { Status } from "src/app/models/secretariat/patients/status.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "etats";

@Injectable({
  providedIn: "root",
})
export class StatusService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Status[]> {
    return this.http.get<StatusResponse[]>(apiEndpoint).pipe(
      map((statuses) => {
        const mapped: Status[] = statuses.map((status) =>
          Status.fromResponse(status)
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
