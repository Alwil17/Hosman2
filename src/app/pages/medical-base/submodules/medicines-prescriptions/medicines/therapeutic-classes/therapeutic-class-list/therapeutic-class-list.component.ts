import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { TherapeuticClass } from "src/app/models/medical-base/submodules/medicines-prescriptions/therapeutic-class.model";
import { TherapeuticClassService } from "src/app/services/medical-base/submodules/medicines-prescriptions/therapeutic-class.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

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
    private toastService: ToastService
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
}
