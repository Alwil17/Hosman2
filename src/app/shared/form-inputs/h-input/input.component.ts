import { TitleCasePipe } from "@angular/common";
import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { AbstractControl, FormControl, FormGroup } from "@angular/forms";

@Component({
  selector: "h-input",
  templateUrl: "./input.component.html",
  styleUrls: ["./input.component.scss"],
})
export class HInputComponent implements OnInit {
  @Input() label = "";
  @Input() type = "text";
  @Input() placeholder = "";

  @Input() alignRight = false;

  @Input() isMandatory = false;
  @Input() isFormSubmitted = false;
  @Input() readOnly = false;

  @Input() minDate?: string;
  @Input() maxDate?: string;

  @Input() maskFormat = "";
  @Input() maskSuffix = "";
  @Input() units? : string;
  @Input() dropMaskInValue = true;

  // @Input() controlName: string = "";
  @Input() control: FormControl = new FormControl();
  @Input() group?: FormGroup;
  @Input() name?: string;

  @Input() isLayoutHorizontal = false;

  @Input()
  emitOnInputClick = false;

  @Output()
  onInputClick: EventEmitter<void> = new EventEmitter<void>();

  @Input() uppercased = false;
  @Input() titlecased = false;
  @Input() firstLetterUppercased = false;

  // Possible values are 'sm', '' and 'lg'
  @Input() size = "sm";

  // Possible values are '1', '2', '3', '4', '5'
  @Input() bottomMargin = "";

  @Input() tabIndex : number | null = null

  formControl!: FormControl;

  bottomMarginClass = "";

  constructor(private titleCase: TitleCasePipe) {}

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
    // if (this.controlName.trim().length !== 0 && this.group) {
    //   this.control = this.group.get(this.controlName) as FormControl;
    // }

    // Due to some sort of typing problem ? Investigate later. Used for dynamic generation of filed
    if (this.control instanceof AbstractControl) {
      this.formControl = this.control as FormControl;
    }

    if (this.group !== null && this.group !== undefined && this.name !== null && this.name !== undefined) {
        this.group?.addControl( this.name, this.formControl)
    }
  }

  emitInputClick() : void {
    this.onInputClick.emit();
  }

  onChange() {
    if (this.uppercased) {
      this.control.setValue((this.control.value as string).toUpperCase());
    } else if (this.titlecased) {
      this.control.setValue(this.titleCase.transform(this.control.value));
    } else if (this.firstLetterUppercased) {
      const value = this.control.value as string;
      const transformed = value.charAt(0).toUpperCase() + value.substring(1);
      this.control.setValue(transformed);
    }
  }
}
