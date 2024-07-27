import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-text',
  templateUrl: './text.component.html',
  styleUrls: ['./text.component.scss']
})
export class TextComponent implements OnInit {
  @Input() label = "";
  @Input() isMandatory = false;
  @Input() rows = 3;
  @Input() placeholder = "";
  @Input() control = new FormControl();
  @Input() fontSize: string = "0.8125rem"
  @Input() readOnly = false;
  @Input() pointer = false;
  @Output() doubleClick: EventEmitter<void> = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  onDoubleClick(): void {
    this.doubleClick.emit();
  }

}
