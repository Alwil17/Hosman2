import { Component, Input, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";

@Component({
  selector: "multi-input",
  templateUrl: "./multi-input.component.html",
  styleUrls: ["./multi-input.component.scss"],
})
export class MultiInputComponent implements OnInit {
  @Input() config: any = false;
  @Input() group = new FormGroup({});

  name: string = "";
  control: FormControl = new FormControl();

  constructor() {}

  ngOnInit(): void {
    this.name = this.config.name;

    if (this.name !== null && this.name !== undefined) {
      if (this.group?.contains(this.name!)) {
        this.control = this.group?.get(this.name!)as FormControl
      } else {
        this.group?.addControl(this.name!, this.control);
      }
     
    }

    // check ifs
    const ifs = this.config.if;
    if (ifs !== undefined) {
      ifs.forEach((fi: any) => {
        if (this.group.get(fi.name)!.value !== fi.value) this.config.show = false;

        this.group.get(fi.name)!.valueChanges.subscribe((value) => {
          console.log(value)
          this.config.show = !(value === null || value !== fi.value);
        });
      });
    }
  }
}
