import { Component, Input, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { forkJoin } from "rxjs";
import { isAdult } from "src/app/helpers/age-calculator";
import { SelectOption } from "src/app/models/extras/select.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { SectorService } from "src/app/services/secretariat/shared/sector.service";

@Component({
  selector: "app-hospitalisation-form-modal",
  templateUrl: "./hospitalisation-form-modal.component.html",
  styleUrls: ["./hospitalisation-form-modal.component.scss"],
})
export class HospitalisationFormModalComponent implements OnInit {
  @Input()
  patientInfos!: Patient;

  genders = [
    { id: 1, text: "Masculin", short: "M" },
    { id: 2, text: "Féminin", short: "F" },
    { id: 3, text: "Indéterminé", short: "I" },
  ];

  sectors: SelectOption[] = [];
  rooms: SelectOption[] = [];
  beds: SelectOption[] = [];

  statusController = new FormControl(null);
  lastnameController = new FormControl(null);
  firstnameController = new FormControl(null);
  genderController = new FormControl(null);

  birthdayController = new FormControl(null);
  ageController = new FormControl(null);
  entryDateController = new FormControl(null);

  sectorController = new FormControl(null, Validators.required);
  roomController = new FormControl(null);
  bedController = new FormControl(null);
  motifController = new FormControl(null);

  hospitalisationForm!: FormGroup;

  constructor(private sectorService: SectorService) {}

  ngOnInit(): void {
    this.hospitalisationForm = new FormGroup({
      status: this.statusController,
      lastname: this.lastnameController,
      firstname: this.firstnameController,
      gender: this.genderController,

      birthday: this.birthdayController,
      entryDate: this.entryDateController,

      sector: this.sectorController,
      room: this.roomController,
      bed: this.bedController,
      motif: this.motifController,
    });

    this.fetchSelectData();

    // this.initFieldsValue();
  }

  fetchSelectData() {
    forkJoin({
      sectors: this.sectorService.getAll(),
    }).subscribe({
      next: (data) => {
        this.sectors = data.sectors.map((value) => ({
          id: value.id,
          text: value.libelle,
        }));

        this.initFieldsValue();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  initFieldsValue() {
    const isPatientAdult = isAdult(this.patientInfos.date_naissance);

    let status = "";
    if (isPatientAdult) {
      if (this.patientInfos.sexe === "M") {
        status = "M";
      } else {
        status = "Mme";
      }
    } else {
      status = "Enfant";
    }

    this.statusController.setValue(status);
    this.lastnameController.setValue(this.patientInfos.nom);
    this.firstnameController.setValue(this.patientInfos.prenoms);
    this.genderController.setValue(
      this.genders.find((gender) => gender.short === this.patientInfos.sexe)
    );

    this.birthdayController.setValue(
      new Date(this.patientInfos.date_naissance).toLocaleDateString("fr-ca")
    );
    this.ageController.setValue(this.patientInfos.age);
    this.entryDateController.setValue(new Date().toLocaleDateString("fr-ca"));

    // this.sectorController.setValue(null);
  }
}
