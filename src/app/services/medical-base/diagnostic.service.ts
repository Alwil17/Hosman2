import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { Diagnostic } from "src/app/models/medical-base/diagnostic.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base + "diagnostics";

@Injectable({
  providedIn: "root",
})
export class DiagnosticService {
  constructor(private http: HttpClient) {}

  // getAll(): Observable<Diagnostic[]> {
  //   return this.http.get<Diagnostic[]>(apiEndpoint).pipe(
  //     map((diagnostics) => {
  //       // this.diagnostics = diagnostics;

  //       return diagnostics;
  //     })
  //   );
  // }

  search(searchTerm: string): Observable<Diagnostic[]> {
    return this.http.get<Diagnostic[]>(
      apiEndpoint + "/search?libelle=" + searchTerm
    );
  }
}
