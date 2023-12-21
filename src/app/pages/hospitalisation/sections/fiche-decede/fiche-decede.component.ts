import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-fiche-decede',
  templateUrl: './fiche-decede.component.html',
  styleUrls: ['./fiche-decede.component.scss']
})
export class FicheDecedeComponent implements OnInit {

  showControl = new FormControl(false); 
  displayOptions = [{text : "Oui", value: true}, {text : "Non", value: false}]

  constructor() { }

  ngOnInit(): void {
  }

}
