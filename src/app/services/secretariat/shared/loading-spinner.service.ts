import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class LoadingSpinnerService {
  // Used to determine number of ongoing request
  // As long as the count is not equal to 0, the loading spinner will be displayed unless forcibly hidden (by a component)
  pendingRequestsCount = 0;

  // Loading spinner visibility
  visibility: BehaviorSubject<boolean>;

  // Used to override loading spinner visibility
  isLoadingSpinnerDisplayed = true;

  constructor() {
    this.visibility = new BehaviorSubject<boolean>(false);
  }

  // Method used to show the global loading spinner (overlay) on UI
  show() {
    this.visibility.next(true);
    this.pendingRequestsCount++;
  }

  // Method used to hide the global loading spinner (overlay) on UI
  hide() {
    this.pendingRequestsCount--;
    if (this.pendingRequestsCount === 0) {
      this.visibility.next(false);
    }
  }

  // Methods used to override loading spinner visibility -----------------------------------------------------------------------------------------
  // Forcibly show loading spinner
  // Is used at interceptor level to reset loading spinner visibilty to default
  showLoadingSpinner() {
    this.isLoadingSpinnerDisplayed = true;
  }

  // Forcibly hide loading spinner
  // Is to be used at component level (normally)
  hideLoadingSpinner() {
    this.isLoadingSpinnerDisplayed = false;
  }
  // END ------------------------------------------------------------------------------------------------------------------------------------------
}
