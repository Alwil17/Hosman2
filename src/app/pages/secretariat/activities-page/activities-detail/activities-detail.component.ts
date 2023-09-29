import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Invoice } from "src/app/models/secretariat/patients/invoice.model";
import { ActGroup } from "src/app/models/secretariat/shared/act-group.model";
import { InvoiceService } from "src/app/services/secretariat/patients/invoice.service";
import { ActGroupService } from "src/app/services/secretariat/shared/act-group.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-activities-detail",
  templateUrl: "./activities-detail.component.html",
  styleUrls: ["./activities-detail.component.scss"],
})
export class ActivitiesDetailComponent implements OnInit {
  rubricsData: ActGroup[] = [];
  rubrics!: SelectOption[];
  rubricFirstOption = { id: -1, text: "Toutes les rubriques" };

  invoicesList: Invoice[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.invoicesList.length;
  invoicesListCut: Invoice[] = [];

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  startDateControl = new FormControl(this.today);
  endDateControl = new FormControl();
  rubricControl = new FormControl(this.rubricFirstOption);
  searchControl = new FormControl("");
  constructor(
    private actGroupService: ActGroupService,
    private invoiceService: InvoiceService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.refreshInvoicesList();

    this.startDateControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshInvoicesList();
      }
    });

    this.endDateControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshInvoicesList();
      }
    });

    this.rubricControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshInvoicesList();
      }
    });

    this.actGroupService.getAll().subscribe({
      next: (data) => {
        this.rubricsData = data;
        this.rubrics = [
          this.rubricFirstOption,
          ...data.map((rubric) => ({
            id: rubric.id,
            text: rubric.libelle,
          })),
        ];
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  refreshInvoicesList() {
    const minDate = new Date(this.startDateControl.value);
    const maxDate = this.endDateControl.value
      ? new Date(this.endDateControl.value)
      : undefined;
    const actGroupCode = this.rubricsData.find(
      (value) => this.rubricControl.value?.id == value.id
    )?.code;

    this.invoiceService
      .searchBy({
        minDate: minDate,
        maxDate: maxDate,
        actGroupCode: actGroupCode,
      })
      .subscribe({
        next: (data) => {
          this.invoicesList = data;

          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
          // });

          this.refreshInvoices();
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

  refreshInvoices() {
    this.collectionSize = this.invoicesList.length;

    this.invoicesListCut = this.invoicesList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }
}
