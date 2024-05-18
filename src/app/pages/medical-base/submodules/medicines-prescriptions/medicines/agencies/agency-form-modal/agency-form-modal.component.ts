import { Component, Input, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Agency } from "src/app/models/medical-base/submodules/medicines-prescriptions/agency.model";
import { AgencyRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/agency-request.model";
import { AgencyService } from "src/app/services/medical-base/submodules/medicines-prescriptions/agency.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-agency-form-modal",
  templateUrl: "./agency-form-modal.component.html",
  styleUrls: ["./agency-form-modal.component.scss"],
})
export class AgencyFormModalComponent implements OnInit {
  @Input()
  agencyInfos?: Agency;

  nameController = new FormControl(null, Validators.required);
  directorController = new FormControl(null);
  emailController = new FormControl(null, [
    Validators.required,
    Validators.email,
  ]);
  addressController = new FormControl(null);
  tel1Controller = new FormControl(null);
  tel2Controller = new FormControl(null);

  agencyForm!: FormGroup;

  isAgencyFormSubmitted = false;

  constructor(
    private agencyService: AgencyService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.agencyForm = new FormGroup({
      name: this.nameController,
      director: this.directorController,
      email: this.emailController,
      address: this.addressController,
      tel1: this.tel1Controller,
      tel2: this.tel2Controller,
    });

    if (this.agencyInfos) {
      this.nameController.setValue(this.agencyInfos.nom);
      this.directorController.setValue(this.agencyInfos.directeur);
      this.emailController.setValue(this.agencyInfos.email);
      this.addressController.setValue(this.agencyInfos.adresse);
      this.tel1Controller.setValue(this.agencyInfos.tel1);
      this.tel2Controller.setValue(this.agencyInfos.tel2);
    }
  }

  registerThClass() {
    this.isAgencyFormSubmitted = true;

    if (this.agencyForm.invalid) {
      return;
    }

    const agencyData = new AgencyRequest({
      nom: this.nameController.value,
      directeur: this.directorController.value,
      email: this.emailController.value,
      adresse: this.addressController.value,
      tel1: this.tel1Controller.value,
      tel2: this.tel2Controller.value,
    });

    this.agencyService.create(agencyData).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["L'agence a été enregistrée avec succès."],
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
    this.isAgencyFormSubmitted = true;

    if (this.agencyForm.invalid) {
      return;
    }

    const agencyData = new AgencyRequest({
      nom: this.nameController.value,
      directeur: this.directorController.value,
      email: this.emailController.value,
      adresse: this.addressController.value,
      tel1: this.tel1Controller.value,
      tel2: this.tel2Controller.value,
    });

    this.agencyService.update(this.agencyInfos?.id, agencyData).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["L'agence a été modifiée avec succès."],
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
