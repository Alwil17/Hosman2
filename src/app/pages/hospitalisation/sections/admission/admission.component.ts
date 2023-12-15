import { Component, OnInit } from '@angular/core';
import { Patient } from 'src/app/models/secretariat/patients/patient.model';

@Component({
  selector: 'app-hosp-admission',
  templateUrl: './admission.component.html',
  styleUrls: ['./admission.component.scss']
})
export class AdmissionComponent implements OnInit {

  
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

  today = new Date().toLocaleDateString("fr-ca");

  constructor() { }

  ngOnInit(): void {
  }

}
