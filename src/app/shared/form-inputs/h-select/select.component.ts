import { Component, Input, OnInit, ViewEncapsulation } from "@angular/core";
import { AbstractControl, FormControl } from "@angular/forms";
import { SelectOption } from "../../../models/extras/select.model";
import { Subject } from "rxjs";

@Component({
  selector: "h-select",
  templateUrl: "./select.component.html",
  styleUrls: ["./select.component.scss"],
  encapsulation: ViewEncapsulation.None
})
export class HSelectComponent implements OnInit {
  @Input() label = "";
  @Input() isMandatory = false;
  @Input() isFormSubmitted = false;
  @Input() placeholder = "Sélectionner...";
  @Input() options: SelectOption[] | any[] = [];
  @Input() control: AbstractControl | FormControl = new FormControl();
  @Input() canAddOption = false;
  @Input() editable = true;
  @Input() clearable = true;
  @Input() searchable = true;
  @Input() readOnly = false;
  @Input() bindLabel = "text";
  @Input() bindValue = "value";

  @Input() isLoading = false;
  @Input() typeahead = new Subject<any>();

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
}
