import {ChangeDetectorRef, Component, OnInit, TemplateRef, ViewChild, EventEmitter, Output, Input} from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MessageService } from '@services/messages/message.service';
import { CrStore } from '@stores/cr';
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { fields } from "./fields";
import {Section} from "../../field.model";
import {extractFormControls} from "../../../../helpers/utils";

@Component({
  selector: 'cr-examen-baby',
  templateUrl: './examenbaby.component.html',
  styleUrls: ['./examenbaby.component.scss']
})
export class ExamenbabyComponent implements OnInit {
  @ViewChild("examenbaby") modal!: TemplateRef<any>;
  @ViewChild("fullTextArea") fullTextArea!: TemplateRef<any>;

  @Input() control: FormControl = new FormControl();
  @Output() closeModal: EventEmitter<any> = new EventEmitter();

  selectedValues : any = null
  phrase = new FormControl(null, []);
  fields :  Section[] = fields

  fg: FormGroup = new FormGroup({});
  constructor(
      private store: CrStore,
      private message: MessageService,
      private modalService: NgbModal,
      private cdr: ChangeDetectorRef,
  ) { }

  ngOnInit(): void {
  }

  open() {
    this.modalService.open(this.modal, {
      size: "md",
      centered: true,
      keyboard: true,
      backdrop: "static",
    });

  }

  openFullModal() {
    this.modalService.open(this.fullTextArea, {
      size: "xl",
      centered: true,
      keyboard: true,
      backdrop: "static",
    });

  }


  translate(section : any){
    return this.store.theMixer(this.fg.controls, section.template, section.resume) + '\n'
  }

  validate(){

    let phrasologie = ''
    for (const section of this.fields) {
      phrasologie = phrasologie + this.translate(section)
    }
    this.phrase.setValue(phrasologie.trim())

    /* sending data to CR main group */
    const values = extractFormControls(this.fg)
    this.control.setValue({phrase : this.phrase.value, values : values})
    /* ******************************** */

    this.modalService.dismissAll()
  }


}
