import { Component, Input, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";

@Component({
  selector: "app-select",
  templateUrl: "./select.component.html",
  styleUrls: ["./select.component.scss"],
})
export class SelectComponent implements OnInit {
  @Input() label = "";
  @Input() isMandatory = false;
  @Input() defaultOption = "Sélectionner...";
  @Input() options: string[] = [];
  @Input() control = new FormControl();

  constructor() {}

  ngOnInit(): void {}
}
