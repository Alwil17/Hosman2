import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { ActGroup } from "src/app/models/secretariat/shared/act-group.model";
import { ActGroupResponse } from "src/app/models/secretariat/shared/responses/act-group-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "groupes";

@Injectable({
  providedIn: "root",
})
export class ActGroupService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<ActGroup[]> {
    return this.http.get<ActGroupResponse[]>(apiEndpoint).pipe(
      map((actGroups) => {
        const mapped: ActGroup[] = actGroups.map((actGroup) =>
          ActGroup.fromResponse(actGroup)
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
