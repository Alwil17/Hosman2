import { Component, OnInit } from "@angular/core";
import { Subject, takeUntil, Subscription, map, Observable } from "rxjs";
import { Lit } from "src/app/models/hospitalisation/lit";
import { Chambre } from "src/app/models/hospitalisation/chambre";
import { HospitalisationStore } from "@stores/hospitalisation";
import { MessageService } from "@services/messages/message.service";
import { ToastrService } from "ngx-toastr";
import { ActivatedRoute } from "@angular/router";
import { FormControl, FormGroup } from "@angular/forms";
import { DatePipe } from "@angular/common";
import { Sector } from "src/app/models/secretariat/shared/sector.model";
import { validateYupSchema } from "src/app/helpers/utils";
import * as Yup from "yup";
import { ErrorMessages, WarningMessages } from "src/app/helpers/messages";

@Component({
  selector: "app-hosp-admission",
  templateUrl: "./admission.component.html",
  styleUrls: ["./admission.component.scss"],
})
export class AdmissionComponent implements OnInit {
  subscription: Subscription | undefined;

  lits: Lit[] = [];
  all_lits: Lit[] = [];
  chambres: Chambre[] = [];
  sectors: Sector[] = [];
  genders = [
    { id: 1, text: "Masculin", short: "M" },
    { id: 2, text: "Féminin", short: "F" },
    { id: 3, text: "Indéterminé", short: "I" },
  ];
  statuts = [
    { id: 1, text: "Mr" },
    { id: 2, text: "Mme" },
    { id: 3, text: "Mlle" },
  ];
  consultation: any = null;
  patient: any = null;
  hasPatient: boolean = true;

  consultation_id: number = -1;
  hospitalisation_id: number | null = null;
  suivis: any | null = null;

  private destroy$ = new Subject<void>();

  today = new Date().toLocaleDateString("fr-FR");

