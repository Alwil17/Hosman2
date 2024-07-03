import {Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Section} from "../../field.model";
import {CrStore} from "@stores/cr";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {paracliniqueFields} from './fields/paraclinique-fields';
import {traitementFields} from './fields/traitement-fields';
import {suiviFields} from './fields/suivi-fields';
import {extractFormControls} from "../../../../helpers/utils";
import {fields} from "../accouchement/fields";

@Component({
    selector: 'cr-paracliniques',
    templateUrl: './paracliniques.component.html',
    styleUrls: ['./paracliniques.component.scss']
})
export class ParacliniquesComponent implements OnInit {
    // modals
    @ViewChild("paraclinique") paracliniqueModal!: TemplateRef<any>;
    @ViewChild("traitement") traitementModal!: TemplateRef<any>;
    @ViewChild("suivi") suiviModal!: TemplateRef<any>;


    @Input() control: FormControl = new FormControl();
    @Output() closeModal: EventEmitter<any> = new EventEmitter();
    selectedValues: any = null
    phrase = new FormControl(null, []);
    paracliniqueFields: Section[] = paracliniqueFields
    traitementFields: Section[] = traitementFields
    suiviFields: Section[] = suiviFields

    fg: FormGroup = new FormGroup({});

    constructor(
        private store: CrStore,
        private modalService: NgbModal,
    ) {
    }

    ngOnInit(): void {
    }

    open(name: TemplateRef<any>) {
        this.modalService.open(name, {
            size: "lg",
            centered: true,
            keyboard: true,
            backdrop: "static",
        });
    }

    translate(section : any){
        return this.store.theMixer(this.fg.controls, section.template, section.resume) + '\n'
    }

    validate(){

        // let phrasologie = ''
        // for (const section of this.fields) {
        //     phrasologie = phrasologie + this.translate(section)
        // }
        // this.phrase.setValue(phrasologie.trim())
        //
        // /* sending data to CR main group */
        // const values = extractFormControls(this.fg)
        // this.control.setValue({phrase : this.phrase.value, values : values})
        // /* ******************************** */
        //
        // this.modalService.dismissAll()
    }

}
