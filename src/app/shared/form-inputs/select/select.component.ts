import { Component, Input, OnInit } from "@angular/core";
import { AbstractControl, FormControl } from "@angular/forms";
import { SelectOption } from "../../../models/extras/select.model";
import { Subject } from "rxjs";

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

  @Input() isLayoutHorizontal = false;

  @Input() isLoading = false;
  @Input() typeahead = new Subject<any>();

  // Possible values are 'sm', '' and 'lg'
  @Input() size = "sm";

  // Possible values are '1', '2', '3', '4', '5'
  @Input() bottomMargin = "";

  formControl!: FormControl;

  bottomMarginClass = "";

  constructor() {}

  ngOnInit(): void {
    if (this.bottomMargin) {
      this.bottomMarginClass = "mb-" + this.bottomMargin;
    } else {
      if (this.size === "sm") {
        this.bottomMarginClass = "mb-2";
      } else if (this.size === "lg") {
        this.bottomMarginClass = "mb-3";
      } else {
        this.bottomMarginClass = "mb-2";
      }
    }

    // Due to some sort of typing problem ? Investigate later
    if (this.control instanceof AbstractControl) {
      this.formControl = this.control as FormControl;
    }
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
