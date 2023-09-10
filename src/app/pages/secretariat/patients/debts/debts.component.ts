import { Component, OnInit } from "@angular/core";
import { Debt } from "src/app/models/secretariat/patients/debt.model";
import { DebtService } from "src/app/services/secretariat/patients/debt.service";

@Component({
  selector: "app-debts",
  templateUrl: "./debts.component.html",
  styleUrls: ["./debts.component.scss"],
})
export class DebtsComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  debtList: Debt[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.debtList.length;
  debtListCut: Debt[] = [];

  constructor(private debtService: DebtService) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Patients" },
      { label: "Créances", active: true },
    ];

    this.debtService.getAll().subscribe({
      next: (data) => {
        this.debtList = data;

        this.refreshDebts();
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  refreshDebts() {
    this.collectionSize = this.debtList.length;

    this.debtListCut = this.debtList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }
}
