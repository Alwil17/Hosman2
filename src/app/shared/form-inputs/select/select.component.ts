import { Component, Input, OnInit } from "@angular/core";
import { AbstractControl, FormControl } from "@angular/forms";
import { SelectOption } from "../../../models/extras/select.model";

@Component({
  selector: "app-select",
  templateUrl: "./select.component.html",
  styleUrls: ["./select.component.scss"],
})
export class SelectComponent implements OnInit {
  @Input() label = "";
  @Input() isMandatory = false;
  @Input() isFormSubmitted = false;
  @Input() placeholder = "Sélectionner...";
  @Input() options: SelectOption[] = [];
  @Input() control: AbstractControl | FormControl = new FormControl();
  @Input() canAddOption = false;
  @Input() editable = true;
  @Input() clearable = true;
  @Input() searchable = true;
  @Input() readOnly = false;

  formControl!: FormControl;

  constructor() {}

  ngOnInit(): void {
    if (this.control instanceof AbstractControl)
      this.formControl = this.control as FormControl;
  }

  addTagFn(value: string) {
    const transformed = value.charAt(0).toUpperCase() + value.substring(1);

    return { id: -1, text: transformed, new: true };
  }

  // OLD

  // @Input() label = "";
  // @Input() isMandatory = false;
  // @Input() isFormSubmitted = false;
  // @Input() defaultOption = "Sélectionner...";
  // @Input() options: SelectModel[] = [];
  // @Input() control = new FormControl();

  // constructor() {}

  // ngOnInit(): void {}
}
