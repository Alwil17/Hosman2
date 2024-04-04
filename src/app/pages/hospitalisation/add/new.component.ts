import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { NgbModal, ModalDismissReasons } from "@ng-bootstrap/ng-bootstrap";
import { HospitalisationStore } from "@stores/hospitalisation";
import * as moment from "moment";
import { Subject, Subscription, map, pairwise, takeUntil } from "rxjs";
import { hasStateChanges } from "src/app/helpers/utils";

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
  suivis: any[] = [];
  suiviHasTodayRoom: boolean = false;

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
        this.hospitalisationStore.clearHospitalisation()
        this.hospitalisationStore.fetchConsultation(this.consultation_id);
      } else if (hospitalisation_id !== undefined) {
        this.hospitalisationStore.fetchHospitalisation(hospitalisation_id);
      }
    });

    this.subscription = this.hospitalisationStore.stateChanged
      .pipe(pairwise())
      .subscribe(([previous, current]) => {
        if (
          hasStateChanges(
            this.hospitalisation,
            previous.hospitalisation,
            current.hospitalisation
          )
        ) {
          this.hospitalisation = current.hospitalisation;
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

        if (hasStateChanges(this.suivis, previous.suivis, current.suivis)) {
          this.suivis = current.suivis;
        }
        
        if (this.suivis !== undefined && this.suivis !== null && this.hospitalisation !== null) {
          this.getAutoAddRoom();
        }
      });
  }

  getAutoAddRoom() {
    if (this.suiviHasTodayRoom === false) {
      const r = this.suivis.filter(
        (s) => s.type === "chambres" || s.type === "lits"
      );

      // search for today room
      let date = new Date(this.hospitalisation["date_hospit"]);
      let currentDate = new Date();
      let days = Math.floor(
        (currentDate.getTime() - date.getTime()) / 1000 / 60 / 60 / 24
      );
      // let r = moment(this.hospitalisation["date_hospit"]);
      for (let i = 0; i <= days; i++) {
        let day = moment(date).add(i, "days");
        const ch = r.find(
          (t) =>
            t["type"] === "chambres" &&
            moment(day.format("YYYY-MM-DD[T]00:00:00")).isSame(
              moment(t["apply_date"])
            )
        );
        const li = this.suivis.find(
          (t) =>
            t["type"] === "lits" &&
            moment(day.format("YYYY-MM-DD[T]00:00:00")).isSame(
              moment(t["apply_date"])
            )
        );

        if (ch === undefined && li === undefined) {
          const c = r.find((d) => d.type === "chambres");
          c["apply_date"] = day.format("yyyy-MM-DD[T]00:00:00.000000");
          c["hospit_id"] = this.hospitalisation.id;
          delete c.id;
          delete c.created_at;
          delete c.updated_at;
          delete c.extras;

          const l = r.find((d) => d.type === "lits");
          l["apply_date"] = day.format("yyyy-MM-DD[T]00:00:00.000000");
          l["hospit_id"] = this.hospitalisation.id;
          delete l.id;
          delete l.created_at;
          delete l.updated_at;
          delete l.extras;

          this.hospitalisationStore.commitSuivi(c);
          this.hospitalisationStore.commitSuivi(l);
          this.suivis.push(c)
          this.suivis.push(l)
        }

        this.suiviHasTodayRoom = true;

        // const day = moment();
      }
    }
  }

  selectPage(name: string) {
    this.hospitalisationStore.changePage(name)
  }
}
