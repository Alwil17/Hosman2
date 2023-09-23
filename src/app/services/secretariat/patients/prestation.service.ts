import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PrestationRequest } from "src/app/models/secretariat/patients/requests/prestation-request.model";
import { InvoiceResponse } from "src/app/models/secretariat/patients/responses/invoice-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "prestations";

@Injectable({
  providedIn: "root",
})
export class PrestationService {
  constructor(private http: HttpClient) {}

  generatePreInvoiceInfos(
    data: PrestationRequest
  ): Observable<InvoiceResponse> {
    return this.http.post<InvoiceResponse>(apiEndpoint, data);
  }

  // getAll(): Observable<Doctor[]> {
  //   return this.http.get<DoctorResponse[]>(apiEndpoint).pipe(
  //     map((doctors) => {
  //       const mapped: Doctor[] = doctors.map((doctor) =>
  //         Doctor.fromResponse(doctor)
  //       );

  //       return mapped;
  //     })
  //   );
  // }

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
