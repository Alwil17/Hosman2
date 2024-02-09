import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Agency } from "src/app/models/medical-base/submodules/medicines-prescriptions/agency.model";
import { AgencyRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/agency-request.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base + "agences";

@Injectable({
  providedIn: "root",
})
export class AgencyService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Agency[]> {
    return this.http.get<Agency[]>(apiEndpoint);
  }

  create(data: AgencyRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  get(id: any): Observable<Agency> {
    return this.http.get<Agency>(`${apiEndpoint}/${id}`);
  }

  update(id: any, data: AgencyRequest): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}`, data);
  }

  delete(id: any): Observable<void> {
    return this.http.delete<void>(`${apiEndpoint}/${id}`);
  }
}
