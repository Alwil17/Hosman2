import { Component, Input, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup } from "@angular/forms";

@Component({
  selector: "app-multi-input-modal",
  templateUrl: "./multi-input-modal.component.html",
  styleUrls: ["./multi-input-modal.component.scss"],
})
export class MultiInputModalComponent implements OnInit {
  @Input()
  title = "";

  @Input()
  label = "";

  @Input()
  numberOfFields!: number;

  multiInputForm!: FormGroup;

  constructor() {}

  ngOnInit(): void {
    this.multiInputForm = new FormGroup({
      multiInput: new FormArray([]),
    });

    for (let i = 0; i < this.numberOfFields; i++) {
      this.addMultiInputField();
    }
  }

  // MULTI-INPUT FIELDS --------------------------------------------------------------------------------------------------------
  get multiInputFields() {
    return this.multiInputForm.get("multiInput") as FormArray;
  }

  addMultiInputField() {
    this.multiInputFields.push(new FormControl(null));
    console.log(this.multiInputFields);
  }

  removeMultiInputField(index: number) {
    this.multiInputFields.removeAt(index);
    console.log(this.multiInputFields);
  }
}
