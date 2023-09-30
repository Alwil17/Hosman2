import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Debt } from "src/app/models/secretariat/patients/debt.model";
import { DebtService } from "src/app/services/secretariat/patients/debt.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-debts",
  templateUrl: "./debts.component.html",
  styleUrls: ["./debts.component.scss"],
})
export class DebtsComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  criteria: SelectOption[] = [
    {
      id: "fullname",
      text: "Nom et prénoms du patient",
    },
    {
      id: "reference",
      text: "N° Reçu",
    },
  ];

  startDateControl = new FormControl(this.today);
  endDateControl = new FormControl();
  searchControl = new FormControl();

  criterionControl = new FormControl(this.criteria[0]);

  debtList: Debt[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.debtList.length;
  debtListCut: Debt[] = [];

  constructor(
    private debtService: DebtService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Patients" },
      { label: "Créances", active: true },
    ];

    this.refreshDebtsList();

    this.startDateControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshDebtsList();
      }
    });

    this.endDateControl.valueChanges.subscribe((value) => {
      if (value) {
        this.refreshDebtsList();
      }
    });

    this.searchControl.valueChanges.subscribe((value) => {
      if (value) {
        this.refreshDebtsList();
      }
    });

    this.criterionControl.valueChanges.subscribe((value) => {
      if (value) {
        this.refreshDebtsList();
      }
    });

    // this.debtService.getAll().subscribe({
    //   next: (data) => {
    //     this.debtList = data;

    //     this.refreshDebts();
    //   },
    //   error: (error) => {
    //     console.error(error);
    //   },
    // });
  }

  refreshDebtsList() {
    const minDate = new Date(this.startDateControl.value);
    const maxDate = this.endDateControl.value
      ? new Date(this.endDateControl.value)
      : undefined;
    const searchTerm = this.searchControl.value;
    const criterion = this.criterionControl.value?.id;

    this.debtService
      .searchBy({
        minDate: minDate,
        maxDate: maxDate,
        searchTerm: searchTerm,
        criterion: criterion,
      })
      .subscribe({
        next: (data) => {
          this.debtList = data;

          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
          // });

          this.refreshDebts();
        },
        error: (error) => {
          console.error(error);

          this.toastService.show({
            messages: [
              "Une erreur s'est produite lors du rafraîchissment de la liste.",
            ],
            delay: 10000,
            type: ToastType.Error,
          });
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
