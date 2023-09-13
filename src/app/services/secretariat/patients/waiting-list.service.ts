import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { tap } from "rxjs/operators";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "";

@Injectable({
  providedIn: "root",
})
export class WaitingListService {
  private waitingList: WaitingListItem[] = [
    // new WaitingListItem(
    //   1,
    //   "PAT20",
    //   "LAWSON",
    //   "Laté Armel-Gabriel",
    //   new Date("1998-3-31"),
    //   "Masculin",
    //   ["C", "CS", "Etc."],
    //   10000,
    //   "Cardiologie",
    //   "Dr. Whatever",
    //   new Date()
    // ),
  ];

  constructor(private http: HttpClient) {}

  getAll() {
    return [...this.waitingList];
  }

  create(
    data: WaitingListItem
    // data2?: {
    //   reference: string;
    //   total: number;
    //   montant_pec: number;
    //   majoration: {
    //     montant: number;
    //     motif: string;
    //   };
    //   reduction: {
    //     montant: number;
    //     motif: string;
    //   };
    //   a_payer: number;
    //   reliquat: {
    //     montant: number;
    //     etat_id: number;
    //   };
    //   creance: {
    //     montant: number;
    //     etat_id: number;
    //   };
    //   date_facture: Date;
    //   date_reglement: Date;
    //   etat_id: number;
    //   exporte: number;
    //   mode_payements: [
    //     {
    //       facture_id: number;
    //       mode_payement_id: number;
    //       montant: number;
    //     }
    //   ];
    // }
  ): Observable<any> {
    //   data2?: {
    //   "reference": "string",
    //   "total": 12000,
    //   "montant_pec": 80,
    //   "majoration": 0,
    //   "reduction": 0,
    //   "a_payer": 0,
    //   "verse": 0,
    //   "reliquat": 0,
    //   "creance": 0,
    //   "mode_payement": "string",
    //   "date_facture": "2023-08-26T19:22:17.704Z",
    //   "date_reglement": "2023-08-26T19:22:17.704Z",
    //   "etat_id": 0,
    //   "exporte": 0
    // }
    this.waitingList = [...this.waitingList, data];

    return of(true);
    // return this.http.post(baseUrl, data2);
  }

  // getAll(): Observable<Patient[]> {
  //   return this.http.get<Patient[]>(baseUrl);
  // }

  // get(id: any): Observable<any> {
  //   return this.http.get(`${baseUrl}/${id}`);
  // }

  // create(data: any): Observable<any> {
  //   return this.http.post(baseUrl, data);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${baseUrl}/${id}`, data);
  // }
}
