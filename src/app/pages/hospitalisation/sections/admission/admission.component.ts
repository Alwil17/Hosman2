import { Component, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { Lit } from 'src/app/models/hospitalisation/lit';
import { Chambre } from 'src/app/models/hospitalisation/chambre';
import { ChambreStore } from 'src/app/stores/chambres-store';
import { LitStore  } from 'src/app/stores/lits-store';
import { MessageService } from '../../../../services/messages/confirmation-message.service'


@Component({
  selector: 'app-hosp-admission',
  templateUrl: './admission.component.html',
  styleUrls: ['./admission.component.scss']
})
export class AdmissionComponent implements OnInit {

  constructor(private chambreStore: ChambreStore, private litStore : LitStore, private message: MessageService) {}

  lits : Lit[] = [];
  chambres : Chambre[] = [];
  sectors = []

  private destroy$ = new Subject<void>();


  today = new Date().toLocaleDateString("fr-FR");

  ngOnInit(): void {
    this.litStore.getLitsObservable()
    .pipe(takeUntil(this.destroy$))
    .subscribe((lits: Lit[]) => {
      this.lits = lits;
    });

    this.chambreStore.getChambresObservable()
    .pipe(takeUntil(this.destroy$))
    .subscribe((chambres: Chambre[]) => {
      this.chambres = chambres;
    });
  

    this.getData()
  }

  getData(){
    this.chambreStore.getAll()  
    this.litStore.getAll()  
  }

async confirmAction() {
    const confirm = await this.message.openConfirmationDialog('Custom confirmation message');
    console.log(confirm)
    // if (confirm) {
    //   // Continue with your action
    //   console.log('Action confirmed');
    // } else {
    //   // Handle the case when the user clicks "No" or closes the dialog
    //   console.log('Action canceled');
    // }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
