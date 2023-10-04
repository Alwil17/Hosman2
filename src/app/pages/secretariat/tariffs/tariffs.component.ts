import { Component, OnInit } from "@angular/core";
import { ActGroup } from "src/app/models/secretariat/shared/act-group.model";
import { Tariff } from "src/app/models/secretariat/shared/tariff.model";
import { ActGroupService } from "src/app/services/secretariat/shared/act-group.service";
import { TariffService } from "src/app/services/secretariat/shared/tariff.service";

@Component({
  selector: "app-tariffs",
  templateUrl: "./tariffs.component.html",
  styleUrls: ["./tariffs.component.scss"],
})
export class TariffsComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  actGroups!: ActGroup[];
  selectedGroupTariffs!: Tariff[];

  table1: any[] = [];

  table2: {}[] = [];

  table1Page = 1;
  table1PageSize = 5;
  table1CollectionSize = this.table1.length;
  activities: any[] = [];

  table2Page = 1;
  table2PageSize = 5;
  table2CollectionSize = this.table2.length;
  activitiesSelect: {}[] = [];

  constructor(
    private actGroupService: ActGroupService,
    private tariffService: TariffService
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Renseignements" },
      { label: "Tarifs", active: true },
    ];

    this.actGroupService.getAll().subscribe({
      next: (data) => {
        this.actGroups = data;

        // this.selectedPrestationIndex = data[0].code;
        this.tariffService.getByGroupCode(data[0].code).subscribe({
          next: (data) => {
            this.selectedGroupTariffs = data;

            this.refreshTable1();
          },
          error: (e) => {
            console.log(e);
          },
        });
      },
      error: (e) => {
        console.log(e);
      },
    });
  }

  updateTable(actGroup: ActGroup) {
    const code = actGroup.code;

    // this.selectedPrestationIndex = code;

    this.tariffService.getByGroupCode(code).subscribe({
      next: (data) => {
        this.selectedGroupTariffs = data;

        this.refreshTable1();
      },
      error: (e) => {
        console.log(e);
      },
    });
  }

  refreshTable1() {
    this.table1 = this.selectedGroupTariffs.map((item) => {
      // let patientPrice = 0;
      // if (this.patientService.getActivePatientType() == 0) {
      //   patientPrice = item.tarif_non_assure;
      // } else if (this.patientService.getActivePatientType() == 1) {
      //   patientPrice = item.tarif_etr_non_assure;
      // } else if (this.patientService.getActivePatientType() == 2) {
      //   patientPrice = item.tarif_assur_locale;
      // } else {
      //   patientPrice = item.tarif_assur_hors_zone;
      // }

      return {
        id: item.id,
        designation: item.libelle,
        no_local: item.tarif_non_assure,
        no_foreigner: item.tarif_etr_non_assure,
        yes_local: item.tarif_assur_locale,
        yes_foreigner: item.tarif_assur_hors_zone,
        description: item.description,
      };
    });

    this.table1Page = 1;
    this.table1CollectionSize = this.table1.length;

    this.refreshActivities();
  }

  refreshActivities() {
    this.activities = this.table1
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.table1Page - 1) * this.table1PageSize,
        (this.table1Page - 1) * this.table1PageSize + this.table1PageSize
      );
  }

  refreshActivitiesSelect() {
    this.activitiesSelect = this.table2
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.table2Page - 1) * this.table2PageSize,
        (this.table2Page - 1) * this.table2PageSize + this.table2PageSize
      );
  }
}
