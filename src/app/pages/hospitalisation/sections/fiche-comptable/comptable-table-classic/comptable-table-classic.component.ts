import { Component, Input, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { HospitalisationStore } from "@stores/hospitalisation";
import { Patient } from "../../../../../models/secretariat/patients/patient.model";
import { FormControl } from "@angular/forms";
import * as moment from "moment";
import { Chambre } from "../../../../../models/hospitalisation/chambre";

var timer: any, // timer required to reset
  timeout = 200;

@Component({
  selector: "app-comptable-table-classic",
  templateUrl: "./comptable-table-classic.component.html",
  styleUrls: ["./comptable-table-classic.component.scss"],
})
export class ComptableTableClassicComponent implements OnInit {
  subscription: Subscription | undefined;

  @Input() typeData: string | null | undefined;
  @Input() tableData: any[] = [];
  @Input() headers: any[] = [];

  applyFilter: string = "nom_officiel";

  patient: any;
  hospitalisation: any;
  days: any[] = [];

  search = new FormControl();

  list: any[] = []; // full list
  filtered_list: any[] = []; // filtered list
  numberOfPages: number = 0;
  searchResults: any[] = [];
  rowsPerPage: number = 10;
  currentPage: number = 1;
  suivis: any[] = [];

  constructor(private hospitalisationStore: HospitalisationStore) {
    this.search.valueChanges.subscribe((value) => {
      this.doFilter(value);
    });
  }

  ngOnInit(): void {
    this.subscription = this.hospitalisationStore.stateChanged.subscribe(
      (state) => {
        if (state) {
          this.patient = state.patient;
          this.hospitalisation = state.hospitalisation;
          this.suivis = state.suivis;
        }
      }
    );

    this.list = this.tableData;

    /* reformat chambres data */
    if (this.typeData == "chambres") {
      let d: object[] = [];
      for (const chambreKey in this.tableData) {
        if (this.tableData.hasOwnProperty(chambreKey)) {
          const chambre = this.tableData[chambreKey];

          // Now, you can iterate over the keys of chambre
          for (const litKey in chambre.lits) {
            const litValue = chambre.lits[litKey];

            if (litValue !== null && litValue !== undefined) {
              d.push({
                id: litValue.id,
                chambre: chambre.id,
                libelle: `${chambre.nom} - ${litValue.nom}`,
              });
            }
          }
        }
      }

      this.list = d;
    }

    switch (this.typeData) {
      case "medic":
      case "conso":
        this.applyFilter = "nom_officiel";
        break;

      case "examens":
      case "chambres":
        this.applyFilter = "libelle";
        break;
    }

    this.calculateNumberOfPages();
    this.setCurrentPage(1);

    this.genDays();
  }

  get numberOfPagesArray(): number[] {
    return new Array(this.numberOfPages).fill(0).map((_, index) => index + 1);
  }

  genDays() {
    const res = [];
    let date = new Date(this.hospitalisation["date_hospit"]);
    let currentDate = new Date();
    let days = Math.floor(
      (currentDate.getTime() - date.getTime()) / 1000 / 60 / 60 / 24
    );
    let r = moment(this.hospitalisation["date_hospit"]);
    for (let i = 0; i <= days; i++) {
      let currentDate = moment(date).add(i, "days");
      res.push({
        o: currentDate.format("yyyy-MM-DD"),
        i: i,
      });
    }

    this.days = res;
  }

  private calculateNumberOfPages(): void {
    if (this.list !== null) {
      const itemsToPaginate =
        this.search.value === null || this.search.value === ""
          ? this.list
          : this.searchResults;

      this.numberOfPages = Math.ceil(
        (itemsToPaginate.length || 0) / this.rowsPerPage
      );
    } else {
      this.numberOfPages = 0;
    }
  }

  get visiblePageNumbers(): number[] {
    const surroundingPages = 2;
    const visiblePages: number[] = [];

    if (this.numberOfPages <= 7) {
      // If there are 7 or fewer pages, display all pages
      for (let i = 1; i <= this.numberOfPages; i++) {
        visiblePages.push(i);
      }
    } else {
      // Display a subset of pages with surrounding pages
      const start = Math.max(1, this.currentPage - surroundingPages);
      const end = Math.min(
        this.numberOfPages,
        this.currentPage + surroundingPages
      );

      // Display "Prev" button if needed
      if (start > 1) {
        visiblePages.push(1);
        if (start > 2) {
          visiblePages.push(-1); // Ellipsis
        }
      }

      // Display visible pages
      for (let i = start; i <= end; i++) {
        visiblePages.push(i);
      }

      // Display "Next" button if needed
      if (end < this.numberOfPages) {
        if (end < this.numberOfPages - 1) {
          visiblePages.push(-1); // Ellipsis
        }
        visiblePages.push(this.numberOfPages);
      }
    }

    return visiblePages;
  }

  previous(): void {
    this.currentPage--;
    this.setCurrentPage(this.currentPage);
  }

  next(): void {
    this.currentPage++;
    this.setCurrentPage(this.currentPage);
  }

  setCurrentPage(page: number): void {
    if (this.list == null) {
      this.filtered_list = [];
    } else {
      this.currentPage = page;
      this.filtered_list = (
        this.search.value === null || this.search.value === ""
          ? this.list
          : this.searchResults
      ).slice(
        (this.currentPage - 1) * this.rowsPerPage,
        this.currentPage * this.rowsPerPage
      );
    }
  }

  doFilter(text: string): void {
    try {
      if (text.length === 0) {
        this.filtered_list = this.list;
      } else {
        this.searchResults = this.list.filter((object: string | null) => {
          let result = "";
          if (object === null) return null;
          if (
            this.applyFilter === null ||
            this.applyFilter.split(",").length === 0
          )
            return null;
          for (const key of this.applyFilter.split(",")) {
            let rowValue = "";
            // if (key.trim().includes(".")) {
            //   const x = key.trim().split(".");
            //   rowValue =
            //     object[x[0] as keyof typeof object] !== undefined && object[x[0] as keyof typeof object] !== null
            //       ? (object[x[0] as keyof typeof object][x[1] as keyof typeof object[x[0]]] as string)

            //       : "";
            // } else {
            rowValue =
              object[key as keyof typeof object] !== null &&
              object[key.trim() as keyof typeof object] !== undefined
                ? object[key.trim() as keyof typeof object].toString()
                : "";
            // }
            if (rowValue.toLowerCase().includes(text.trim().toLowerCase())) {
              result = object;
              break;
            }
          }
          return result;
        });
        this.filtered_list = this.searchResults;
      }

      this.calculateNumberOfPages();
      this.setCurrentPage(1);
    } catch (e) {
      console.log("filtering : " + e);
    }
  }

  getRowId(day: moment.Moment, type_id: number, sub_id?: number) {
    if (this.suivis !== null && this.suivis !== undefined) {
      if (this.typeData == "chambres") {
        const ch = this.suivis.find(
          (t) =>
            t["type"] === "chambres" &&
            t["type_id"] === sub_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );
        const li = this.suivis.find(
          (t) =>
            t["type"] === "lits" &&
            t["type_id"] === type_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );

        return (
          (ch !== null && ch !== undefined ? ch.id : null) +
          "/" +
          (li !== null && li !== undefined ? li.id : null)
        );
      } else {
        const res = this.suivis.find(
          (t) =>
            t["type"] === this.typeData &&
            t["type_id"] === type_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );
        return res !== null && res !== undefined ? res.id : null;
      }
    } else {
      return null;
    }
  }

  setActive(day: moment.Moment, type_id: number, sub_id?: number) {
    if (this.suivis !== null && this.suivis !== undefined) {
      if (this.typeData == "chambres") {
        const ch = this.suivis.find(
          (t) =>
            t["type"] === "chambres" &&
            t["type_id"] === sub_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );
        const li = this.suivis.find(
          (t) =>
            t["type"] === "lits" &&
            t["type_id"] === type_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );

        return (
          ch !== null && ch !== undefined && li !== null && li !== undefined
        );
      } else {
        const res = this.suivis.find(
          (t) =>
            t["type"] === this.typeData &&
            t["type_id"] === type_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );
        return res !== null && res !== undefined;
      }
    } else {
      return false;
    }
  }

  getTotal(type_id: number, sub_id?: any) {
    let res = 0;

    if (this.suivis !== null && this.suivis !== undefined) {
      if (this.typeData == "chambres") {
        const li = this.suivis.filter(
          (t) => t["type"] === "lits" && t["type_id"] === type_id
        );

        res = li.reduce((total: any, item: any) => total + item.qte, 0);
      } else {
        const arr = this.suivis.filter(
          (t) => t["type"] === this.typeData && t["type_id"] === type_id
        );

        const v = arr.reduce((total, item) => total + item.qte, 0);
        res = v;
      }
    }

    return res !== 0 ? res : "";
  }

  getRowQte(day: moment.Moment, type_id: number, sub_id?: number) {
    if (this.suivis !== null && this.suivis !== undefined) {
      if (this.typeData !== "chambres") {
        const res = this.suivis.find(
          (t) =>
            t["type"] === this.typeData &&
            t["type_id"] === type_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );
        return res !== null && res !== undefined ? res.qte : '';
      }
    } else {
      return '';
    }
  }

  selectItem(day: moment.Moment, type_id: number, extra?: any) {
    if (this.typeData == "chambres") {
      const row = this.getRowId(day, type_id, extra);
      if (row.toString().includes('null')) {
        const c = {
          type: "chambres",
          type_id: extra,
          qte: 1,
          apply_date: moment(day).format("YYYY-MM-DD[T]HH:mm:ss"),
          hospit_id: this.hospitalisation.id,
        };

        const l = {
          type: "lits",
          type_id: type_id,
          qte: 1,
          apply_date: moment(day).format("YYYY-MM-DD[T]HH:mm:ss"),
          hospit_id: this.hospitalisation.id,
        };

        this.hospitalisationStore.commitSuivi(c);
        this.suivis.push(c);
        this.hospitalisationStore.commitSuivi(l);
        this.suivis.push(l);
      }
    } else {
      const row = this.getRowId(day, type_id, extra);

      if (row === null || row === undefined) {
        const data = {
          type: this.typeData,
          type_id: type_id,
          qte: 1,
          apply_date: moment(day).format("YYYY-MM-DD[T]HH:mm:ss"),
          hospit_id: this.hospitalisation.id,
        };
        this.hospitalisationStore.commitSuivi(data);
        this.suivis.push(data);
      } else {
        let rowValue = this.suivis.find((s) => s.id === row);
        rowValue.qte++;

        delete rowValue.created_at
        delete rowValue.updated_at
        delete rowValue.id

        rowValue.hospit_id = this.hospitalisation.id
        this.hospitalisationStore.commitSuivi(rowValue);
      }
    }
  }

  removeItem(day: moment.Moment, type_id: number, extra?: any) {
    const row = this.getRowId(day, type_id, extra);

    if (row !== null && row !== undefined) {
      if (row.toString().includes("/")) {
        const arr = row.split("/");
        const ch = arr[0];
        const li = arr[1];

        if (ch !== "null" && li !== "null") {
          this.hospitalisationStore.removeSuivi(ch);
          this.hospitalisationStore.removeSuivi(li);
          this.suivis = this.suivis.filter(
            (s) => s.id.toString() !== li && s.id.toString() !== ch
          );
          // console.log(this.suivis);
        }
      } else {
        // means its not room suivi
        let rowValue = this.suivis.find((s) => s.id === row);
        // console.log(rowValue)
        if (rowValue.qte > 1) {
          rowValue.qte--;

          delete rowValue.created_at
          delete rowValue.updated_at
          delete rowValue.id

          rowValue.hospit_id = this.hospitalisation.id
          this.hospitalisationStore.commitSuivi(rowValue);
        } else {
          this.hospitalisationStore.removeSuivi(row);
          this.suivis = this.suivis.filter((s) => s.id !== row);
        }
      }
    }
  }

  dbClick(day: moment.Moment, type_id: number, extra?: any) {
    let app = this;
    timer = setTimeout(function () {
      timer = null;
      app.selectItem(day, type_id, extra);
    }, timeout);
  }

  sClick(day: moment.Moment, type_id: number, extra?: any) {
    if (timer) {
      clearTimeout(timer);
      timer = null;
      this.removeItem(day, type_id, extra);
    }
  }

  ngOnChanges(): void {
    this.calculateNumberOfPages();
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
