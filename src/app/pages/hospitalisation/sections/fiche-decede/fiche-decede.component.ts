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
  selector: 'app-fiche-decede',
  templateUrl: './fiche-decede.component.html',
  styleUrls: ['./fiche-decede.component.scss']
})
export class FicheDecedeComponent implements OnInit {

  showControl = new FormControl(false); 
  displayOptions = [{text : "Oui", value: true}, {text : "Non", value: false}]

  subscription: Subscription | undefined;

  today = formatDate(new Date(), "yyyy-MM-dd");

  deceded: any = null;

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

  reanimation = new FormControl(false, []);
  deces = new FormControl(false, []);
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
      reanimation: this.reanimation,
      deces: this.deces,
    });

    this.hospitalisationStore.getValue("hospitalisation")?.subscribe({
      next: (v) => {
        this.hospitalisation_id = v.id;
        this.hospitalisationStore.fetchDedeced(v.id);
      },
    });

    this.subscription = this.hospitalisationStore.stateChanged
    .pipe(pairwise())
    .subscribe(([p, c]) => {
    

      if (c.deceded !== null && c.deceded !== undefined && c.deceded.length > 0) {
        this.deceded = c.deceded[0]
        this.setValues();
      }

    });
  }

  setValues() {
    this.showControl.setValue(true);

    this.date_op.setValue(formatDate(this.deceded.date_op, "yyyy-MM-dd"));
    this.motif.setValue(this.deceded.motif);
    this.comment.setValue(this.deceded.comments);
    this.reanimation.setValue(this.deceded.reanimation === 1);
    this.deces.setValue(this.deceded.deces === 1);
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
          reanimation: this.reanimation.value === true ? 1 : 0,
          deces: this.deces.value === true ? 1 : 0,
          hospit_id: this.hospitalisation_id,
        };

        if (this.deceded !== null && this.deceded !== undefined && 'id' in this.deceded) {
          data.id = this.deceded.id
        }

        this.hospitalisationStore.saveDeceded(data).subscribe({
          next: (v) => {
            data.id = v;
            console.log(data)
            this.toast.success("Hospitalisation", "Enregistrement effectué");
            this.hospitalisationStore.fetchDedeced(this.hospitalisation_id!);
          },
          error: (e) => console.error(e),
        });

      }
    }
  }




}
