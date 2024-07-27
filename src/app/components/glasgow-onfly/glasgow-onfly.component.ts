import {Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {GlasgowComponent} from "../glasgow/glasgow.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {NgbModalRef} from "@ng-bootstrap/ng-bootstrap/modal/modal-ref";
import {FormControl} from "@angular/forms";
import {distinctUntilChanged} from "rxjs";
import { GLEBE } from "./bebe";
import { GLADULTE } from "./adulte";

@Component({
  selector: 'glasgow-onfly',
  templateUrl: './glasgow-onfly.component.html',
  styleUrls: ['./glasgow-onfly.component.scss']
})
export class GlasgowOnflyComponent implements OnInit {
  @ViewChild("glas") glas!: TemplateRef<any>;
  @Input() modal: any = null;
  @Output() valueEmitter: EventEmitter<any> = new EventEmitter();

  private modalRef!: NgbModalRef;
  formula: any[] = [];
  score: any = 0;

  consciences = [
    { value: "Normale", label: "Normale" },
    { value: "La conscience est abolie", label: "Abolie" },
    { value: "La conscience est altérée", label: "Altérée" },
    { value: "La conscience est très altérée", label: "Très altérée" },
  ];
  conscience = new FormControl(null, [], []);

  constructor(
      private modalService: NgbModal,
  ) { }

  ngOnInit(): void {
    this.formula = GLEBE;

    if (this.modal)
      this.modal.shown.subscribe(() => {
        this.score = this.totalScore();
      });

    this.conscience.valueChanges
        .pipe(distinctUntilChanged())
        .subscribe((value) => {
          if (value !== "Normale")
            this.openGlasgow();
        })


  }

  openGlasgow() {
    this.modalRef = this.modalService.open(this.glas, {
      size: "md",
      centered: true,
      keyboard: false,
      backdrop: "static",
    });

    // modalRef.componentInstance.modal = modalRef;
    // modalRef.componentInstance.closeModal.subscribe((data: any) => {
    //   // return data
    //   // this.glasgow = data;
    // });
  }

  selectRow(score: any, item: any) {
    item.selected = score;
    this.score = this.totalScore();
  }

  totalScore() {
    return this.formula.reduce(
        (total, item) =>
            total + (item.selected !== undefined ? item.selected.value : 0),
        0
    );
  }

  close() {
    const m = this.formula.map((f) => {
      return { name: f.name, selected: f.selected , label: f.label};
    });
    // this.valueEmitter.emit({ value: this.score, selected: m });

    // ici on es obliger de formuler directement vu qu'on peut pas stocker les selections pour le moment
    // console.log(m)
    this.valueEmitter.emit(`${this.conscience.value} et le score de glasgow est de ${this.score} : ${m.reduce(
        (text, item) =>
            text + item.label + ' - ' + item.selected.label + ', ' ,
        ''
    )}` )

    this.modalRef.close()
  }

  cancel() {
    this.modalRef.close()
  }


}
