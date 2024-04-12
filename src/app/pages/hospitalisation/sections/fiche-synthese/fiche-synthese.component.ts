import { Component, OnInit } from "@angular/core";
import { HospitalisationStore } from "@stores/hospitalisation";
import { pairwise, Subscription } from "rxjs";
import { hasStateChanges } from "src/app/helpers/utils";
import { filter } from "rxjs/operators";

@Component({
  selector: "app-fiche-synthese",
  templateUrl: "./fiche-synthese.component.html",
  styleUrls: ["./fiche-synthese.component.scss"],
})
export class FicheSyntheseComponent implements OnInit {
  subscription: Subscription | undefined;
  patient: any = null
  tabs: any[] = [];
  suivis: any[] = [];
  list: any[] = [];

  constructor(private hospitalisationStore: HospitalisationStore) {}

  ngOnInit(): void {
    this.subscription = this.hospitalisationStore.stateChanged
      .pipe(pairwise())
      .subscribe(([previous, current]) => {
        if (hasStateChanges(this.tabs, previous.tabs, current.tabs)) {
          this.tabs = current.tabs;
        }

        if (hasStateChanges(this.patient, previous.patient, current.patient)) {
          this.patient = current.patient;
        }

        if (hasStateChanges(this.suivis, previous.suivis, current.suivis)) {
          if (current.suivis)
          this.suivis = current.suivis.filter(
            (s: any) => s.type !== "watches" && s.type !== "evolution" && s.type !== "lits"
          );
        }

        if (current.currentPage === 'fiche-synthese') {
          this.populate()
        }
      });
  }

  populate() {
    this.list = [];
    
    this.tabs.forEach((t) => {
      const v: any = this.suivis.filter((s) => s.type === t.type);
      if (v.length > 0) {
        const a = {
          num : "",
          name: t.name,
          qte: "",
          pu: "",
          total: "",
          categ: 1
        };

        this.list.push(a);

        t.data.forEach((td: any) => {
          const g = v.filter((s: any) => s.type_id === td.id);
          if (g.length > 0) {
            // console.log(td);

            const qteTotal = g.reduce((total : any, item : any) => total + item.qte, 0);
            // console.log(qteTotal);
            let label = td.nom_officiel ?? td.nom; 
            if (label === undefined ||label === null || label === "") label = td.libelle

            // console.log(label);
            this.list.push({
              categ: 0,
              num : td.id,
              name: label,
              qte: qteTotal,
              pu: td.prix,
              total: td.prix * qteTotal,
            });
          }
        });
      }
    });

  }
}
