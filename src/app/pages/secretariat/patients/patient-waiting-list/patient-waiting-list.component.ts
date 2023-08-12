import { Component, OnInit } from "@angular/core";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";
import { WaitingListService } from "src/app/services/secretariat/patients/waiting-list.service";

@Component({
  selector: "app-patient-waiting-list",
  templateUrl: "./patient-waiting-list.component.html",
  styleUrls: ["./patient-waiting-list.component.scss"],
})
export class PatientWaitingListComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  waitingList: WaitingListItem[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.waitingList.length;
  waitingListCut: WaitingListItem[] = [];

  constructor(private waitingListService: WaitingListService) {
    this.waitingList =
      // ...this.waitingList,
      this.waitingListService.getAll();

    this.refreshPatients();
  }

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Patients" },
      { label: "Liste d'attente", active: true },
    ];
  }

  refreshPatients() {
    this.collectionSize = this.waitingList.length;

    this.waitingListCut = this.waitingList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }
}
