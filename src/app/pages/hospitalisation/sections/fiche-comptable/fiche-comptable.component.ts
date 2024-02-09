import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { HospitalisationStore } from "@stores/hospitalisation";
import { Subject, Subscription, takeUntil } from "rxjs";

@Component({
  selector: "app-fiche-comptable",
  templateUrl: "./fiche-comptable.component.html",
  styleUrls: ["./fiche-comptable.component.scss"],
})
export class FicheComptableComponent implements OnInit {
  subscription: Subscription | undefined;
  tabs: any[] = [];
  search = new FormControl();

  constructor(private hospitalisationStore: HospitalisationStore) {}

  ngOnInit(): void {
    this.subscription = this.hospitalisationStore.stateChanged.subscribe(
      (state) => {
        if (state) {
          this.tabs = state.tabs;
        }
      }
    );

  }

  filterInAll(){
    this.hospitalisationStore.doFilterTabs(this.search.value)
  }

  clearSearch(){
    this.search.setValue('')
    this.hospitalisationStore.clearTabsFilter()
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
