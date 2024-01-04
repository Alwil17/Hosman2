import { Component, OnInit } from "@angular/core";
import { Subject, takeUntil, Subscription } from "rxjs";
import { Lit } from "src/app/models/hospitalisation/lit";
import { Chambre } from "src/app/models/hospitalisation/chambre";
import { ChambreStore } from "@stores/chambres";
import { HospitalisationStore } from "@stores/hospitalisation";
import { MessageService } from "@services/messages/message.service";
import { ToastrService } from "ngx-toastr";
import { ActivatedRoute } from "@angular/router";
import { FormControl, FormGroup } from "@angular/forms";
import { DatePipe } from "@angular/common";
import { SectorStore } from "@stores/sectors";
import { Sector } from "src/app/models/secretariat/shared/sector.model";
import { validateYupSchema } from "src/app/helpers/utils";
import * as Yup from "yup";
import { ErrorMessages, WarningMessages } from "src/app/helpers/messages";
import { Status } from 'src/app/models/secretariat/patients/status.model';

@Component({
  selector: "app-hosp-admission",
  templateUrl: "./admission.component.html",
  styleUrls: ["./admission.component.scss"],
})
export class AdmissionComponent implements OnInit {
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
  hasPatient: boolean = false;

  consultation_id: number = -1;
  hospitation_id: number | null = null;

  private destroy$ = new Subject<void>();

  today = new Date().toLocaleDateString("fr-FR");

  private chambreChanges: Subscription | undefined;

  constructor(
    private chambreStore: ChambreStore,
    private sectorStore: SectorStore,
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

    this.route.queryParams.subscribe((params) => {
      this.consultation_id = params["consultation"];
    });

    this.sectorStore
      .getSectorsObservable()
      .pipe(takeUntil(this.destroy$))
      .subscribe((sectors: Sector[]) => {
        this.sectors = sectors;
      });

    this.chambreStore
      .getChambresObservable()
      .pipe(takeUntil(this.destroy$))
      .subscribe((chambres: Chambre[]) => {
        this.chambres = chambres;
      });


    this.hospitalisationStore
      .getConsultation()
      .pipe(takeUntil(this.destroy$))
      .subscribe((consultation: any) => {
        this.consultation = consultation;
        this.patient = consultation["patient"];
        this.hasPatient = this.patient != null && this.hasPatient != undefined;

        this.loadPatient(this.patient);
        this.loadHospitalisation(null); // <== update if editing hospitalisation
      });

    this.getData();

    this.chambreChanges = this.admissionFormGroup
      .get("chambre")
      ?.valueChanges.subscribe((n) => {
         this.lit.setValue(null)
         this.lits = this.chambres.find((c) => c.id === n)!['lits'];
      });
  }

  getData() {
    this.chambreStore.getAll();
    this.sectorStore.getAll();
    if (this.consultation_id !== undefined) {
      this.hospitalisationStore.fetchConsultation(this.consultation_id);
    }
  }

  loadPatient(patient: any) {
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

  loadHospitalisation(hospitalisation: any) {
    if (hospitalisation == null) {
      this.sector.setValue(this.consultation["secteur"]["code"]);
      this.motif.setValue(this.consultation.motifs.map(function(c : any) {return c['libelle']}).join(','))
      this.diagnostic.setValue(this.consultation.diagnostics.map(function(c : any) {return c['theCode']}).join(','))
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
          "motif": this.admissionFormGroup.value.motif,
          "diagnostic": this.admissionFormGroup.value.diagnostic,
          "hdm": this.admissionFormGroup.value.hdm,
          "patient_ref": this.patient.reference,
          "secteur_code": this.admissionFormGroup.value.sector,
          "consultation_ref": this.consultation.reference,
          "date_hospit": new Date(this.admissionFormGroup.value.hospit_date)
        }


        // console.log(data)
        this.hospitalisationStore.post(data).subscribe(
          (response) => {
              const responseData = response;
              this.toast.success("Hospitalisation", "Admission effectuée");
              this.hospitation_id = responseData;
            },
          (error) => {
            console.error('POST request failed:', error);
          }
        );        
      }
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
    this.chambreChanges!.unsubscribe();
  }
}
