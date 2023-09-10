import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Invoice } from "src/app/models/secretariat/patients/invoice.model";
import { InvoiceRequest } from "src/app/models/secretariat/patients/requests/invoice-request.model";
import { InvoiceResponse } from "src/app/models/secretariat/patients/responses/invoice-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "factures";

@Injectable({
  providedIn: "root",
})
export class InvoiceService {
  constructor(private http: HttpClient) {}

  // getAll(): Observable<Invoice[]> {
  //   return this.http.get<InvoiceResponse[]>(apiEndpoint).pipe(
  //     map((invoices) => {
  //       const mapped: Invoice[] = invoices.map((invoice) =>
  //         Invoice.fromResponse(invoice)
  //       );

  //       return mapped;
  //     })
  //   );
  // }

  create(data: InvoiceRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  get(id: any): Observable<Invoice> {
    return this.http.get<Invoice>(`${apiEndpoint}/${id}/show`);
  }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${apiEndpoint}/${id}`, data);
  // }
}
