import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PrescriptionListRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/prescription-list-request.model";
import { environment } from "src/environments/environment";

const prescriptionListEndpoint = environment.medical_base + "ordonnances";
const prescriptionEndpoint = environment.medical_base + "prescriptions";

@Injectable({
  providedIn: "root",
})
export class PrescriberService {
  constructor(private http: HttpClient) {}

  registerPrescriptions(data: PrescriptionListRequest): Observable<any> {
    return this.http.post(prescriptionListEndpoint, data);
  }

  // getAll(): Observable<Product[]> {
  //   return this.http.get<Product[]>(apiEndpoint);
  // }

  // create(data: ProductRequest): Observable<any> {
  //   return this.http.post(apiEndpoint, data);
  // }

  // get(id: any): Observable<Product> {
  //   return this.http.get<Product>(`${apiEndpoint}/${id}`);
  // }

  // update(id: any, data: ProductRequest): Observable<any> {
  //   return this.http.put(`${apiEndpoint}/${id}`, data);
  // }

  // delete(id: any): Observable<void> {
  //   return this.http.delete<void>(`${apiEndpoint}/${id}`);
  // }
}
