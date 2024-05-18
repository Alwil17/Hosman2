import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "caisse";

@Injectable({
  providedIn: "root",
})
export class CheckoutService {
  constructor(private http: HttpClient) {}

  loadPdfBy(criteria: {
    isDetailed: boolean;
    minDate: Date;
    maxDate?: Date;
  }): Observable<any> {
    let apiComplementary = "?vue=";

    if (criteria.isDetailed) {
      apiComplementary += "tout";
    } else {
      apiComplementary += "compact";
    }

    if (criteria?.minDate) {
      apiComplementary +=
        "&datemin=" + criteria.minDate.toLocaleDateString("fr-ca");
    }

    if (criteria?.maxDate) {
      apiComplementary +=
        "&datemax=" + criteria.maxDate.toLocaleDateString("fr-ca");
    }

    // if (!criteria?.minDate && !criteria?.maxDate) {
    //   apiComplementary = "";
    // }

    let headers = new HttpHeaders();
    headers = headers.set("Accept", "application/pdf");

    return this.http.get(`${apiEndpoint}/report${apiComplementary}`, {
      headers: headers,
      responseType: "blob",
    });
  }
}
