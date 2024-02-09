import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TherapeuticClassRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/therapeutic-class-request.model";
import { TherapeuticClass } from "src/app/models/medical-base/submodules/medicines-prescriptions/therapeutic-class.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base + "classes";

@Injectable({
  providedIn: "root",
})
export class TherapeuticClassService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<TherapeuticClass[]> {
    return this.http.get<TherapeuticClass[]>(apiEndpoint);
  }

  create(data: TherapeuticClassRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  get(id: any): Observable<TherapeuticClass> {
    return this.http.get<TherapeuticClass>(`${apiEndpoint}/${id}`);
  }

  update(id: any, data: TherapeuticClassRequest): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}`, data);
  }

  delete(id: any): Observable<void> {
    return this.http.delete<void>(`${apiEndpoint}/${id}`);
  }
}
