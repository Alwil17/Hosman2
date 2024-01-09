import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { HospitalisationStore } from "@stores/hospitalisation";
import { Subject, Subscription, map, takeUntil } from "rxjs";

@Component({
  selector: "app-beds",
  templateUrl: "./new.component.html",
  styleUrls: ["./new.component.scss"],
})
export class HospitHomeComponent implements OnInit {
  subscription: Subscription | undefined;
  consultation$: any = null;
  consultation_id: number = -1;
  hospitalisation: any | null = null;

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.hospitalisationStore.fetchSector();
      this.hospitalisationStore.fetchChambres();
      this.hospitalisationStore.fetchTabs();

      this.consultation_id = params["consultation"];
      const hospitalisation_id = params["id"];
      if (this.consultation_id !== undefined) {
        this.hospitalisationStore.fetchConsultation(this.consultation_id);
      } else if (hospitalisation_id !== undefined) {
        this.hospitalisationStore.fetchHospitalisation(hospitalisation_id);
      }
    });

    this.subscription = this.hospitalisationStore.stateChanged.subscribe(
      (state) => {
        if (state) {
          this.hospitalisation = state.hospitalisation;
          if (this.hospitalisation !== null) {
            // show fiches
            const admissionSection = document.querySelector(
              "#accordion-admission"
            );
            const fichesSection = document.querySelector("#accordion-fiches");

            admissionSection
              ?.querySelector("button")
              ?.classList.remove("collapsed");
            admissionSection
              ?.querySelector(".accordion-collapse")
              ?.classList.remove("show");

            fichesSection?.querySelector("button")?.classList.add("collapsed");
            fichesSection
              ?.querySelector(".accordion-collapse")
              ?.classList.add("show");
          }
        }
      }
    );
  }
}
