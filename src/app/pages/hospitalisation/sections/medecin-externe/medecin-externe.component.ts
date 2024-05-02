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
  selector: "app-medecin-externe",
  templateUrl: "./medecin-externe.component.html",
  styleUrls: ["./medecin-externe.component.scss"],
})
export class MedecinExterneComponent implements OnInit {
  subscription: Subscription | undefined;

  medecins: any[] = [
    { text: "Mr Faust", value: "F0001" },
    { text: "Dr JP", value: "F0002" },
  ];
  type_ops: any[] = [
    { text: "Cardiaque", value: "Cardiaque" },
    { text: "Petite chirurgie", value: "Petite chirurgie" },
  ];

  today = new Date().toLocaleDateString("fr-FR");

  hospitalisation_id: number | null = null;

  externes : any[] = []

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private message: MessageService,
    private toast: ToastrService,
    private datePipe: DatePipe
  ) {}

  fGroup: FormGroup = new FormGroup({});
  type_op = new FormControl(
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
  comment = new FormControl(null, []);

  ngOnInit(): void {
    this.fGroup = new FormGroup({
      type_op: this.type_op,
      medecin: this.medecin,
      date_op: this.date_op,
      comment: this.comment,
    });

    this.subscription = this.hospitalisationStore.stateChanged
      .pipe(pairwise())
      .subscribe(([previous, current]) => {

        if (
          (this.medecins !== null && this.medecins.length === 0) ||
          JSON.stringify(previous.medecins) !== JSON.stringify(current.medecins)
        ) {
          this.medecins = current.medecins;
          this.hospitalisationStore.fetchExternes(current.hospitalisation.id);
        }

        if (
          (this.externes !== null && this.externes.length === 0) ||
          JSON.stringify(previous.externes) !== JSON.stringify(current.externes)
        ) {
          this.externes = current.externes
        }
        if (current.hospitalisation)
          this.hospitalisation_id = current.hospitalisation.id
          
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
        const data : any = {
          type_op: this.fGroup.value.type_op,
          date_op: new Date(this.fGroup.value.date_op),
          comments: this.fGroup.value.comment,
          med_ref: this.fGroup.value.medecin,
          hospit_id: this.hospitalisation_id,
        };

        this.hospitalisationStore.saveMedExterne(data).subscribe({
          next: (v) => {
            const medecin = this.medecins.find((m) => m.matricule === this.fGroup.value.medecin)
            data.id = v
            data.medecin = medecin
            this.toast.success("Hospitalisation", "Mise à jour effectuée");
            this.externes.push(data)
            this.fGroup.reset()
          },
          error: (e) => console.error(e),
        });
      }
    }
  }

  async deleteRecord(id: number) {
    const confirm = await this.message.confirmDialog(
      WarningMessages.SURE_TO_CONTINUE
    );

    if (confirm) {
      this.hospitalisationStore.deleteMedExterne(id).subscribe({
        next: (v) => {
            this.externes = this.externes.filter((e) => e.id !== id)
        }, 
        error: (e) => console.error(e),
      })
    }
      
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe;
  }
}
