import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "caisse";

@Injectable({
  providedIn: "root",
})
export class CheckoutService {
  constructor(private http: HttpClient) {}

  loadPdf(): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set("Accept", "application/pdf");

    return this.http.get(`${apiEndpoint}/report`, {
      headers: headers,
      responseType: "blob",
    });
  }
}
