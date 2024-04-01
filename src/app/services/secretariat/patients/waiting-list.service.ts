import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map, of } from "rxjs";
import { WaitingListFilter } from "src/app/models/enums/waiting-list-filter.enum";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "attentes";

@Injectable({
  providedIn: "root",
})
export class WaitingListService {
  private waitingList: WaitingListItem[] = [];

  constructor(private http: HttpClient) {}

  getAll(): Observable<WaitingListItem[]> {
    return this.http.get<WaitingListItem[]>(apiEndpoint);
  }

  filterBy(criteria: {
    view: WaitingListFilter;
    doctorRegistrationNumber?: string;
  }): Observable<WaitingListItem[]> {
    let apiComplementary = "vue=" + criteria.view;

    if (criteria.view === WaitingListFilter.DOCTOR) {
      if (criteria.doctorRegistrationNumber) {
        apiComplementary += "&medecin=" + criteria.doctorRegistrationNumber;
      } else {
        return of([]);
      }
    }

    console.log(`${apiEndpoint}/search?${apiComplementary}`);

    return this.http
      .get<WaitingListItem[]>(`${apiEndpoint}/search?${apiComplementary}`)
      .pipe(
        map((waitingList) => {
          const mapped: WaitingListItem[] = waitingList.map(
            (waitingListItem) => new WaitingListItem(waitingListItem)
          );

          // const mapped = waitingList;

          return mapped;
        })
      );
  }

  delete(id: any): Observable<void> {
    return this.http.delete<void>(`${apiEndpoint}/${id}`);
  }

  // create(data: WaitingListItem): Observable<any> {
  //   this.waitingList = [...this.waitingList, data];

  //   return of(true);
  //   // return this.http.post(baseUrl, data2);
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
