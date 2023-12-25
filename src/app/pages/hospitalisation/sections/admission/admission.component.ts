import { Component, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { Lit } from 'src/app/models/hospitalisation/lit';
import { Chambre } from 'src/app/models/hospitalisation/chambre';
import { ChambreStore } from '@stores/chambres-store';
import { LitStore  } from '@stores/lits-store';
import { HospitalisationStore } from '@stores/hospitalisation';
import { MessageService } from '@services/messages/message.service'
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from "@angular/router";


@Component({
  selector: 'app-hosp-admission',
  templateUrl: './admission.component.html',
  styleUrls: ['./admission.component.scss']
})
export class AdmissionComponent implements OnInit {

  constructor(private chambreStore: ChambreStore, private litStore : LitStore,
              private hospitalisationStore : HospitalisationStore,
              private message: MessageService, private toast: ToastrService,
              private route: ActivatedRoute) {}

  lits : Lit[] = [];
  chambres : Chambre[] = [];
  sectors = []
  consultation = null;

  private destroy$ = new Subject<void>();


  today = new Date().toLocaleDateString("fr-FR");

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      console.log(params)
    });

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
    this.hospitalisationStore.fetchConsultation(100)
  }

async confirmAction() {
  // this.toast.success('Hello world!', 'Toastr fun!');
  //   const confirm = await this.message.confirmDialog('Custom confirmation message');
  //   console.log(confirm)
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
