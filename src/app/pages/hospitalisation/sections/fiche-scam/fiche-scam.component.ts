import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-fiche-scam',
  templateUrl: './fiche-scam.component.html',
  styleUrls: ['./fiche-scam.component.scss']
})
export class FicheScamComponent implements OnInit {

  showControl = new FormControl(false); 
  displayOptions = [{text : "Oui", value: true}, {text : "Non", value: false}]

  constructor() { }

  ngOnInit(): void {
  }

}
