import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { MessageService } from "@services/messages/message.service";
import { HospitalisationStore } from "@stores/hospitalisation";
import { ToastrService } from "ngx-toastr";
import { Subscription, pairwise } from "rxjs";
import { Cim11Component } from "src/app/components/cim11/cim11.component";
import { ErrorMessages, WarningMessages } from "src/app/helpers/messages";
import { formatDate, hasStateChanges, markAllControlsAsTouched, validateYupSchema } from "src/app/helpers/utils";
import * as Yup from "yup";

@Component({
  selector: 'app-fiche-sortie',
  templateUrl: './fiche-sortie.component.html',
  styleUrls: ['./fiche-sortie.component.scss']
})
export class FicheSortieComponent implements OnInit {

  subscription: Subscription | undefined;

  enceinteOptions = [{text : "Oui", value: "Oui"}, {text : "Non", value: "Non"}]

  today = formatDate(new Date(), "yyyy-MM-dd");

  sortie: any = null;

  hospitalisation_id: number | null = null;

  hospitalisation : any = null

  diagnostics: FormControl[] = [];

  date_op = new FormControl(
    this.today,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  enceinte = new FormControl(
    "Non",
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  fGroup: FormGroup = new FormGroup({});  

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private message: MessageService,
    private toast: ToastrService,
    private router: Router,
    private modalService: NgbModal,
    private http: HttpClient,
  ) { }

  ngOnInit(): void {
    this.addDiag()
    this.fGroup = new FormGroup({
      date_op: this.date_op,
      enceinte: this.enceinte,
    });

    this.hospitalisationStore.getValue("hospitalisation")?.subscribe({
      next: (v) => {
        this.hospitalisation_id = v.id;
        this.hospitalisationStore.fetchSortie(v.id);
        this.hospitalisation = v
      },
    });

    this.subscription = this.hospitalisationStore.stateChanged
    .pipe(pairwise())
    .subscribe(([p, c]) => {
    
      if (c.sorties !== null && c.sorties !== undefined && c.sorties.length > 0) {
        this.sortie = c.sorties[0]
        this.setValues();
      }

      if (hasStateChanges(this.hospitalisation, p.hospitalisation, c.hospitalisation)) {
        this.hospitalisation = c.hospitalisation
      }
    });

  }

  setValues() {
    this.date_op.setValue(formatDate(this.sortie.date_op, "yyyy-MM-dd"));
    this.enceinte.setValue(this.sortie.motif);
    const diags = this.sortie.diagnostics

    this.diagnostics = []
    for (var d of diags) {
      this.addDiag(d.diagnostic)
    }
  }

  addDiag(value? : string){
    const f = new FormControl(
      value ?? "",
      [],
      [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
    );

    this.diagnostics.push(f)
    this.fGroup.addControl("diag_"+(this.diagnostics.length - 1).toString(),f)  
  }

  removeDiag(position : number) {
    this.fGroup.removeControl("diag_"+position.toString())
    this.diagnostics.splice(position, 1);
  }

  async confirmAction() {
    if (!this.fGroup.valid) {
      markAllControlsAsTouched(this.fGroup);
    } else {
      const confirm = await this.message.confirmDialog(
        WarningMessages.SURE_TO_CONTINUE
      );
      if (confirm) {
        
        const data: any = {
          enceinte: this.fGroup.value.enceinte,
          date_op: new Date(this.fGroup.value.date_op),
          hospit_id: this.hospitalisation_id,
          diagnostics: this.diagnostics.map((d) => { return { diagnostic : d.value.split(' - ')[0]}})
        };


        console.log(data)
        
        if (this.sortie !== null && this.sortie !== undefined && 'id' in this.sortie) {
          data.id = this.sortie.id
        }

        // this.hospitalisationStore.saveSortie(data).subscribe({
        //   next: (v) => {
        //     data.id = v;
        //     this.toast.success("Hospitalisation", "Enregistrement effectué");
        //     this.hospitalisationStore.fetchSortie(this.hospitalisation_id!);

            // this.hospitalisation.status = 1

            // this.hospitalisationStore.saveHospitalisation(this.hospitalisation, this.hospitalisation_id).subscribe({
            //   next: (v) => {
            //     data.id = v;
            //     this.toast.success("Hospitalisation", "Hospitalisation terminé. La chambre a été libéré");
            //     // this.hospitalisationStore.clearHospitalisation()
            //     this.router.navigate(["/hospitalisation/list"]);
            //   },
            //   error: (e) => console.error(e),
            // });
        //   },
        //   error: (e) => console.error(e),
        // });
      }
    }
  }


  
  openCIM(input: FormControl){
    const modalRef = this.modalService.open(Cim11Component, {
      size: "lg",
      centered: true,
      keyboard: false,
      backdrop: "static",
    });

    modalRef.componentInstance.modal = modalRef;
    modalRef.componentInstance.closeModal.subscribe((data: any) => {
      // return data
      // console.log(data)
      input.setValue(data.code + " - " + data.selectedText)
    });
  }


  ngOnDestroy(): void {
    this.subscription?.unsubscribe;
  }

}
