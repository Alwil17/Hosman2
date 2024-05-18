import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Agency } from "src/app/models/medical-base/submodules/medicines-prescriptions/agency.model";
import { AgencyService } from "src/app/services/medical-base/submodules/medicines-prescriptions/agency.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { AgencyFormModalComponent } from "../agency-form-modal/agency-form-modal.component";
import { merge } from "rxjs";

@Component({
  selector: "app-agency-list",
  templateUrl: "./agency-list.component.html",
  styleUrls: ["./agency-list.component.scss"],
})
export class AgencyListComponent implements OnInit {
  searchControl = new FormControl("");

  agencies: Agency[] = [];

  constructor(
    private agencyService: AgencyService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.getAgenciesList();

    this.searchControl.valueChanges.subscribe((value) => {
      if (value != null) {
        this.searchAgenciesList();
      }
    });
  }

  getAgenciesList() {
    this.agencyService.getAll().subscribe({
      next: (data) => {
        this.agencies = data;

        // this.toastService.show({
        //   messages: ["Rafraîchissement de la liste."],
        //   type: ToastType.Success,
        // });
      },
      error: (error) => {
        console.error(error);

        this.toastService.show({
          messages: [
            "Une erreur s'est produite lors de la récupération de la liste.",
          ],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  searchAgenciesList() {
    const searchTerm = this.searchControl.value
      ? String(this.searchControl.value)
      : "";

    if (searchTerm === "") {
      this.getAgenciesList();

      return;
    }

    this.agencyService
      .searchBy({
        q: searchTerm,
      })
      .subscribe({
        next: (data) => {
          this.agencies = data;

          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
          // });
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

  openAgencyFormModal(agency?: Agency) {
    const agencyFormModalRef = this.modalService.open(
      AgencyFormModalComponent,
      {
        size: "lg",
        centered: true,
        scrollable: true,
        backdrop: "static",
        keyboard: false,
      }
    );

    merge(agencyFormModalRef.closed, agencyFormModalRef.dismissed).subscribe({
      next: (value) => {
        this.searchAgenciesList();
      },
    });

    if (agency) {
      agencyFormModalRef.componentInstance.agencyInfos = agency;

      // agencyFormModalRef.componentInstance.isAgencyModified.subscribe(
      //   (isAgencyModified: boolean) => {
      //     console.log("Product modified : " + isAgencyModified);

      //     if (isAgencyModified) {
      //       agencyFormModalRef.close();
      //     }
      //   }
      // );
    } else {
      // agencyFormModalRef.componentInstance.isAgencyCreated.subscribe(
      //   (isAgencyCreated: boolean) => {
      //     console.log("Product created : " + isAgencyCreated);

      //     if (isAgencyCreated) {
      //       agencyFormModalRef.close();
      //     }
      //   }
      // );
    }
  }
}
