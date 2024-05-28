import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {AbstractControl, FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'h-text',
  templateUrl: './text.component.html',
  styleUrls: ['./text.component.scss']
})
export class HTextComponent implements OnInit {
  @Input() label = "";
  @Input() isMandatory = false;
  @Input() rows = 3;
  @Input() placeholder = "";
  @Input() control = new FormControl();
  @Input() fontSize: string = "0.8125rem"
  @Input() readOnly = false;
  @Input() pointer = false;
  @Input() group?: FormGroup;
  @Input() name?: string;

  @Output() doubleClick: EventEmitter<void> = new EventEmitter<void>();

  formControl!: FormControl;
  constructor() { }

  ngOnInit(): void {
    this.formControl = this.control as FormControl;

    if (this.group !== null && this.group !== undefined && this.name !== null && this.name !== undefined) {
      this.group?.addControl( this.name, this.formControl)
    }
  }

  onDoubleClick(): void {
    this.doubleClick.emit();
  }

}
