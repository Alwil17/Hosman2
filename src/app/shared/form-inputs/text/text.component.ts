import { Component, Input, OnInit } from '@angular/core';
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

  constructor() { }

  ngOnInit(): void {
  }

}
