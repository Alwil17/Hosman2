import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-fiche-transfuse',
  templateUrl: './fiche-transfuse.component.html',
  styleUrls: ['./fiche-transfuse.component.scss']
})
export class FicheTransfuseComponent implements OnInit {

  showControl = new FormControl(false); 
  displayOptions = [{text : "Oui", value: true}, {text : "Non", value: false}]

  constructor() { }

  ngOnInit(): void {
  }

}
