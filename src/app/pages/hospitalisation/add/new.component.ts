import { Component, OnInit } from '@angular/core';
import { ChambreStore } from 'src/app/stores/chambres-store';

@Component({
  selector: 'app-beds',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.scss']
})
export class NewBedComponent implements OnInit {

  constructor(private chambreStore: ChambreStore) {}

  state$ = this.chambreStore.stateChanged;

  ngOnInit(): void {
    this.getData()
  }

  getData(){
    this.chambreStore.getAll()  
  }

}
