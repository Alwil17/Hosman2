import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { ChronicDisease } from "src/app/models/secretariat/patients/chronic-disease.model";
import { ChronicDiseaseResponse } from "src/app/models/secretariat/patients/responses/chronic-disease-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base; //+ "patients";

// type Intervention = {
//   reference?: string;
//   date_intervention?: Date;
//   type?: string;
//   commentaire?: string;
//   hdm?: string;
//   patient_id?: number;
//   secteur_id?: number;
//   attente_id?: number;
//   constante?: {
//     poids?: number;
//     taille?: number;
//     tension?: number;
//     temperature?: number;
//     poul?: number;
//     perimetre_cranien?: number;
//   };
//   motifs?: [
//     {
//       intervention_id?: number;
//       motif_id?: number;
//     }
//   ];
//   diagnostics?: [
//     {
//       intervention_id?: number;
//       diagnostic_id?: number;
//     }
//   ];
// };

// type Motif = {
//   id: number;
//   libelle: string;
//   slug: string;
// };

// type Diagnostic = {
//   id: number;
//   code: string;
//   libelle: string;
//   slug: string;
// };

@Injectable({
  providedIn: "root",
})
export class MbService {
  constructor(private http: HttpClient) {}

  // create(data: Intervention): Observable<any> {
  //   return this.http.post(apiEndpoint + "interventions", data);
  // }

  // getAllChronicDiseases(): Observable<ChronicDisease[]> {
  //   return this.http.get<ChronicDiseaseResponse[]>(apiEndpoint + "motifs").pipe(
  //     map((motifs) => {
  //       this.motifs = motifs;

  //     })
  //   );
  // }

  // getAllMotifs(): Observable<Motif[]> {
  //   return this.http.get<Motif[]>(apiEndpoint + "motifs").pipe(
  //     map((motifs) => {
  //       // this.motifs = motifs;

  //       return motifs;
  //     })
  //   );
  // }

  // getAllDiagnostics(): Observable<Diagnostic[]> {
  //   return this.http.get<Diagnostic[]>(apiEndpoint + "diagnostics").pipe(
  //     map((diagnostics) => {
  //       // this.diagnostics = diagnostics;

  //       return diagnostics;
  //     })
  //   );
  // }
}
