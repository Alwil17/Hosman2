import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import {
  Subject,
  takeUntil,
  Subscription,
  map,
  Observable,
  distinctUntilChanged,
} from "rxjs";
import { Lit } from "src/app/models/hospitalisation/lit";
import { Chambre } from "src/app/models/hospitalisation/chambre";
import { HospitalisationStore } from "@stores/hospitalisation";
import { MessageService } from "@services/messages/message.service";
import { ToastrService } from "ngx-toastr";
import { ActivatedRoute, Router } from "@angular/router";
import { FormControl, FormGroup } from "@angular/forms";
import { DatePipe } from "@angular/common";
import { Sector } from "src/app/models/secretariat/shared/sector.model";
import { formatDate, validateYupSchema } from "src/app/helpers/utils";
import * as Yup from "yup";
import { ErrorMessages, WarningMessages } from "src/app/helpers/messages";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { GlasgowComponent } from "src/app/components/glasgow/glasgow.component";

@Component({
  selector: "app-hosp-admission",
  templateUrl: "./admission.component.html",
  styleUrls: ["./admission.component.scss"],
})
export class AdmissionComponent implements OnInit {
  @ViewChild("constants") constants!: TemplateRef<any>;
  subscription: Subscription | undefined;

  modalReference: any;

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
  etats = [
    { value: "Etat général conservé", label: "Conservé" },
    { value: "Etat général altéré", label: "Altéré" },
    { value: "L'etat général est mauvais", label: "Mauvais" },
    { value: "L'etat général est médiocre", label: "Médiocre" },
    { value: "L'etat général est passable", label: "Passable" },
    { value: "L'etat général est très mauvais", label: "Très mauvais" },
    { value: "L'etat général est très médiocre", label: "Très médiocre" },
  ];
  consciences = [
    { value: "Normale", label: "Normale" },
    { value: "La conscience est abolie", label: "Abolie" },
    { value: "La conscience est altérée", label: "Altérée" },
    { value: "La conscience est très altérée", label: "Très altérée" },
  ];

  consultation: any = null;
  patient: any = null;
  hasPatient: boolean = true;

  consultation_id: number = -1;
  hospitalisation_id: number | null = null;
  suivis: any | null = null;
  hospitalisation: any = null;

  freeBeds: any[] = [];

  private destroy$ = new Subject<void>();

  today = formatDate(new Date(), "yyyy-MM-dd HH:mm");

  glasgow: any = 0;

  canOpenGlasgow: boolean = false;

  private chambreChanges: Subscription | undefined;

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
    this.today,
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

