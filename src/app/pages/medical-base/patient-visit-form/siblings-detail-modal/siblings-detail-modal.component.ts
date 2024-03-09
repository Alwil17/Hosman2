import { Component, Input, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup } from "@angular/forms";
import { SelectOption } from "src/app/models/extras/select.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";

@Component({
  selector: "app-siblings-detail-modal",
  templateUrl: "./siblings-detail-modal.component.html",
  styleUrls: ["./siblings-detail-modal.component.scss"],
})
export class SiblingsDetailModalComponent implements OnInit {
  @Input()
  patientInfos!: Patient;

  @Input()
  patientRank!: number;

  @Input()
  totalOfSiblings!: number;

  genders = [
    { id: 1, text: "Masculin", short: "M" },
    { id: 2, text: "Féminin", short: "F" },
    { id: 3, text: "Indéterminé", short: "I" },
  ];

  siblingsForm!: FormGroup;

  constructor() {}

  ngOnInit(): void {
    this.siblingsForm = new FormGroup({
      siblings: new FormArray([]),
    });

    this.initSiblingsForm();
  }

  initSiblingsForm() {
    for (let i = 0; i < this.totalOfSiblings; i++) {
      if (i === this.patientRank - 1) {
        this.addSiblingsField({
          lastName: this.patientInfos.nom,
          firstName: this.patientInfos.prenoms,
          gender: this.genders.find(
            (value) => value.short === this.patientInfos.sexe
          )!,
          dateOfBirth: new Date(
            this.patientInfos.date_naissance
          ).toLocaleDateString("fr-ca"),
        });

        continue;
      }
      this.addSiblingsField();
    }
  }

  // FORMS FIELDS --------------------------------------------------------------------------------------------------------
  get siblingsFields() {
    return this.siblingsForm.get("siblings") as FormArray;
  }

  addSiblingsField(
    value: {
      lastName: string;
      firstName: string;
      gender: SelectOption;
      dateOfBirth: string;
    } | null = null,
    index: number | null = null
  ) {
    // if (index) {
    //   this.paternalAndMaternalSiblingsFields.insert(
    //     index + 1,
    //     new FormGroup({
    //       lastName: new FormControl(value?.lastName ?? null),
    //       firstName: new FormControl(value?.firstName ?? null),
    //       gender: new FormControl(value?.gender ?? null),
    //       dateOfBirth: new FormControl(value?.dateOfBirth ?? null),
    //     })
    //   );
    // } else {
    this.siblingsFields.push(
      new FormGroup({
        lastName: new FormControl(value?.lastName ?? null),
        firstName: new FormControl(value?.firstName ?? null),
        gender: new FormControl(value?.gender ?? null),
        dateOfBirth: new FormControl(value?.dateOfBirth ?? null),
      })
    );
    // }

    console.log(this.siblingsFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removeSiblingsField(index: number) {
    this.siblingsFields.removeAt(index);
    console.log(this.siblingsFields);
  }
}
