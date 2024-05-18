import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { Motif } from "src/app/models/medical-base/motif.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base + "motifs";

@Injectable({
  providedIn: "root",
})
export class MotifService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Motif[]> {
    return this.http.get<Motif[]>(apiEndpoint).pipe(
      map((motifs) => {
        // this.motifs = motifs;

        return motifs;
      })
    );
  }

  search(searchTerm: string): Observable<Motif[]> {
    return this.http.get<Motif[]>(
      apiEndpoint + "/search?libelle=" + searchTerm
    );
  }
}
