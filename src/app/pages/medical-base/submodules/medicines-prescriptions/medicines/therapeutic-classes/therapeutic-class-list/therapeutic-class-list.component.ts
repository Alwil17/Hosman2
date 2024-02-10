import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { TherapeuticClass } from "src/app/models/medical-base/submodules/medicines-prescriptions/therapeutic-class.model";
import { TherapeuticClassService } from "src/app/services/medical-base/submodules/medicines-prescriptions/therapeutic-class.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { TherapeuticClassFormModalComponent } from "../therapeutic-class-form-modal/therapeutic-class-form-modal.component";
import { firstValueFrom, merge } from "rxjs";
import { ConfirmModalComponent } from "src/app/shared/modals/confirm-modal/confirm-modal.component";

@Component({
  selector: "app-therapeutic-class-list",
  templateUrl: "./therapeutic-class-list.component.html",
  styleUrls: ["./therapeutic-class-list.component.scss"],
})
export class TherapeuticClassListComponent implements OnInit {
  searchControl = new FormControl("");

  therapeuticClasses: TherapeuticClass[] = [];

  constructor(
    private therapeuticClassService: TherapeuticClassService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.getTherapeuticClassList();

    this.searchControl.valueChanges.subscribe((value) => {
      if (value != null) {
        this.searchTherapeuticClass();
      }
    });
  }

  getTherapeuticClassList() {
    this.therapeuticClassService.getAll().subscribe({
      next: (data) => {
        this.therapeuticClasses = data;

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

  searchTherapeuticClass() {
    const searchTerm = this.searchControl.value
      ? String(this.searchControl.value)
      : "";

    if (searchTerm === "") {
      this.getTherapeuticClassList();

      return;
    }

    this.therapeuticClassService
      .searchBy({
        q: searchTerm,
      })
      .subscribe({
        next: (data) => {
          this.therapeuticClasses = data;

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

  openThClassModal(thClass?: TherapeuticClass) {
    const thClassFormModalRef = this.modalService.open(
      TherapeuticClassFormModalComponent,
      {
        size: "md",
        centered: true,
        scrollable: true,
        backdrop: "static",
        keyboard: false,
      }
    );

    merge(thClassFormModalRef.closed, thClassFormModalRef.dismissed).subscribe({
      next: (value) => {
        this.searchTherapeuticClass();
      },
    });

    if (thClass) {
      thClassFormModalRef.componentInstance.thClassInfos = thClass;
    }
  }

  async deleteThClass(thClass: TherapeuticClass) {
    const thClassId = thClass.id;

    // OPEN CONFIRMATION MODAL
    const confirmModalRef = this.modalService.open(ConfirmModalComponent, {
      size: "md",
      centered: true,
      keyboard: false,
      backdrop: "static",
      // scrollable: true,
    });

    confirmModalRef.componentInstance.message =
      "Êtes-vous sûr de vouloir supprimer la classe thérapeutique : " +
      thClass.nom +
      " ?";
    confirmModalRef.componentInstance.subMessage =
      "Cette opération est irréversible.";
    confirmModalRef.componentInstance.isDangerButton = true;

    const isConfirmed = await firstValueFrom(
      confirmModalRef.componentInstance.isConfirmed.asObservable()
    );

    // CLOSE CONFIRMATION MODAL
    confirmModalRef.close();

    // CHECK IF USER CONFIRMED OR NOT
    if (!isConfirmed) {
      return;
    }

    this.therapeuticClassService.delete(thClassId).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: [
            "La classe " + thClass.nom + " a été supprimée avec succès.",
          ],
          type: ToastType.Success,
        });

        this.searchTherapeuticClass();
      },
      error: (error) => {
        console.log(error);

        this.toastService.show({
          messages: ["Désolé, une erreur s'est produite."],
          type: ToastType.Error,
          delay: 10_000,
        });
      },
    });
  }
}
