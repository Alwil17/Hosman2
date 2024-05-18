import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Laboratory } from "src/app/models/medical-base/submodules/medicines-prescriptions/laboratory.model";
import { LaboratoryRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/laboratory-request.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base + "laboratoires";

@Injectable({
  providedIn: "root",
})
export class LaboratoryService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Laboratory[]> {
    return this.http.get<Laboratory[]>(apiEndpoint);
  }

  create(data: LaboratoryRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  get(id: any): Observable<Laboratory> {
    return this.http.get<Laboratory>(`${apiEndpoint}/${id}`);
  }

  update(id: any, data: LaboratoryRequest): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}`, data);
  }

  delete(id: any): Observable<void> {
    return this.http.delete<void>(`${apiEndpoint}/${id}`);
  }
}
