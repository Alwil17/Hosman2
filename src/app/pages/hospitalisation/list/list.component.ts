import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { HospitalisationStore } from "@stores/hospitalisation";
import { BehaviorSubject, Observable, Subscription, pairwise } from "rxjs";
import { Chambre } from "src/app/models/hospitalisation/chambre";
import { Lit } from "src/app/models/hospitalisation/lit";
import { calculateExactAge } from "src/app/helpers/age-calculator";
import * as moment from "moment";
import { Router } from '@angular/router';

const hospitalisationEndpoint = "/api/hospits";

@Component({
  selector: "app-list",
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
})
export class ListComponent implements OnInit {
  subscription: Subscription | undefined;
  chambres: Chambre[] = [];
  hospitalises: any[] = [];
  list: any[] = [];
  extras: any[] = [];

  constructor(
    private http: HttpClient,
    private hospitalisationStore: HospitalisationStore,
    private router: Router
  ) {}

  ngOnInit(): void {
    console.log("INIT")
    this.subscription = this.hospitalisationStore.stateChanged
      .pipe(pairwise())
      .subscribe(([previous, current]) => {
        if (
          this.list.length === 0 || JSON.stringify(previous.chambres) !== JSON.stringify(current.chambres)
        )
          this.chambres = current.chambres?.filter(
            (chs: any) => chs.lits.length > 0
          );
        if (this.list.length == 0 || JSON.stringify(previous.list) !== JSON.stringify(current.list)) {
          // console.log(current.list[0])
          for (let step = 0; step < current.list.length; step++) {
            this.extras.push({extra: JSON.parse(current.list[step].extras), 'hospit_id' : current.list[step].id, 'date_hospit' : current.list[step].date_hospit});
          }
          this.list = current.list;
          this.hospitalises = [];
        }
      });

    this.hospitalisationStore.fetchChambres();
    this.hospitalisationStore.fetchHospitalisationList();

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
      return d.format("DD/MM/yyyy")
    } else {
      return null;
    }
  }

  openHospit(chambre: Number, lit: Number) {
    const value = this.extras.find(
      (e) => e.extra.chambre === chambre && e.extra.lit === lit
    );
    if (value !== undefined) {
      this.router.navigate(['/hospitalisation/edit'], { queryParams: { id: value.hospit_id } });
    }
  }
}
