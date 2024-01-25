import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { MessageService } from "@services/messages/message.service";
import { HospitalisationStore } from "@stores/hospitalisation";
import { ToastrService } from "ngx-toastr";
import { Subscription, pairwise } from "rxjs";
import { ErrorMessages, WarningMessages } from "src/app/helpers/messages";
import { formatDate, validateYupSchema } from "src/app/helpers/utils";
import * as Yup from "yup";

@Component({
  selector: 'app-fiche-transfuse',
  templateUrl: './fiche-transfuse.component.html',
  styleUrls: ['./fiche-transfuse.component.scss']
})
export class FicheTransfuseComponent implements OnInit {

  showControl = new FormControl(false); 
  displayOptions = [{text : "Oui", value: true}, {text : "Non", value: false}]

  patients: any[] = []

  subscription: Subscription | undefined;

  today = formatDate(new Date(), "yyyy-MM-dd");

  hospitalisation_id: number | null = null;

  transfused: any = null;

  date_op = new FormControl(
    this.today,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  provenance = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  donneur = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  receveur = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  hemoglobine = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  motif = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

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
      provenance: this.provenance,
      donneur: this.donneur,
      receveur: this.receveur,
      hemoglobine: this.hemoglobine,
      motif: this.motif,
      comment: this.comment,
    });

    this.hospitalisationStore.getValue("hospitalisation")?.subscribe({
      next: (v) => {
        this.hospitalisation_id = v.id;
        this.hospitalisationStore.fetchTransfused(v.id);
      },
    });

    this.subscription = this.hospitalisationStore.stateChanged
      .pipe(pairwise())
      .subscribe(([p, c]) => {
        if (
          (this.patients !== undefined && this.patients.length === 0) ||
          JSON.stringify(p.medecins) !== JSON.stringify(c.medecins)
        ) {
          this.patients = c.patients;
        }

        // this.transfused = c.transfused
        if (c.transfused !== null && c.transfused !== undefined && 
          c.transfused.length > 0) {
          this.transfused = c.transfused[0]
          this.setValues();
        }

      });

      this.hospitalisationStore.fetchPatients();

  }

  setValues() {
    this.showControl.setValue(true);

    this.donneur.setValue(this.transfused.donneur);
    this.receveur.setValue(this.transfused.receveur);
    this.provenance.setValue(this.transfused.provenance);
    this.hemoglobine.setValue(this.transfused.hemoglobine);
    this.date_op.setValue(formatDate(this.transfused.date_op, "yyyy-MM-dd"));
    this.motif.setValue(this.transfused.motif);
    this.comment.setValue(this.transfused.comments);
   
  }

  private markAllControlsAsTouched(): void {
    Object.keys(this.fGroup.controls).forEach((controlName) => {
      const control = this.fGroup.get(controlName);
      control!.markAsTouched();
    });
  }

  async confirmAction() {
    if (!this.fGroup.valid) {
      this.markAllControlsAsTouched();
    } else {
      const confirm = await this.message.confirmDialog(
        WarningMessages.SURE_TO_CONTINUE
      );
      if (confirm) {

        const data: any = {
          provenance: this.fGroup.value.provenance,
          donneur_ref: this.fGroup.value.donneur,
          receveur_ref: this.fGroup.value.receveur,
          hemoglobine: this.fGroup.value.hemoglobine,
          date_op: new Date(this.fGroup.value.date_op),
          motif: this.fGroup.value.motif,
          comments: this.fGroup.value.comment,
          hospit_id: this.hospitalisation_id,
        };

        if (this.transfused !== null && this.transfused !== undefined &&
           'id' in this.transfused) {
          data.id = this.transfused.id
        }

        this.hospitalisationStore.saveTransfused(data).subscribe({
          next: (v) => {
            data.id = v;
            this.toast.success("Hospitalisation", "Enregistrement effectué");
            this.hospitalisationStore.fetchTransfused(this.hospitalisation_id!);
          },
          error: (e) => console.error(e),
        });

      }
    }
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe;
  }

}
