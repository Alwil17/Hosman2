import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base + "diagnostics";

type Diagnostic = {
  id: number;
  code: string;
  libelle: string;
  slug: string;
};

@Injectable({
  providedIn: "root",
})
export class DiagnosticService {
  constructor(private http: HttpClient) {}

  getAllDiagnostics(): Observable<Diagnostic[]> {
    return this.http.get<Diagnostic[]>(apiEndpoint).pipe(
      map((diagnostics) => {
        // this.diagnostics = diagnostics;

        return diagnostics;
      })
    );
  }
}