  temperature = new FormControl(null, [], []);
  poids = new FormControl(null, [], []);
  taille = new FormControl(null, [], []);
  pc = new FormControl(null, [], []);
  fc = new FormControl(null, [], []);
  fr = new FormControl(null, [], []);
  ta = new FormControl(null, [], []);
  etat = new FormControl(null, [], []);
  conscience = new FormControl(null, [], []);

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private message: MessageService,
    private toast: ToastrService,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    private modalService: NgbModal,
    private routerService: Router
  ) {
    this.conscience.valueChanges
      .pipe(distinctUntilChanged())
      .subscribe((value) => {
        if (this.canOpenGlasgow === true && value !== "Normale")
          this.openGlasgow();
      });
  }

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
          if (state.freeBeds) this.freeBeds = state.freeBeds;

          this.loadPatient(this.patient);

          if (
            state.hospitalisation !== undefined &&
            state.hospitalisation !== null
          ) {
            this.loadHospitalisation(state.hospitalisation);
          } else {
            this.loadHospitalisation(null);
          }

          this.chambreChanges = this.admissionFormGroup
            .get("chambre")
            ?.valueChanges.subscribe((n) => {
                this.lit.setValue(null);
                if (this.suivis === null || this.suivis.length === 0) {
                 
                  const ch = this.chambres.find((c) => c.id === n);
                  if (ch) {
                    this.lits = this.freeBeds.length === 0 ? ch["lits"] : ch["lits"].filter(
                      (l) =>
                        this.freeBeds.find((f) => f.id === l.id) !== undefined
                    );
                  }
                } else {
                  this.lits = this.chambres!.find((c) => c.id === n)!["lits"];
                }
            });
        }
      }
    );
  }

  openGlasgow() {
    const modalRef = this.modalService.open(GlasgowComponent, {
      size: "md",
      centered: true,
      keyboard: false,
      backdrop: "static",
    });

    if (this.hospitalisation) {
      modalRef.componentInstance.hospitalisation = this.hospitalisation;
    }

    modalRef.componentInstance.modal = modalRef;
    modalRef.componentInstance.closeModal.subscribe((data: any) => {
      // return data
      this.glasgow = data;
    });
  }

  loadPatient(patient: any) {
    if (this.patient !== null) {
      const gender = this.genders.find((g) => g["short"] == patient["sexe"]);

      this.firstname.setValue(patient["nom"]);
      this.lastname.setValue(patient["prenoms"]);
      this.gender.setValue(gender ? gender.short : "M");
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
    this.canOpenGlasgow = false;

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
            return c["theCode"] + " - " + c["title"];
          })
          .join(",")
      );
      this.hdm.setValue(this.consultation["hdm"]);
      this.getConsultationConstants();
      this.pasteTofield();
    }

    if (hospitalisation != null) {
      this.hospitalisation = hospitalisation;
      this.hospitalisation_id = hospitalisation.id;
      this.motif.setValue(hospitalisation["motif"]["libelle"]);
      this.diagnostic.setValue(hospitalisation["diagnostic"]["theCode"] + " - " + hospitalisation["diagnostic"]["title"]);
      this.arrive.setValue(hospitalisation["arrive"]);
      this.sector.setValue(hospitalisation["secteur_code"]);
      this.hospit_date.setValue(
        this.formatDate(hospitalisation["date_hospit"], "yyyy-MM-dd")
      );

      // auto load chambre and bed from suivis
      if (this.suivis !== null && this.suivis.length > 0) {
        const litSuivi = this.suivis.filter(
          (i: any) => i["type"] === "lits"
        )[0];
        const chambreSuivi = this.suivis.filter(
          (i: any) => i["type"] === "chambres"
        )[0];
        if (chambreSuivi !== null && litSuivi !== null) {
          this.chambre.setValue(chambreSuivi["type_id"]);
          this.lit.setValue(litSuivi["type_id"]);
        }
      }
    }

    this.canOpenGlasgow = true;
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

  getConsultationConstants() {
    const v = this.consultation.constante;
    this.temperature.setValue(v.temperature);
    this.poids.setValue(v.poids);
    this.taille.setValue(v.taille);
    this.ta.setValue(v.tension);
    this.pc.setValue(v.perimetre_cranien);
    this.fc.setValue(v.poul);
    this.fr.setValue(v.frequence_respiratoire);

    if (this.hospitalisation) {
      const e = JSON.parse(this.hospitalisation.extras).constantes;
      if (e !== undefined) {
        this.temperature.setValue(e.temperature);
        this.poids.setValue(e.poids);
        this.taille.setValue(e.taille);
        this.ta.setValue(e.ta);
        this.pc.setValue(e.pc);
        this.fc.setValue(e.fc);
        this.fr.setValue(e.fr);
        this.etat.setValue(e.etat);
        this.conscience.setValue(e.conscience);

        this.glasgow = e.glasgow;
      }
    }
  }

  openConstants() {
    this.modalReference = this.modalService.open(this.constants, {
      size: "md",
      centered: true,
      keyboard: false,
      backdrop: "static",
    });
  }

  pasteTofield() {
    let s = "";
    if (this.temperature.value.toString().trim().length > 0)
      s += `Température (°C): ${this.temperature.value}; `;
    if (this.poids.value.toString().trim().length > 0)
      s += `Poids (kg): ${this.poids.value}; `;
    if (this.taille.value.toString().trim().length > 0)
      s += `Taille (cm): ${this.taille.value}; `;
    if (this.pc.value.toString().trim().length > 0)
      s += `PC: ${this.pc.value}; `;
    if (this.fc.value.toString().trim().length > 0)
      s += `Fréquence cardiaque (/mn): ${this.fc.value}; `;
    if (this.fr.value.toString().trim().length > 0)
      s += `Fréquence respiratoire (/mn): ${this.fr.value}; `;
    if (this.ta.value.toString().trim().length > 0)
      s += `Tension artérielle (mm/Hg): ${this.ta.value}; `;
    if (this.etat.value && this.etat.value.toString().trim().length > 0)
      s += `${this.etat.value}; `;
    if (
      this.conscience.value &&
      this.conscience.value.toString().trim().length > 0
    )
      s += `${this.conscience.value}; `;

    if (this.glasgow.value && this.glasgow.value !== 0)
      s += `Score de glasgow : ${this.glasgow.value}`;

    this.arrive.setValue(s);

    if (this.modalReference) this.modalReference.close();
  }

  async cancel() {
    const confirm = await this.message.confirmDialog(
      WarningMessages.SURE_TO_CONTINUE
    );

    if (confirm) {
      this.routerService.navigateByUrl(
        "/medical-base/patient-visits-summary/" + this.patient.reference
      );
    }
  }

  async confirmAction() {
    if (!this.admissionFormGroup.valid) {
      this.markAllControlsAsTouched();
    } else {
      const confirm = await this.message.confirmDialog(
        WarningMessages.SURE_TO_CONTINUE
      );
      if (confirm) {
        const constantes = {
          temperature: this.temperature.value,
          poids: this.poids.value,
          taille: this.taille.value,
          pc: this.pc.value,
          fc: this.fc.value,
          fr: this.fr.value,
          ta: this.ta.value,
          etat: this.etat.value,
          conscience: this.conscience.value,
          glasgow: this.glasgow,
        };

        const data = {
          id: this.hospitalisation_id,
          motif: this.admissionFormGroup.value.motif,
          diagnostic: this.admissionFormGroup.value.diagnostic.split(" - ")[0],
          hdm: this.admissionFormGroup.value.hdm,
          patient_ref: this.patient.reference,
          secteur_code: this.admissionFormGroup.value.sector,
          consultation_ref: this.consultation.reference,
          date_hospit: new Date(this.admissionFormGroup.value.hospit_date),
          arrive: this.arrive.value,
          extras: JSON.stringify({
            patient: this.patient,
            constantes,
            chambre: this.chambre.value,
            lit: this.lit.value,
          }),
        };

        // console.log(data)

        if (this.hospitalisation_id == null) {
          this.hospitalisationStore
            .saveHospitalisation(data, this.hospitalisation_id)
            .subscribe(
              async (response) => {
                const responseData = response;
                this.toast.success("Hospitalisation", "Hospitalisation effectuée");
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
                  apply_date: new Date(
                    this.admissionFormGroup.value.hospit_date
                  ),
                  hospit_id: this.hospitalisation_id,
                });

                // add lit
                this.hospitalisationStore.commitSuivi({
                  type: "lits",
                  type_id: this.lit.value,
                  qte: 1,
                  apply_date: new Date(
                    this.admissionFormGroup.value.hospit_date
                  ),
                  hospit_id: this.hospitalisation_id,
                });
                // this.hospitalisationStore.fetchHospitalisation(responseData);
                this.hospitalisationStore.clearHospitalisation();
                const route = "/hospitalisation/edit?id=" + responseData;
                await this.routerService.navigateByUrl(route);
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
