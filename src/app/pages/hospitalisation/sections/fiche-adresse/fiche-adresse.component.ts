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
  selector: "app-fiche-adresse",
  templateUrl: "./fiche-adresse.component.html",
  styleUrls: ["./fiche-adresse.component.scss"],
})
export class FicheAdresseComponent implements OnInit {
  showControl = new FormControl(false);
  displayOptions = [
    { text: "Oui", value: true },
    { text: "Non", value: false },
  ];

  medecins: any[] = [
    { text: "Mr Faust", value: "F0001" },
    { text: "Dr JP", value: "F0002" },
  ];

  subscription: Subscription | undefined;

  today = formatDate(new Date(), "yyyy-MM-dd");

  hospitalisation_id: number | null = null;

  adressed: any = null;

  medecin = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  specialite = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  institution = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  transport = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

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

  comment = new FormControl(null, []);
  lettre = new FormControl(false, []);
  report = new FormControl(false, []);

  fGroup: FormGroup = new FormGroup({});

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private message: MessageService,
    private toast: ToastrService,
  ) {}

  ngOnInit(): void {
    this.fGroup = new FormGroup({
      medecin: this.medecin,
      specialite: this.specialite,
      institution: this.institution,
      transport: this.transport,
      date_op: this.date_op,
      motif: this.motif,
      comment: this.comment,
      lettre: this.lettre,
      rapport: this.report,
    });

    this.hospitalisationStore.getValue("hospitalisation")?.subscribe({
      next: (v) => {
        this.hospitalisation_id = v.id;
        this.hospitalisationStore.fetchAdressed(v.id);
      },
    });

    this.subscription = this.hospitalisationStore.stateChanged
      .pipe(pairwise())
      .subscribe(([previous, current]) => {
        if (
          (this.medecins !== null && this.medecins.length === 0) ||
          JSON.stringify(previous.medecins) !== JSON.stringify(current.medecins)
        ) {
          this.medecins = current.medecins;
        }

        this.adressed = current.addressed
        if (current.addressed !== null && current.addressed !== undefined && current.addressed.length > 0) {
          this.adressed = current.addressed[0]
          this.setValues();
        }

      });

    this.hospitalisationStore.fetchMedecins();
  }

  setValues() {
    this.showControl.setValue(true);

    this.medecin.setValue(this.adressed.med_ref);
    this.specialite.setValue(this.adressed.specialite);
    this.institution.setValue(this.adressed.institution);
    this.transport.setValue(this.adressed.transport);
    this.date_op.setValue(formatDate(this.adressed.date_op, "yyyy-MM-dd"));
    this.motif.setValue(this.adressed.motif);
    this.comment.setValue(this.adressed.comments);
    this.lettre.setValue(this.adressed.medical_letter === 1);
    this.report.setValue(this.adressed.medical_report === 1);
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
          med_ref: this.fGroup.value.medecin,
          specialite: this.fGroup.value.specialite,
          institution: this.fGroup.value.institution,
          transport: this.fGroup.value.transport,
          date_op: new Date(this.fGroup.value.date_op),
          motif: this.fGroup.value.motif,
          comments: this.fGroup.value.comment,
          medical_letter: this.lettre.value === true ? 1 : 0,
          medical_report: this.report.value === true ? 1 : 0,
          hospit_id: this.hospitalisation_id,
        };

        if (this.adressed !== null && this.adressed !== undefined && 'id' in this.adressed) {
          data.id = this.adressed.id
        }

        this.hospitalisationStore.saveAdressed(data).subscribe({
          next: (v) => {
            data.id = v;
            this.toast.success("Hospitalisation", "Enregistrement effectué");
            this.hospitalisationStore.fetchAdressed(this.hospitalisation_id!);
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
