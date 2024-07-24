import {Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Section} from "../../field.model";
import {CrStore} from "@stores/cr";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {paracliniqueFields} from './fields/paraclinique-fields';
import {traitementFields} from './fields/traitement-fields';
import {suiviFields} from './fields/suivi-fields';
import {extractFormControls, slugify} from "../../../../helpers/utils";

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
    @ViewChild("fullTextArea") fullTextArea!: TemplateRef<any>;

    @Input() control: FormControl = new FormControl();
    @Output() closeModal: EventEmitter<any> = new EventEmitter();
    selectedValues: any = null
    phrase = new FormControl(null, []);
    paracliniqueFields: Section[] = paracliniqueFields
    traitementFields: Section[] = traitementFields
    suiviFields: Section[] = suiviFields

    fg: FormGroup = new FormGroup({});

    // for tabs
    paracliniqueTabs: any = []

    constructor(
        private store: CrStore,
        private modalService: NgbModal,
    ) {
    }

    ngOnInit(): void {
        this.paracliniqueTabs = this.getGroupedFields(paracliniqueFields)
        // console.log(this.paracliniqueTabs)
    }

    sluTheName(name: string) {
        return slugify(name)
    }

    getGroupedFields(fields: Section[]) {
        const g = fields.filter((f) => f.groupName !== undefined)

        // add fields if groupname already exist
        const combined : any[any] = []
        g.forEach((field) => {
            const w = combined.find((c : any) => c.groupName === field.groupName)
            if (w === undefined) {
                combined.push(field)
            } else {
                const mT = w.template.concat(field.template)
                w.template = mT.filter((item: any, index: any) =>
                    mT.indexOf(item) == index
                )
                w.resume = w.resume.concat(' ',field.resume)
                combined.find((c : any) => c.groupName === field.groupName).resume.concat(field.resume)
            }
        })

        console.log(combined)

        return combined.map((w:any) => {
                return {
                    name: this.slugify(w.groupName!),
                    section: w
                }
            }
        )
    }

    open(name: TemplateRef<any>) {
        this.modalService.open(name, {
            size: "lg",
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


    translate(section: any) {
        return this.store.theMixer(this.fg.controls, section.template, section.resume) + '\n'
    }

    validate() {

        let validated = true
        for (const section of paracliniqueFields) {
            validated = this.store.checkErrors(section.template, this.fg.controls)
            if (!validated) break
        }
        if (validated) {
            let phrasologie = ''

            for (const section of paracliniqueFields) {
                phrasologie = phrasologie + this.translate(section)
            }
            this.phrase.setValue(phrasologie.trim())

            /* sending data to CR main group */
            const values = extractFormControls(this.fg)
            this.control.setValue({phrase: this.phrase.value, values: values})
            /* ******************************** */

            this.modalService.dismissAll()
        }
    }

    protected readonly slugify = slugify;
}
