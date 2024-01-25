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
  selector: 'app-fiche-scam',
  templateUrl: './fiche-scam.component.html',
  styleUrls: ['./fiche-scam.component.scss']
})
export class FicheScamComponent implements OnInit {

  showControl = new FormControl(false); 
  displayOptions = [{text : "Oui", value: true}, {text : "Non", value: false}]

  subscription: Subscription | undefined;

  today = formatDate(new Date(), "yyyy-MM-dd");

  scam: any = null;

  hospitalisation_id: number | null = null;

  date_op = new FormControl(
    this.today,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  motif = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  ordonnance = new FormControl(false, []);
  decharge = new FormControl(false, []);
  comment = new FormControl(null, []);

  fGroup: FormGroup = new FormGroup({});

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private message: MessageService,
    private toast: ToastrService,
  ) { }

  ngOnInit(): void {
    this.fGroup = new FormGroup({
      date_op: this.date_op,
      motif: this.motif,
      comment: this.comment,
      ordonnance: this.ordonnance,
      decharge: this.decharge,
    });

    this.hospitalisationStore.getValue("hospitalisation")?.subscribe({
      next: (v) => {
        this.hospitalisation_id = v.id;
        this.hospitalisationStore.fetchScam(v.id);
      },
    });

    this.subscription = this.hospitalisationStore.stateChanged
    .pipe(pairwise())
    .subscribe(([p, c]) => {
    

      if (c.scams !== null && c.scams !== undefined && c.scams.length > 0) {
        this.scam = c.scams[0]
        this.setValues();
      }

    });
  }

  setValues() {
    this.showControl.setValue(true);

    this.date_op.setValue(formatDate(this.scam.date_op, "yyyy-MM-dd"));
    this.motif.setValue(this.scam.motif);
    this.comment.setValue(this.scam.comments);
    this.ordonnance.setValue(this.scam.ordonnance === 1);
    this.decharge.setValue(this.scam.decharge === 1);
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
          date_op: new Date(this.fGroup.value.date_op),
          motif: this.fGroup.value.motif,
          comments: this.fGroup.value.comment,
          ordonnance: this.ordonnance.value === true ? 1 : 0,
          decharge: this.decharge.value === true ? 1 : 0,
          hospit_id: this.hospitalisation_id,
        };

        if (this.scam !== null && this.scam !== undefined && 'id' in this.scam) {
          data.id = this.scam.id
        }

        this.hospitalisationStore.saveScams(data).subscribe({
          next: (v) => {
            data.id = v;
            console.log(data)
            this.toast.success("Hospitalisation", "Enregistrement effectué");
            this.hospitalisationStore.fetchScam(this.hospitalisation_id!);
          },
          error: (e) => console.error(e),
        });
      }
    }
  }

}
