import { Component, Input, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { HospitalisationStore } from "@stores/hospitalisation";
import { Patient } from "../../../../../models/secretariat/patients/patient.model";
import { FormControl } from "@angular/forms";

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
  days: number = 1;

  search = new FormControl();

  list: any[] = []; // full list
  filtered_list: any[] = []; // filtered list
  numberOfPages: number = 0;
  searchResults: any[] = [];
  rowsPerPage: number = 10;
  currentPage: number = 1;

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
          this.days = this.getDiffDays(this.hospitalisation["date_hospit"]);
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
  }

  get numberOfPagesArray(): number[] {
    return new Array(this.numberOfPages).fill(0).map((_, index) => index + 1);
  }

  getDiffDays(sDate: string) {
    var startDate = new Date(sDate);
    var endDate = new Date();

    // console.log(startDate);
    // console.log(endDate);

    var Time = endDate.getTime() - startDate.getTime();
    return Time / (1000 * 3600 * 24);
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

  ngOnChanges(): void {
    this.calculateNumberOfPages();
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
