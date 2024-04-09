import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {FormControl} from "@angular/forms";
import {validateYupSchema} from "../../helpers/utils";
import * as Yup from "yup";
import {ErrorMessages} from "../../helpers/messages";
import { GLEBE } from './bebe';
import { GLADULTE } from './adulte';

@Component({
  selector: 'app-glasgow',
  templateUrl: './glasgow.component.html',
  styleUrls: ['./glasgow.component.scss']
})
export class GlasgowComponent implements OnInit {

  formula: any[] = []

  nom = new FormControl(
      null,
      [],
      [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  prenom = new FormControl(
      null,
      [],
      [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  score : any = 0

  @Input() age: number = 0;

  @Output() closeModal: EventEmitter<any> = new EventEmitter();
  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
    if (this.age <= 5) {
      this.formula = GLEBE
    } else {
      this.formula = GLADULTE
    }
  }

  selectRow(score: any, item: any) {
    item.selected = score
    this.score = this.totalScore()
  }

  close() {
    this.closeModal.emit({ value: this.score });
    this.activeModal.close();
  }

  totalScore(){
    return this.formula.reduce((total, item) => total + (item.selected !== undefined ? item.selected.value : 0), 0)
  }
}
