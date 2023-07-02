import { Component, Input, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";

@Component({
  selector: "app-input",
  templateUrl: "./input.component.html",
  styleUrls: ["./input.component.scss"],
})
export class InputComponent implements OnInit {
  @Input() label = "";
  @Input() type = "text";
  @Input() placeholder = "";
  @Input() isMandatory = false;
  @Input() readOnly = false;
  @Input() formControl = new FormControl();
  // @Input() format = "";

  constructor() {}

  ngOnInit(): void {}
}
