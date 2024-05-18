import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Delegate } from "src/app/models/medical-base/submodules/medicines-prescriptions/delegate.model";
import { DelegateRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/delegate-request.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base + "delegues";

@Injectable({
  providedIn: "root",
})
export class DelegateService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Delegate[]> {
    return this.http.get<Delegate[]>(apiEndpoint);
  }

  create(data: DelegateRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  get(id: any): Observable<Delegate> {
    return this.http.get<Delegate>(`${apiEndpoint}/${id}`);
  }

  update(id: any, data: DelegateRequest): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}`, data);
  }

  delete(id: any): Observable<void> {
    return this.http.delete<void>(`${apiEndpoint}/${id}`);
  }
}
