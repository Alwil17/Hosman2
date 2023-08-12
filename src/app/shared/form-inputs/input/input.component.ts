import { Component, Input, OnInit } from "@angular/core";
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

  constructor() {}

  ngOnInit(): void {
    // if (this.controlName.trim().length !== 0 && this.group) {
    //   this.control = this.group.get(this.controlName) as FormControl;
    // }
  }
}
