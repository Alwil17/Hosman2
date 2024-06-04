import {Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {values} from "./values";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {NgbModalRef} from "@ng-bootstrap/ng-bootstrap/modal/modal-ref";

@Component({
  selector: 'app-silverman',
  templateUrl: './silverman.component.html',
  styleUrls: ['./silverman.component.scss'],
})
export class SilvermanComponent implements OnInit {
  @ViewChild("silverman") silverman!: TemplateRef<any>;
  @Input() modal: any = null;
  @Output() valueEmitter: EventEmitter<any> = new EventEmitter();

  private modalRef!: NgbModalRef;
  formula: any[] = [];
  score: any = 0;
  constructor(private modalService: NgbModal,) { }

  ngOnInit(): void {
    if (this.modal)
      this.modal.shown.subscribe(() => {
        this.score = this.totalScore();
      });

    this.formula = values
  }

  selectRow(score: any, item: any) {
    item.selected = score;
    this.score = this.totalScore();
  }

  open() {
    this.modalRef = this.modalService.open(this.silverman, {
      size: "md",
      centered: true,
      keyboard: true,
      backdrop: "static",
    });

  }

  close() {
    const m = this.formula.map((f) => {
      return { name: f.name, selected: f.selected , label: f.label};
    });
    // this.valueEmitter.emit({ value: this.score, selected: m });

    // ici on es obliger de formuler directement vu qu'on peut pas stocker les selections pour le moment
    // console.log(m)
    this.valueEmitter.emit(`Le score de silverman est de ${this.score} : ${m.reduce(
        (text, item) =>
            text + item.label + ' - ' + item.selected.label + ', ' ,
        ''
    )}` )

    this.modalRef.close()
  }

  totalScore() {
    return this.formula.reduce(
        (total, item) =>
            total + (item.selected !== undefined ? item.selected.value : 0),
        0
    );
  }

  cancel() {
    this.modalRef.close()
  }

}
