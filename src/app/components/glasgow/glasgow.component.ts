import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {FormControl} from "@angular/forms";
import {validateYupSchema} from "../../helpers/utils";
import * as Yup from "yup";
import {ErrorMessages} from "../../helpers/messages";
import { GLEBE } from './bebe';
import { GLADULTE } from './adulte';
import { isUnderAge } from 'src/app/helpers/age-calculator';

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
  age : any = 0

  @Input() hospitalisation : any = null;

  @Output() closeModal: EventEmitter<any> = new EventEmitter();
  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
    if (this.hospitalisation) {

      if (isUnderAge(this.hospitalisation.patient.date_naissance, 5)) {
        this.formula = GLEBE
      } else {
        this.formula = GLADULTE
      }

      const e = JSON.parse(this.hospitalisation.extras).constantes
      if (e !== undefined) {
        this.formula.forEach((f : any) => {
          f.selected = e.glasgow.selected.find((s : any) => s.name === f.name).selected
        })
        this.score = e.glasgow.value
      }

    } else {
      this.formula = GLADULTE
    }

  }

  selectRow(score: any, item: any) {
    item.selected = score
    this.score = this.totalScore()
    console.log(this.score)
  }

  close() {
    const m = this.formula.map((f) => { return {name : f.name , selected : f.selected}})
    this.closeModal.emit({ value: this.score, selected : m });
    this.activeModal.close();
  }

  totalScore(){
    return this.formula.reduce((total, item) => total + (item.selected !== undefined ? item.selected.value : 0), 0)
  }

  cancel(){    
    this.activeModal.close();
  }
}