  private chambreChanges: Subscription | undefined;

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private message: MessageService,
    private toast: ToastrService,
    private route: ActivatedRoute,
    private datePipe: DatePipe
  ) {}

  //controls
  admissionFormGroup: FormGroup = new FormGroup({});
  statut = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  gender = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  firstname = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  lastname = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  birth_date = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.date().required(ErrorMessages.REQUIRED))]
  );
  hospit_date = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.date().required(ErrorMessages.REQUIRED))]
  );
  hdm = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  motif = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  chambre = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.number().required(ErrorMessages.REQUIRED))]
  );
  sector = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  lit = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.number().required(ErrorMessages.REQUIRED))]
  );
  diagnostic = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  arrive = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  ngOnInit(): void {
    this.admissionFormGroup = new FormGroup({
      statut: this.statut,
      gender: this.gender,
      firstname: this.firstname,
      lastname: this.lastname,
      lastbirth_datename: this.birth_date,
      hospit_date: this.hospit_date,
      sector: this.sector,
      chambre: this.chambre,
      lit: this.lit,
      motif: this.motif,
      diagnostic: this.diagnostic,
      hdm: this.hdm,
      arrive: this.arrive,
    });

    this.subscription = this.hospitalisationStore.stateChanged.subscribe(
      (state) => {
        // console.log("''''''''''''''''''''''''''''''''''''''''''''''")
        // console.log(state);
        if (state) {
          if (state.patient) this.patient = state.patient;
          if (state.consultation) this.consultation = state.consultation;
          if (state.chambres) this.chambres = state.chambres;
          if (state.sectors) this.sectors = state.sectors;
          if (state.suivis) this.suivis = state.suivis;

          this.loadPatient(this.patient);

          if (
            state.hospitalisation !== undefined &&
            state.hospitalisation !== null
          ) {
            this.loadHospitalisation(state.hospitalisation);
          } else {
            this.loadHospitalisation(null);
          }
        }
      }
    );

    this.chambreChanges = this.admissionFormGroup
      .get("chambre")
      ?.valueChanges.subscribe((n) => {
        this.lit.setValue(null);
        this.lits = this.chambres!.find((c) => c.id === n)!["lits"];
      });
  }

  loadPatient(patient: any) {
    if (this.patient !== null) {
      this.firstname.setValue(patient["nom"]);
      this.lastname.setValue(patient["prenoms"]);
      this.gender.setValue(
        this.genders.find((g) => g["short"] == patient["sexe"])!.short
      );
      this.statut.setValue(
        patient["sexe"] == "M" ? this.statuts[0].text : this.statuts[2].text
      );
      this.birth_date.setValue(
        this.formatDate(patient["date_naissance"], "yyyy-MM-dd")
      );
    }
  }

  loadHospitalisation(hospitalisation: any) {
    // console.log(hospitalisation);

    if (this.patient !== null) {
      this.sector.setValue(this.consultation["secteur"]["code"]);
      this.motif.setValue(
        this.consultation.motifs
          .map(function (c: any) {
            return c["libelle"];
          })
          .join(",")
      );
      this.diagnostic.setValue(
        this.consultation.diagnostics
          .map(function (c: any) {
            return c["theCode"];
          })
          .join(",")
      );
      this.hdm.setValue(this.consultation["hdm"]);
    }

    if (hospitalisation != null) {
      this.hospitalisation_id = hospitalisation.id;
      this.motif.setValue(hospitalisation["motif"]["libelle"]);
      this.diagnostic.setValue(hospitalisation["diagnostic"]["theCode"]);
      this.arrive.setValue(hospitalisation["arrive"]);
      this.sector.setValue(hospitalisation["secteur_code"]);
      this.hospit_date.setValue(
        this.formatDate(hospitalisation["date_hospit"], "yyyy-MM-dd")
      );

      // auto load chambre and bed from suivis
      if (this.suivis !== null && this.suivis.length > 0) {
        const litSuivi = this.suivis.filter((i : any) => i['type'] === "lits")[0]
        const chambreSuivi = this.suivis.filter((i : any) => i['type'] === "chambres")[0]
        if (chambreSuivi !== null && litSuivi !== null) {
          this.chambre.setValue(chambreSuivi['type_id'])
          this.lit.setValue(litSuivi['type_id'])
        }
      }
    }
  }

  private formatDate(date: Date, format: string): string {
    return this.datePipe.transform(date, format) ?? "";
  }

  private markAllControlsAsTouched(): void {
    Object.keys(this.admissionFormGroup.controls).forEach((controlName) => {
      const control = this.admissionFormGroup.get(controlName);
      control!.markAsTouched();
    });
  }

  async confirmAction() {
    if (!this.admissionFormGroup.valid) {
      this.markAllControlsAsTouched();
    } else {
      const confirm = await this.message.confirmDialog(
        WarningMessages.SURE_TO_CONTINUE
      );
      if (confirm) {
        // console.log(this.admissionFormGroup.value);
        // console.log(this.patient);
        // console.log(this.consultation);

        const data = {
          id: this.hospitalisation_id,
          motif: this.admissionFormGroup.value.motif,
          diagnostic: this.admissionFormGroup.value.diagnostic,
          hdm: this.admissionFormGroup.value.hdm,
          patient_ref: this.patient.reference,
          secteur_code: this.admissionFormGroup.value.sector,
          consultation_ref: this.consultation.reference,
          date_hospit: new Date(this.admissionFormGroup.value.hospit_date),
          arrive: this.arrive.value,
        };

        // console.log(data)

        if (this.hospitalisation_id == null) {
          this.hospitalisationStore
            .saveHospitalisation(data, this.hospitalisation_id)
            .subscribe(
              (response) => {
                const responseData = response;
                this.toast.success("Hospitalisation", "Admission effectuée");
                this.hospitalisation_id = responseData;
                this.hospitalisationStore.updateStore(
                  { hospitalisation_id: responseData },
                  "SET ID"
                );

                // add chambre
                this.hospitalisationStore.commitSuivi({
                  type: "chambres",
                  type_id: this.chambre.value,
                  qte: 1,
                  apply_date: new Date(this.admissionFormGroup.value.hospit_date),
                  hospit_id: this.hospitalisation_id
                });

                // add lit
                this.hospitalisationStore.commitSuivi({
                  type: "lits",
                  type_id: this.lit.value,
                  qte: 1,
                  apply_date: new Date(this.admissionFormGroup.value.hospit_date),
                  hospit_id: this.hospitalisation_id
                });
                this.hospitalisationStore.fetchHospitalisation(responseData);

              },
              (error) => {
                console.error("POST request failed:", error);
              }
            );
        } else {
          this.hospitalisationStore
            .saveHospitalisation(data, this.hospitalisation_id)
            .subscribe((response) => {
              this.toast.success("Hospitalisation", "Mise à jour effectuée");
            });
        }
      }
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
    this.chambreChanges!.unsubscribe();
    this.subscription?.unsubscribe;
  }
}
