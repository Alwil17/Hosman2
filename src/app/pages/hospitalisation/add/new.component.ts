import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HospitalisationStore } from '@stores/hospitalisation';
import { Subject, map, takeUntil } from 'rxjs';

@Component({
  selector: 'app-beds',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.scss']
})
export class HospitHomeComponent implements OnInit {

  consultation$: any = null;
  patient: any = null;
  hasPatient: boolean = false;
  consultation_id: number = -1;
  hospitalisation_id: number | null = null;

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.consultation_id = params["consultation"];
      if (this.consultation_id !== undefined) {
        this.hospitalisationStore.fetchConsultation(this.consultation_id);
        this.hospitalisationStore.fetchSector()
        this.hospitalisationStore.fetchChambres()
        this.hospitalisationStore.fetchTabs()
      }
    });

  }

}
