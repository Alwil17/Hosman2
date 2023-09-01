import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";

const baseUrl = "http://localhost:8081/factures";

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

  create(data: WaitingListItem, 
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
  ) {
    this.waitingList = [...this.waitingList, data];


    // this.http.post(baseUrl, data2)
    
  }

  // getAll(): Observable<Patient[]> {
  //   return this.http.get<Patient[]>(baseUrl);
  // }

  // get(id: any): Observable<Patient> {
  //   return this.http.get(`${baseUrl}/${id}`);
  // }

  // create(data: any): Observable<any> {
  //   return this.http.post(baseUrl, data);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${baseUrl}/${id}`, data);
  // }
}
