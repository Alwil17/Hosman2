import { TitleCasePipe } from "@angular/common";
import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { AbstractControl, FormControl, FormGroup } from "@angular/forms";

@Component({
  selector: "app-input",
  templateUrl: "./input.component.html",
  styleUrls: ["./input.component.scss"],
})
export class InputComponent implements OnInit {
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

  // @Input() controlName: string = "";
  @Input() control = new FormControl();
  // @Input() group?: FormGroup;

  @Input() isLayoutHorizontal = false;

  @Input()
  emitOnInputClick = false;

  @Output()
  onInputClick = new EventEmitter();

  @Input() uppercased = false;
  @Input() titlecased = false;
  @Input() firstLetterUppercased = true;

  constructor(private titleCase: TitleCasePipe) {}

  ngOnInit(): void {
    // if (this.controlName.trim().length !== 0 && this.group) {
    //   this.control = this.group.get(this.controlName) as FormControl;
    // }
  }

  emitInputClick() {
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
