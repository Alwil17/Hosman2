import { Component, OnInit } from "@angular/core";
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

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
