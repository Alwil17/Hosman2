import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'h-checkbox',
  templateUrl: './h-checkbox.component.html',
  styleUrls: ['./h-checkbox.component.scss']
})
export class HCheckboxComponent implements OnInit {
  @Input() label = "";
  @Input() isMandatory = false;
  @Input() control = new FormControl();
  @Input() fontSize: string = "0.8125rem"
  @Input() readOnly = false;
  @Input() pointer = false;
  @Input() group?: FormGroup;
  @Input() name?: string;

  formControl!: FormControl;
  constructor() { }

  ngOnInit(): void {
    this.formControl = this.control as FormControl;
    if (this.group !== null && this.group !== undefined && this.name !== null && this.name !== undefined) {
      this.group?.addControl( this.name, this.formControl)
    }
  }

}
