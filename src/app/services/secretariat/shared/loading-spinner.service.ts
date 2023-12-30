import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class LoadingSpinnerService {
  count = 0;
  visibility: BehaviorSubject<boolean>;

  // Used to override loading spinner visibility
  isLoadingSpinnerDisplayed = true;

  constructor() {
    this.visibility = new BehaviorSubject<boolean>(false);
    // this.visibility.subscribe((value) =>
    //   console.log("Loading spinner visibility : " + value + ' ' + this.count)
    // );
  }

  show() {
    this.visibility.next(this.isLoadingSpinnerDisplayed);
    // this.visibility.next(true);
    this.count++;
  }

  hide() {
    this.count--;
    if (this.count === 0) {
      this.visibility.next(false);
    }
  }

  // Used to override loading spinner visibility -----------------------------------------------------------------------------------------
  showLoadingSpinner() {
    this.isLoadingSpinnerDisplayed = true;
  }

  hideLoadingSpinner() {
    this.isLoadingSpinnerDisplayed = false;
  }
}
