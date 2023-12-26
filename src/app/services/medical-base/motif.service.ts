import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base + "motifs";

type Motif = {
  id: number;
  libelle: string;
  slug: string;
};

@Injectable({
  providedIn: "root",
})
export class MotifService {
  constructor(private http: HttpClient) {}

  getAllMotifs(): Observable<Motif[]> {
    return this.http.get<Motif[]>(apiEndpoint).pipe(
      map((motifs) => {
        // this.motifs = motifs;

        return motifs;
      })
    );
  }
}
