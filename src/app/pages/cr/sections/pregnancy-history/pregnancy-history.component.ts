import {Component, OnInit, TemplateRef, ViewChild, EventEmitter, Output, Input} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {CrStore} from '@stores/cr';
import {extractFormControls} from "../../../../helpers/utils";
import {Section} from "../../field.model";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {fields} from './fields';

@Component({
    selector: 'cr-pregnancy-history',
    templateUrl: './pregnancy-history.component.html',
    styleUrls: ['./pregnancy-history.component.scss']
})
export class PregnancyHistoryComponent implements OnInit {
    @ViewChild("pregnancyhistory") modal!: TemplateRef<any>;
    @Input() control: FormControl = new FormControl();
    @Output() closeModal: EventEmitter<any> = new EventEmitter();

    selectedValues: any = null
    phrase = new FormControl(null, []);
    fields: Section[] = fields

    fg: FormGroup = new FormGroup({});

    constructor(
        private store: CrStore,
        private modalService: NgbModal,
    ) {
    }

    ngOnInit(): void {
        // this.fg.valueChanges.subscribe((data) => {
        //     if (this.fg.controls['nbh'] !== undefined && this.fg.controls['nbh'].value !== null) {
        //         this.fg.controls['nbh'].valueChanges.subscribe((data) => {
        //             console.log('---------')
        //             console.log(data)
        //             this.fields[this.fields.length - 1].template = []
        //             for (let i = 1; i <= data; i++) {
        //                 if (this.fg.controls['nbh'+i] === undefined) {
        //                     this.fields[this.fields.length - 1].template.push(
        //                         {
        //                             name: 'nbh_d_l' + i,
        //                             label: "Hospitalisation " + i,
        //                             type: 'label',
        //                             col: 4,
        //                             show: true,
        //                         },
        //                     )
        //                     this.fields[this.fields.length - 1].template.push(
        //                         {
        //                             name: 'nbh_d' + i,
        //                             type: "input",
        //                             col: 8,
        //                             show: true,
        //                         },
        //                     )
        //                 }
        //             }
        //         })
        //     }
        //
        // })
    }

    open() {
        this.modalService.open(this.modal, {
            size: "md",
            centered: true,
            keyboard: true,
            backdrop: "static",
        });

    }

    translate(section: any) {
        return this.store.theMixer(this.fg.controls, section.template, section.resume) + '\n'
    }

    validate() {

        let phrasologie = ''
        for (const section of this.fields) {
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
