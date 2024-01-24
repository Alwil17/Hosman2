import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { MessageService } from "@services/messages/message.service";
import { HospitalisationStore } from "@stores/hospitalisation";
import { ToastrService } from "ngx-toastr";
import { Subscription, pairwise } from "rxjs";
import { ErrorMessages, WarningMessages } from "src/app/helpers/messages";
import { validateYupSchema } from "src/app/helpers/utils";
import * as Yup from "yup";

@Component({
  selector: "app-intervention-chirurgicale",
  templateUrl: "./intervention-chirurgicale.component.html",
  styleUrls: ["./intervention-chirurgicale.component.scss"],
})
export class InterventionChirurgicaleComponent implements OnInit {
  subscription: Subscription | undefined;

  medecins: any[] = [
    { text: "Mr Faust", value: "F0001" },
    { text: "Dr JP", value: "F0002" },
  ];

  type_meds: any[] = [
    { text: "Interne", value: "interne" },
    { text: "Externe", value: "extrene" },
  ];

  today = new Date().toLocaleDateString("fr-FR");

  hospitalisation_id: number | null = null;

  fGroup: FormGroup = new FormGroup({});

  interventions: any[] = [];

  type_med = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  medecin = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  date_op = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  coefficient = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  title = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  comment = new FormControl(null, []);
  frais = new FormControl(false, []);

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private message: MessageService,
    private toast: ToastrService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.fGroup = new FormGroup({
      type_med: this.type_med,
      medecin: this.medecin,
      date_op: this.date_op,
      coefficient: this.coefficient,
      title: this.title,
      comment: this.comment,
      frais: this.frais,
    });

    this.subscription = this.hospitalisationStore.stateChanged
      .pipe(pairwise())
      .subscribe(([previous, current]) => {
        if (
          (this.medecins !== null && this.medecins.length === 0) ||
          JSON.stringify(previous.medecins) !== JSON.stringify(current.medecins)
        ) {
          this.medecins = current.medecins;
          this.hospitalisationStore.fetchInterventions(current.hospitalisation.id);
        }

        if (
          (this.interventions !== undefined && this.interventions !== null && this.interventions.length === 0) ||
          JSON.stringify(previous.interventions) !==
            JSON.stringify(current.interventions)
        ) {
          this.interventions = current.interventions;
        }
        this.hospitalisation_id = current.hospitalisation.id;
      });

    this.hospitalisationStore.fetchMedecins();
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
          date_op: new Date(this.fGroup.value.date_op),
          med_type: this.fGroup.value.type_med,
          med_ref: this.fGroup.value.medecin,
          coef: this.fGroup.value.coefficient,
          comments: this.fGroup.value.comment,
          title: this.fGroup.value.title,
          hospit_id: this.hospitalisation_id,
          frais: this.frais.value === true ? 1 : 0
        };

        this.hospitalisationStore.saveIntervention(data).subscribe({
          next: (v) => {
            const medecin = this.medecins.find(
              (m) => m.matricule === this.fGroup.value.medecin
            );
            data.id = v;
            data.medecin = medecin;
            this.toast.success("Hospitalisation", "Mise à jour effectuée");
            this.interventions.push(data);
            this.fGroup.reset();
            this.type_med.setValue(null)
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
