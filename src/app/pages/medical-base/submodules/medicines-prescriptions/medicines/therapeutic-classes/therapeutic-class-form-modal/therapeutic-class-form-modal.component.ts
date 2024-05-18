import { Component, Input, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { TherapeuticClassRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/therapeutic-class-request.model";
import { TherapeuticClass } from "src/app/models/medical-base/submodules/medicines-prescriptions/therapeutic-class.model";
import { TherapeuticClassService } from "src/app/services/medical-base/submodules/medicines-prescriptions/therapeutic-class.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-therapeutic-class-form-modal",
  templateUrl: "./therapeutic-class-form-modal.component.html",
  styleUrls: ["./therapeutic-class-form-modal.component.scss"],
})
export class TherapeuticClassFormModalComponent implements OnInit {
  @Input()
  thClassInfos?: TherapeuticClass;

  nameController = new FormControl(null, Validators.required);

  thClassForm!: FormGroup;

  isThClassSubmitted = false;

  constructor(
    private thClassService: TherapeuticClassService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.thClassForm = new FormGroup({
      name: this.nameController,
    });

    if (this.thClassInfos) {
      this.nameController.setValue(this.thClassInfos.nom);
    }
  }

  registerThClass() {
    this.isThClassSubmitted = true;

    if (this.thClassForm.invalid) {
      return;
    }

    const thClassData = new TherapeuticClassRequest({
      nom: this.nameController.value,
    });

    this.thClassService.create(thClassData).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["La classe a été enregistrée avec succès."],
          type: ToastType.Success,
        });

        this.nameController.setValue(null);
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Désolé, une erreur s'est produite"],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  registerModifications() {
    this.isThClassSubmitted = true;

    if (this.thClassForm.invalid) {
      return;
    }

    const thClassData = new TherapeuticClassRequest({
      nom: this.nameController.value,
    });

    this.thClassService.update(this.thClassInfos?.id, thClassData).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["La classe a été modifiée avec succès."],
          type: ToastType.Success,
        });
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Désolé, une erreur s'est produite"],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }
}
