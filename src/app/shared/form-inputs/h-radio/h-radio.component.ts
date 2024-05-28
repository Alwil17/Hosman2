import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {SelectOption} from "../../../models/extras/select.model";

@Component({
  selector: 'h-radio',
  templateUrl: './h-radio.component.html',
  styleUrls: ['./h-radio.component.scss']
})
export class HRadioComponent implements OnInit {
  @Input() label = "";
  @Input() control = new FormControl();
  @Input() fontSize: string = "0.8125rem"
  @Input() readOnly = false;
  @Input() pointer = false;
  @Input() options: SelectOption[] | any[] = [];
  @Input() bindLabel = "text";
  @Input() bindValue = "value";
  @Input() group?: FormGroup;
  @Input() name: string = 'radio';

  formControl!: FormControl;
  constructor() { }

  ngOnInit(): void {
    this.formControl = this.control as FormControl;

    if (this.group !== null && this.group !== undefined && this.name !== null && this.name !== undefined) {
      this.group?.addControl( this.name, this.formControl)
    }
  }

}
