import { Component, OnInit } from "@angular/core";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";
import { WaitingListService } from "src/app/services/secretariat/patients/waiting-list.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

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

  constructor(
    private waitingListService: WaitingListService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Patients" },
      { label: "Liste d'attente", active: true },
    ];

    this.refreshWaitingList();
  }

  refreshWaitingList() {
    this.waitingListService.getAll().subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Rafraîchissement de la liste."],
          type: ToastType.Success,
        });

        this.waitingList = data;

        this.refreshWaitingListTable();
      },
      error: (e) => {
        console.error(e);

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

  refreshWaitingListTable() {
    this.collectionSize = this.waitingList.length;

    this.waitingListCut = this.waitingList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  deleteWaitingListItem(id: any) {
    this.waitingListService.delete(id).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Patient retiré de la liste d'attente."],
          type: ToastType.Success,
        });

        this.refreshWaitingList();
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }
}
