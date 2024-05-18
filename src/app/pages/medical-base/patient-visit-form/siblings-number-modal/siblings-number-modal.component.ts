import { Component, Input, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { merge } from "rxjs";
import { parseIntOrZero } from "src/app/helpers/parsers";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { SiblingsDetailModalComponent } from "../siblings-detail-modal/siblings-detail-modal.component";

@Component({
  selector: "app-siblings-number-modal",
  templateUrl: "./siblings-number-modal.component.html",
  styleUrls: ["./siblings-number-modal.component.scss"],
})
export class SiblingsNumberModalComponent implements OnInit {
  @Input()
  patientInfos!: Patient;

  rankController = new FormControl(null, Validators.required);
  sibling1Controller = new FormControl(null, Validators.required);

  sibling2Controller = new FormControl(null);
  sibling3Controller = new FormControl(null);
  sibling4Controller = new FormControl(null);
  sibling5Controller = new FormControl(null);
  sibling6Controller = new FormControl(null);
  sibling7Controller = new FormControl(null);
  sibling8Controller = new FormControl(null);
  sibling9Controller = new FormControl(null);
  sibling10Controller = new FormControl(null);

  totalController = new FormControl(null);

  siblingNumberForm!: FormGroup;
  isSiblingNumberFormSubmitted = false;

  constructor(private modalService: NgbModal) {}

  ngOnInit(): void {
    this.siblingNumberForm = new FormGroup({
      rank: this.rankController,
      sibling1: this.sibling1Controller,
      sibling2: this.sibling2Controller,
      sibling3: this.sibling3Controller,
      sibling4: this.sibling4Controller,
      sibling5: this.sibling5Controller,
      sibling6: this.sibling6Controller,
      sibling7: this.sibling7Controller,
      sibling8: this.sibling8Controller,
      sibling9: this.sibling9Controller,
      sibling10: this.sibling10Controller,
    });

    merge(
      this.sibling1Controller.valueChanges,
      this.sibling2Controller.valueChanges,
      this.sibling3Controller.valueChanges,
      this.sibling4Controller.valueChanges,
      this.sibling5Controller.valueChanges,
      this.sibling6Controller.valueChanges,
      this.sibling7Controller.valueChanges,
      this.sibling8Controller.valueChanges,
      this.sibling9Controller.valueChanges,
      this.sibling10Controller.valueChanges
    ).subscribe({
      next: (value) => {
        setTimeout(() => {
          this.calculateTotal();
        });
      },
    });
  }

  calculateTotal() {
    const sibling1 = parseIntOrZero(this.sibling1Controller.value);
    const sibling2 = parseIntOrZero(this.sibling2Controller.value);
    const sibling3 = parseIntOrZero(this.sibling3Controller.value);
    const sibling4 = parseIntOrZero(this.sibling4Controller.value);
    const sibling5 = parseIntOrZero(this.sibling5Controller.value);
    const sibling6 = parseIntOrZero(this.sibling6Controller.value);
    const sibling7 = parseIntOrZero(this.sibling7Controller.value);
    const sibling8 = parseIntOrZero(this.sibling8Controller.value);
    const sibling9 = parseIntOrZero(this.sibling9Controller.value);
    const sibling10 = parseIntOrZero(this.sibling10Controller.value);

    const total =
      sibling1 +
      sibling2 +
      sibling3 +
      sibling4 +
      sibling5 +
      sibling6 +
      sibling7 +
      sibling8 +
      sibling9 +
      sibling10;

    this.totalController.setValue(total);
  }

  openSiblingsDetailModal() {
    this.isSiblingNumberFormSubmitted = true;

    if (this.siblingNumberForm.invalid) {
      return;
    }

    const siblingsModalRef = this.modalService.open(
      SiblingsDetailModalComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );

    siblingsModalRef.componentInstance.patientInfos = this.patientInfos;
    siblingsModalRef.componentInstance.patientRank = this.rankController.value;
    siblingsModalRef.componentInstance.totalOfSiblings =
      this.totalController.value;
  }
}
