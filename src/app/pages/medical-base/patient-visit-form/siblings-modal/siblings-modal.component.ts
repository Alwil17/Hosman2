import { Component, Input, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup, Validators } from "@angular/forms";
import { SelectOption } from "src/app/models/extras/select.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";

@Component({
  selector: "app-siblings-modal",
  templateUrl: "./siblings-modal.component.html",
  styleUrls: ["./siblings-modal.component.scss"],
})
export class SiblingsModalComponent implements OnInit {
  @Input()
  patientInfos!: Patient;

  genders = [
    { id: 1, text: "Masculin", short: "M" },
    { id: 2, text: "Féminin", short: "F" },
    { id: 3, text: "Indéterminé", short: "I" },
  ];

  siblingsForm!: FormGroup;

  constructor() {}

  ngOnInit(): void {
    this.siblingsForm = new FormGroup({
      paternalAndMaternalSiblings: new FormArray([], Validators.minLength(1)),
      paternalSiblings: new FormArray([], Validators.minLength(1)),
      maternalSiblings: new FormArray([], Validators.minLength(1)),
    });

    this.initSiblingsForm();
  }

  initSiblingsForm() {
    this.addPaternalAndMaternalSiblingsField({
      lastName: this.patientInfos.nom,
      firstName: this.patientInfos.prenoms,
      gender: this.genders.find(
        (value) => value.short === this.patientInfos.sexe
      )!,
      dateOfBirth: new Date(
        this.patientInfos.date_naissance
      ).toLocaleDateString("fr-ca"),
    });

    this.addPaternalSiblingsField();
    this.addMaternalSiblingsField();
  }

  // FORMS FIELDS --------------------------------------------------------------------------------------------------------
  get paternalAndMaternalSiblingsFields() {
    return this.siblingsForm.get("paternalAndMaternalSiblings") as FormArray;
  }

  addPaternalAndMaternalSiblingsField(
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
    this.paternalAndMaternalSiblingsFields.push(
      new FormGroup({
        lastName: new FormControl(value?.lastName ?? null),
        firstName: new FormControl(value?.firstName ?? null),
        gender: new FormControl(value?.gender ?? null),
        dateOfBirth: new FormControl(value?.dateOfBirth ?? null),
      })
    );
    // }

    console.log(this.paternalAndMaternalSiblingsFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removePaternalAndMaternalSiblingsField(index: number) {
    this.paternalAndMaternalSiblingsFields.removeAt(index);
    console.log(this.paternalAndMaternalSiblingsFields);
  }

  movePaternalAndMaternalSiblingsFieldDown(index: number) {
    const actual = this.paternalAndMaternalSiblingsFields.controls[index];
    const next = this.paternalAndMaternalSiblingsFields.controls[index + 1];

    this.paternalAndMaternalSiblingsFields.controls[index] = next;
    this.paternalAndMaternalSiblingsFields.controls[index + 1] = actual;
  }

  movePaternalAndMaternalSiblingsFieldUp(index: number) {
    const actual = this.paternalAndMaternalSiblingsFields.controls[index];
    const previous = this.paternalAndMaternalSiblingsFields.controls[index - 1];

    this.paternalAndMaternalSiblingsFields.controls[index] = previous;
    this.paternalAndMaternalSiblingsFields.controls[index - 1] = actual;
  }

  get paternalSiblingsFields() {
    return this.siblingsForm.get("paternalSiblings") as FormArray;
  }

  addPaternalSiblingsField(
    value: {
      lastName: string;
      firstName: string;
      gender: SelectOption;
      dateOfBirth: string;
    } | null = null,
    index: number | null = null
  ) {
    // if (index) {
    //   this.paternalSiblingsFields.insert(
    //     index + 1,
    //     new FormGroup({
    //       lastName: new FormControl(value?.lastName ?? null),
    //       firstName: new FormControl(value?.firstName ?? null),
    //       gender: new FormControl(value?.gender ?? null),
    //       dateOfBirth: new FormControl(value?.dateOfBirth ?? null),
    //     })
    //   );
    // } else {
    this.paternalSiblingsFields.push(
      new FormGroup({
        lastName: new FormControl(value?.lastName ?? null),
        firstName: new FormControl(value?.firstName ?? null),
        gender: new FormControl(value?.gender ?? null),
        dateOfBirth: new FormControl(value?.dateOfBirth ?? null),
      })
    );
    // }

    console.log(this.paternalSiblingsFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removePaternalSiblingsField(index: number) {
    this.paternalSiblingsFields.removeAt(index);
    console.log(this.paternalSiblingsFields);
  }

  movePaternalSiblingsFieldDown(index: number) {
    const actual = this.paternalSiblingsFields.controls[index];
    const next = this.paternalSiblingsFields.controls[index + 1];

    this.paternalSiblingsFields.controls[index] = next;
    this.paternalSiblingsFields.controls[index + 1] = actual;
  }

  movePaternalSiblingsFieldUp(index: number) {
    const actual = this.paternalSiblingsFields.controls[index];
    const previous = this.paternalSiblingsFields.controls[index - 1];

    this.paternalSiblingsFields.controls[index] = previous;
    this.paternalSiblingsFields.controls[index - 1] = actual;
  }

  get maternalSiblingsFields() {
    return this.siblingsForm.get("maternalSiblings") as FormArray;
  }

  addMaternalSiblingsField(
    value: {
      lastName: string;
      firstName: string;
      gender: SelectOption;
      dateOfBirth: string;
    } | null = null,
    index: number | null = null
  ) {
    // if (index) {
    //   this.maternalSiblingsFields.insert(
    //     index + 1,
    //     new FormGroup({
    //       lastName: new FormControl(value?.lastName ?? null),
    //       firstName: new FormControl(value?.firstName ?? null),
    //       gender: new FormControl(value?.gender ?? null),
    //       dateOfBirth: new FormControl(value?.dateOfBirth ?? null),
    //     })
    //   );
    // } else {
    this.maternalSiblingsFields.push(
      new FormGroup({
        lastName: new FormControl(value?.lastName ?? null),
        firstName: new FormControl(value?.firstName ?? null),
        gender: new FormControl(value?.gender ?? null),
        dateOfBirth: new FormControl(value?.dateOfBirth ?? null),
      })
    );
    // }

    console.log(this.maternalSiblingsFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removeMaternalSiblingsField(index: number) {
    this.maternalSiblingsFields.removeAt(index);
    console.log(this.maternalSiblingsFields);
  }

  moveMaternalSiblingsFieldDown(index: number) {
    const actual = this.maternalSiblingsFields.controls[index];
    const next = this.maternalSiblingsFields.controls[index + 1];

    this.maternalSiblingsFields.controls[index] = next;
    this.maternalSiblingsFields.controls[index + 1] = actual;
  }

  moveMaternalSiblingsFieldUp(index: number) {
    const actual = this.maternalSiblingsFields.controls[index];
    const previous = this.maternalSiblingsFields.controls[index - 1];

    this.maternalSiblingsFields.controls[index] = previous;
    this.maternalSiblingsFields.controls[index - 1] = actual;
  }
}
