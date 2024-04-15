import { Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import * as ECT from '@whoicd/icd11ect';

@Component({
  selector: 'app-cim11',
  templateUrl: './cim11.component.html',
  styleUrls: ['./cim11.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class Cim11Component implements OnInit {

  @Input() modal: any = null;
  @Output() closeModal: EventEmitter<any> = new EventEmitter();
  constructor(public activeModal: NgbActiveModal, ) {}

  ngOnInit(): void {

        const mySettings = {
              apiServerUrl: "https://icd11restapi-developer-test.azurewebsites.net",
              language: "fr"    
          };
  
          const myCallbacks = {
              selectedEntityFunction: (selectedEntity: any) => { 
                  // console.log(selectedEntity)
                  this.submit(selectedEntity)
                  ECT.Handler.clear("1")    
              }
          };
  
          ECT.Handler.configure(mySettings, myCallbacks);

  }

  submit(data: any) {
    this.closeModal.emit(data);
    this.activeModal.close();
  }

  close() {
    this.activeModal.close();
  }

}
