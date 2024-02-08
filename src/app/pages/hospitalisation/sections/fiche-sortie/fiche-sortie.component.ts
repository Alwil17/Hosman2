import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { MessageService } from "@services/messages/message.service";
import { HospitalisationStore } from "@stores/hospitalisation";
import { ToastrService } from "ngx-toastr";
import { Subscription, pairwise } from "rxjs";
import { ErrorMessages, WarningMessages } from "src/app/helpers/messages";
import { formatDate, markAllControlsAsTouched, validateYupSchema } from "src/app/helpers/utils";
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

  diagnostics: FormControl[] = [];

  date_op = new FormControl(
    this.today,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  enceinte = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  fGroup: FormGroup = new FormGroup({});  

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private message: MessageService,
    private toast: ToastrService,
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
      },
    });

    this.subscription = this.hospitalisationStore.stateChanged
    .pipe(pairwise())
    .subscribe(([p, c]) => {
    
      if (c.sorties !== null && c.sorties !== undefined && c.sorties.length > 0) {
        this.sortie = c.sorties[0]
        this.setValues();
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
      value,
      [],
      [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
    );
    this.diagnostics.push(f)
  }

  removeDiag(position : number) {
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
        
        // tobe continue
      }
    }
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe;
  }

}
