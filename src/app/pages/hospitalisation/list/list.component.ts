import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { HospitalisationStore } from "@stores/hospitalisation";
import { BehaviorSubject, Observable, Subscription, pairwise } from "rxjs";
import { Chambre } from "src/app/models/hospitalisation/chambre";
import { Lit } from "src/app/models/hospitalisation/lit";
import { calculateExactAge } from "src/app/helpers/age-calculator";
import * as moment from "moment";
import { Router } from "@angular/router";
import { FormControl } from "@angular/forms";
import { hasStateChanges } from "src/app/helpers/utils";
import { Cim11Component } from "src/app/components/cim11/cim11.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "app-list",
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
})
export class ListComponent implements OnInit {
  subscription: Subscription | undefined;
  chambres: Chambre[] = [];
  hospitalises: any[] = [];
  sectors: any[] = [];
  list: any[] = [];
  extras: any[] = [];
  showList = new FormControl(false);
  filtered_list: any[] = [];
  search = new FormControl();

  applyFilter = "nom,prenoms,reference";

  constructor(
    private http: HttpClient,
    private hospitalisationStore: HospitalisationStore,
    private router: Router,
    private modalService: NgbModal,
  ) {
    this.search.valueChanges.subscribe((value) => {
      this.doFilter(value);
    });
  }

  ngOnInit(): void {
    
    this.subscription = this.hospitalisationStore.stateChanged
      .pipe(pairwise())
      .subscribe(([previous, current]) => {

        if (
            hasStateChanges(
                this.chambres,
                previous.chambres,
                current.chambres
            )
        ) {
          this.chambres = current.chambres?.filter(
              (chs: any) => chs.lits.length > 0
          );
        }

        if (
            hasStateChanges(this.sectors, previous.sectors, current.sectors)
        ) {
          this.sectors = current.sectors;
          console.log(this.sectors)
        }


        if ( this.sectors && this.chambres && hasStateChanges(this.list, previous.list, current.list)) {
          if (current.list !== null) {
            const pendingHospits = current.list.filter((l:any) => l.status === 0)
            for (let step = 0; step < pendingHospits.length; step++) {
              this.extras.push({
                extra: JSON.parse(pendingHospits[step].extras),
                hospit_id: pendingHospits[step].id,
                date_hospit: pendingHospits[step].date_hospit,
              });
            }

            this.list = current.list;

            this.hospitalises = [];

            if (
              this.list &&
              this.list.length > 0 &&
              this.sectors.length > 0 &&
              this.chambres.length > 0
            ) {
              this.doFilter("")
            }
          }
        }
      });

    this.hospitalisationStore.fetchSector();
    this.hospitalisationStore.fetchChambres();
    this.hospitalisationStore.fetchHospitalisationList();
  }

  stringToColor(color: string) {
    switch (color) {
      case "bleu":
        return "#005CB6";
      default:
        return "";
    }
  }

  doFilter(text: string): void {
    try {
      if (text.length === 0) {
        this.filtered_list = this.listData(this.list);
      } else {
        let temp_list: any = this.list;

        const w = temp_list.filter((object: any) => {
          let result = "";

          for (const key of this.applyFilter.split(",")) {
            let rowValue = "";

            const patient = JSON.parse(object.extras).patient;
            if (patient !== undefined) {
              rowValue =
                patient[key] !== null && patient[key.trim()] !== undefined
                  ? patient[key.trim()].toString()
                  : "";

              if (
                rowValue !== undefined &&
                rowValue.toLowerCase().includes(text.trim().toLowerCase())
              ) {
                result = object;
              }
            }
          }
          return result;
        });
        this.filtered_list = this.listData(w);
      }
    } catch (e) {
      console.log("filtering : " + e);
    }
  }

  listData(list: any[]) {
    return list.filter((l) => l.status === 0).map((data) => {
      const h = data;
      const d = JSON.parse(h.extras);
      const c = this.chambres.find((c) => c.id === d.chambre);
      const w = moment(h.date_hospit);
      const n = moment(d.patient.date_naissance);

      let secteur: any = "";
      if (this.sectors !== null && this.sectors.length > 0) {
        secteur = this.sectors.find((s: any) => s.code === h.secteur_code);
      }

      return {
        id: h.id,
        patient: d.patient,
        reference: d.patient.reference,
        nom: d.patient.nom,
        prenoms: d.patient.prenoms,
        chambre: c,
        lit: c!.lits.find((l) => l.id === d.lit),
        hospit_date: w.format("DD/MM/yyyy"),
        date_naiss: n.format("DD/MM/yyyy"),
        rowcolor: this.stringToColor(secteur.couleur),
      };
    });
  }

  getPatientName(chambre: Number, lit: Number) {
    const value = this.extras.find(
      (e) => e.extra.chambre === chambre && e.extra.lit === lit
    );

    if (value !== undefined) {
      return value.extra.patient.nom + " " + value.extra.patient.prenoms;
    } else {
      return null;
    }
  }

  getPatientAge(chambre: Number, lit: Number) {
    const value = this.extras.find(
      (e) => e.extra.chambre === chambre && e.extra.lit === lit
    );

    if (value !== undefined) {
      return calculateExactAge(new Date(value.extra.patient.date_naissance));
    } else {
      return "Lit non occupé";
    }
  }

  getDateHospit(chambre: Number, lit: Number) {
    const value = this.extras.find(
      (e) => e.extra.chambre === chambre && e.extra.lit === lit
    );

    if (value !== undefined) {
      const d = moment(value.date_hospit);
      return d.format("DD/MM/yyyy");
    } else {
      return null;
    }
  }

  openHospit(chambre: Number, lit: Number) {
    const value = this.extras.find(
      (e) => e.extra.chambre === chambre && e.extra.lit === lit
    );
    if (value !== undefined) {
      this.router.navigate(["/hospitalisation/edit"], {
        queryParams: { id: value.hospit_id },
      });
    }
  }

  openHospitById(id: Number) {
    console.log(id);
    this.router.navigate(["/hospitalisation/edit"], {
      queryParams: { id: id },
    });
  }
}
