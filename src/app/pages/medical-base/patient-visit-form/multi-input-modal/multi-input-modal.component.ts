import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
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

  @Input()
  data?: string[];

  @Output()
  formData = new EventEmitter<string[]>();

  multiInputForm!: FormGroup;

  constructor() {}

  ngOnInit(): void {
    this.multiInputForm = new FormGroup({
      multiInput: new FormArray([]),
    });

    for (let i = 0; i < this.numberOfFields; i++) {
      this.addMultiInputField();
    }

    this.initFieldsValue();
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

  initFieldsValue() {
    if (this.data) {
      this.multiInputFields.controls.forEach((control, index) => {
        control.setValue(this.data![index]);
      });
    }
  }

  submit() {
    const formData: string[] = [];

    this.multiInputFields.controls.forEach((control) =>
      formData.push(control.value)
    );

    this.formData.emit(formData);
  }
}
