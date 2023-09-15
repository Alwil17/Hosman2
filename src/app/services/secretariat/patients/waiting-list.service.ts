import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "";

@Injectable({
  providedIn: "root",
})
export class WaitingListService {
  private waitingList: WaitingListItem[] = [
  ];

  constructor(private http: HttpClient) {}

  getAll() {
    return [...this.waitingList];
  }

  create(
    data: WaitingListItem
  ): Observable<any> {
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
