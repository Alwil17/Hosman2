import { Component, OnInit } from '@angular/core';
import { calculateExactAge } from 'src/app/helpers/age-calculator';
import { Patient } from 'src/app/models/secretariat/patients/patient.model';
import { PatientService } from 'src/app/services/secretariat/patients/patient.service';

@Component({
  selector: 'app-beds',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.scss']
})
export class NewBedComponent implements OnInit {

  sectors = [
    { id: 0, text: 'MEDECINE INTERNE ET GENERALE' },
    { id: 1, text: 'PEDIATRIE' },
    { id: 2, text: 'CARDIOLOGIE' },
    { id: 3, text: 'NEUROLOGIE' }
  ]

patients: Patient[] = []

  lits = [
    {id: "1", name: "Lit P, Chambre 01"},
    {id: "1", name: "Lit F, Chambre 01"},
    {id: "1", name: "Lit P, Chambre 02"},
    {id: "1", name: "Lit F, Chambre 02"},
    {id: "1", name: "Lit P, Chambre 03"},
    {id: "1", name: "Lit F, Chambre 03"},
  ]

  patient : any = null

  constructor() {
    
   }

  ngOnInit(): void {
  }

}
