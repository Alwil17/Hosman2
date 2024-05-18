import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { PaymentMode } from "src/app/models/secretariat/patients/payment-mode.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "mode-payements";

@Injectable({
  providedIn: "root",
})
export class PaymentModeService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<PaymentMode[]> {
    return this.http.get<PaymentMode[]>(apiEndpoint);
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
