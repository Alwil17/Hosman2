import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { Addressed } from 'src/app/models/medical-base/submodules/atrd/adressed.model';
import { Deceded } from 'src/app/models/medical-base/submodules/atrd/deceded.model';
import { Refused } from 'src/app/models/medical-base/submodules/atrd/refused.model';
import { Transfered } from 'src/app/models/medical-base/submodules/atrd/transfered.model';

@Component({
  selector: 'app-atrd-form-modal',
  templateUrl: './atrd-form-modal.component.html',
  styleUrls: ['./atrd-form-modal.component.scss']
})
export class AtrdFormModalComponent implements OnInit {

  consultationId$ = new BehaviorSubject<number | null>(null);

  @Output()
  addressedRegistering = new EventEmitter<boolean>();
  transferedRegistering = new EventEmitter<boolean>();
  refusedRegistering = new EventEmitter<boolean>();
  decededRegistering = new EventEmitter<boolean>();

  addressed$ = new Subject<Addressed>();
  @Input()
  addressed?: Addressed;


  transfered$ = new Subject<Transfered>();
  @Input()
  transfered?: Transfered;

  refused$ = new Subject<Refused>();
  @Input()
  refused?: Refused;

  deceded$ = new Subject<Deceded>();
  @Input()
  deceded?: Deceded;

  constructor() { }

  ngOnInit(): void {
    //this.atrdService.updateStore({ consultation_id: this.consultationId$ }, "CONSULTATION_ID")
  }

  emitAddressedRegistration() {
    this.addressedRegistering.emit(true);
  }

  emitTransferedRegistration() {
    this.transferedRegistering.emit(true);
  }
  
  emitRefusedRegistration() {
    this.refusedRegistering.emit(true);
  }
  
  emitDecededRegistration() {
    this.decededRegistering.emit(true);
  }
}
