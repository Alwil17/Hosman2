import { Injectable } from "@angular/core";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";

@Injectable({
  providedIn: "root",
})
export class WaitingListService {
  private waitingList: WaitingListItem[] = [
    new WaitingListItem(
      1,
      "PAT1",
      "LAWSON",
      "Laté Armel-Gabriel",
      new Date("1998-3-31"),
      "Masculin",
      ["C", "CS", "Etc."],
      10000,
      "Cardiologie",
      "Dr. Whatever",
      new Date()
    ),
    new WaitingListItem(
      1,
      "PAT1",
      "LAWSON",
      "Laté Armel-Gabriel",
      new Date("1998-3-31"),
      "Masculin",
      ["C", "CS", "Etc."],
      10000,
      "Cardiologie",
      "Dr. Whatever",
      new Date()
    ),
    new WaitingListItem(
      1,
      "PAT1",
      "LAWSON",
      "Laté Armel-Gabriel",
      new Date("1998-3-31"),
      "Masculin",
      ["C", "CS", "Etc."],
      10000,
      "Cardiologie",
      "Dr. Whatever",
      new Date()
    ),
    new WaitingListItem(
      1,
      "PAT1",
      "LAWSON",
      "Laté Armel-Gabriel",
      new Date("1998-3-31"),
      "Masculin",
      ["C", "CS", "Etc."],
      10000,
      "Cardiologie",
      "Dr. Whatever",
      new Date()
    ),
    new WaitingListItem(
      1,
      "PAT1",
      "LAWSON",
      "Laté Armel-Gabriel",
      new Date("1998-3-31"),
      "Masculin",
      ["C", "CS", "Etc."],
      10000,
      "Cardiologie",
      "Dr. Whatever",
      new Date()
    ),
  ];

  constructor() {}

  getAll() {
    return [...this.waitingList];
  }

  create(data: WaitingListItem) {
    this.waitingList = [...this.waitingList, data];
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
