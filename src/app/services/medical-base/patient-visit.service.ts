import { Injectable } from "@angular/core";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";

@Injectable({
  providedIn: "root",
})
export class PatientVisitService {
  private _selectedWaitingListItem?: WaitingListItem;

  constructor() {}

  selectWaitingListItem(waitingListItem: WaitingListItem) {
    this._selectedWaitingListItem = waitingListItem;
  }

  public get selectedWaitingListItem(): WaitingListItem | undefined {
    return this._selectedWaitingListItem;
  }
}
