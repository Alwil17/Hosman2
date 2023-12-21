import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-fiche-adresse',
  templateUrl: './fiche-adresse.component.html',
  styleUrls: ['./fiche-adresse.component.scss']
})
export class FicheAdresseComponent implements OnInit {

  showControl = new FormControl(false); 
  displayOptions = [{text : "Oui", value: true}, {text : "Non", value: false}]

  constructor() { }

  ngOnInit(): void {
  }

}
