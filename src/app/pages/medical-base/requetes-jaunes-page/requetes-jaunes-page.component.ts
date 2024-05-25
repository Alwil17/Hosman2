import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DiagnosticService } from '@services/medical-base/diagnostic.service';
import { MotifService } from '@services/medical-base/motif.service';
import { PatientVisitService } from '@services/medical-base/patient-visit.service';
import { DoctorService } from '@services/secretariat/shared/doctor.service';
import { LoadingSpinnerService } from '@services/secretariat/shared/loading-spinner.service';
import { SectorService } from '@services/secretariat/shared/sector.service';
import { TariffService } from '@services/secretariat/shared/tariff.service';
import { ToastService } from '@services/secretariat/shared/toast.service';
import { Subject, catchError, concat, distinctUntilChanged, forkJoin, of, switchMap, tap } from 'rxjs';
import { SelectOption } from 'src/app/models/extras/select.model';
import { ToastType } from 'src/app/models/extras/toast-type.model';
import { PdfModalComponent } from 'src/app/shared/modals/pdf-modal/pdf-modal.component';

@Component({
  selector: 'app-requetes-jaunes-page',
  templateUrl: './requetes-jaunes-page.component.html',
  styleUrls: ['./requetes-jaunes-page.component.scss']
})
export class RequetesJaunesPageComponent implements OnInit {
  breadCrumbItems!: Array<{}>;

  // To set date min
  today = new Date().toLocaleDateString("fr-ca");

  // requete par motifs
  search1Form!: FormGroup;
  search1StartDateControl = new FormControl(this.today, Validators.required);
  search1EndDateControl = new FormControl(this.today, Validators.required);
  search1MotifControl = new FormControl(null, Validators.required);
  search1Motifs!: SelectOption[];
  search1MotifsAreLoading = false;
  search1MotifsTypeahead$ = new Subject<any>();

  // requete par diagnostic
  search2Form!: FormGroup;
  search2StartDateControl = new FormControl(this.today, Validators.required);
  search2EndDateControl = new FormControl(this.today, Validators.required);
  search2DiagnosticControl = new FormControl(null, Validators.required);
  search2Diagnostics!: SelectOption[];
  search2DiagnosticsAreLoading = false;
  search2DiagnosticsTypeahead$ = new Subject<any>();

  // requete par secteurs
  search3Form!: FormGroup;
  search3StartDateControl = new FormControl(this.today, Validators.required);
  search3EndDateControl = new FormControl(this.today, Validators.required);
  search3SecteurControl = new FormControl(null, Validators.required);
  search3Secteurs!: SelectOption[];

  // requete par medecins
  search4Form!: FormGroup;
  search4StartDateControl = new FormControl(this.today, Validators.required);
  search4EndDateControl = new FormControl(this.today, Validators.required);
  search4MedecinControl = new FormControl(null, Validators.required);
  search4Medecins!: SelectOption[];

  // requete multicrirères
  search5Form!: FormGroup;
  search5StartDateControl = new FormControl(this.today, Validators.required);
  search5EndDateControl = new FormControl(this.today, Validators.required);
  search5ActeControl = new FormControl(null, Validators.required);
  search5Actes!: SelectOption[];
  search5MotifControl = new FormControl();
  search5Motifs!: SelectOption[];
  search5DiagnosticControl = new FormControl();
  search5Diagnostics!: SelectOption[];

  // requete multicrirères
  search6Form!: FormGroup;
  search6StartDateControl = new FormControl(this.today, Validators.required);
  search6EndDateControl = new FormControl(this.today, Validators.required);

  // requete multicrirères
  search7Form!: FormGroup;
  search7StartDateControl = new FormControl(this.today, Validators.required);
  search7EndDateControl = new FormControl(this.today, Validators.required);

  constructor(
    private sectorService: SectorService,
    private doctorService: DoctorService,
    private tariffService: TariffService,
    private motifService: MotifService,
    private patientVisitService: PatientVisitService,
    private diagnosticService: DiagnosticService,
    private loadingSpinnerService: LoadingSpinnerService,
    private toastService: ToastService,
    private modalService: NgbModal,
  ) { }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: "Base médicale" },
      { label: "Requêtes Jaunes", active: true },
    ];

    this.initForms();
    this.fetchSelectData();
  }

  initForms() {
    this.search1Form = new FormGroup({
      search1StartDateControl: this.search1StartDateControl,
      search1EndDateControl: this.search1EndDateControl,
      search1MotifControl: this.search1MotifControl,
    });
    this.search2Form = new FormGroup({
      search2StartDateControl: this.search2StartDateControl,
      search2EndDateControl: this.search2EndDateControl,
      search2DiagnosticControl: this.search2DiagnosticControl,
    });
    this.search3Form = new FormGroup({
      search3StartDateControl: this.search3StartDateControl,
      search3EndDateControl: this.search3EndDateControl,
      search3SecteurControl: this.search3SecteurControl,
    });
    this.search4Form = new FormGroup({
      search4StartDateControl: this.search4StartDateControl,
      search4EndDateControl: this.search4EndDateControl,
      search4MedecinControl: this.search4MedecinControl,
    });
    this.search5Form = new FormGroup({
      search5StartDateControl: this.search5StartDateControl,
      search5EndDateControl: this.search5EndDateControl,
      search5ActeControl: this.search5ActeControl,
      search5MotifControl: this.search5MotifControl,
      search5DiagnosticControl: this.search5DiagnosticControl,
    });
    this.search6Form = new FormGroup({
      search6StartDateControl: this.search6StartDateControl,
      search6EndDateControl: this.search6EndDateControl,
    });
    this.search7Form = new FormGroup({
      search7StartDateControl: this.search7StartDateControl,
      search7EndDateControl: this.search7EndDateControl,
    });
  }

  fetchSelectData() {
    forkJoin({
      sectors: this.sectorService.getAll(),
      doctors: this.doctorService.getAll(),
      acts: this.tariffService.getByGroupCode("GRP001"),
    }).subscribe({
      next: (data) => {
        this.search3Secteurs = data.sectors.map((value) => ({
          id: value.code,
          text: value.libelle,
        }));

        this.search4Medecins = data.doctors.map((value) => ({
          id: value.matricule,
          text: value.fullName,
        }));

        this.search5Actes = data.acts.map((value) => ({
          id: value.code,
          text: value.libelle,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    })

    // MOTIFS
    const motifs$ = concat(
      of([]), // default items
      this.search1MotifsTypeahead$.pipe(
        distinctUntilChanged(),
        tap(() => {
          this.search1MotifsAreLoading = true;
          this.loadingSpinnerService.hideLoadingSpinner();
        }),
        switchMap((term) =>
          this.motifService.search(term).pipe(
            catchError((err) => {
              console.log(err);

              return of([]);
            }), // empty list on error
            tap(() => {
              this.search1MotifsAreLoading = false;
              // this.loadingSpinnerService.showLoadingSpinner();
            })
          )
        )
      )
    );

    motifs$.subscribe({
      next: (data) => {
        this.search1Motifs = data.map((value) => ({
          id: value.id,
          text: value.libelle,
        }));
        this.search5Motifs = data.map((value) => ({
          id: value.id,
          text: value.libelle,
        }));
      },
      error: (e) => {
        console.log(e);
      },
    });

    // DIAGNOSTICS
    const diagnostics$ = concat(
      of([]), // default items
      this.search2DiagnosticsTypeahead$.pipe(
        distinctUntilChanged(),
        tap(() => {
          this.search2DiagnosticsAreLoading = true;
          this.loadingSpinnerService.hideLoadingSpinner();
        }),
        switchMap((term) =>
          this.diagnosticService.search(term).pipe(
            catchError((err) => {
              console.log(err);

              return of([]);
            }), // empty list on error
            tap(() => {
              this.search2DiagnosticsAreLoading = false;
              // this.loadingSpinnerService.showLoadingSpinner();
            })
          )
        )
      )
    );

    diagnostics$.subscribe({
      next: (data) => {
        this.search2Diagnostics = data.map((value) => ({
          id: value.theCode,
          text: value.theCode + " - " + value.title,
        }));
        this.search5Diagnostics = data.map((value) => ({
          id: value.theCode,
          text: value.theCode + " - " + value.title,
        }));
      },
      error: (e) => {
        console.log(e);
      },
    });
  }

  async submitForm1() {
    if (this.search1Form.invalid) {
      this.toastService.show({
        messages: ["Veuillez renseigner tous les champs obligatoires."],
        type: ToastType.Warning,
      });

      return;
    }

    const minDate = new Date(this.search1StartDateControl.value);
    const maxDate = this.search1EndDateControl.value
      ? new Date(this.search1EndDateControl.value)
      : new Date(this.search1StartDateControl.value);

    let motifCode;
    if (this.search1MotifControl.value) {
      motifCode = this.search1Motifs.find(
        (value) => this.search1MotifControl.value?.id == value.id
      )?.id
    } else {
      motifCode = ""
    }

    this.patientVisitService.printRequetesJaunesReport({
      minDate: minDate,
      maxDate: maxDate,
      motif: motifCode,
      vue: "multi"
    }).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Génération de l'état."],
          type: ToastType.Success,
        });

        const pdfModalRef = this.modalService.open(PdfModalComponent, {
          size: "xl",
          centered: true,
          scrollable: true,
          backdrop: "static",
        });

        pdfModalRef.componentInstance.title = "Reçu";
        pdfModalRef.componentInstance.pdfSrc = data;
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Echec de la génération de l'état."],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  async submitForm2() {
    if (this.search2Form.invalid) {
      this.toastService.show({
        messages: ["Veuillez renseigner tous les champs obligatoires."],
        type: ToastType.Warning,
      });

      return;
    }

    const minDate = new Date(this.search2StartDateControl.value);
    const maxDate = this.search2EndDateControl.value
      ? new Date(this.search2EndDateControl.value)
      : new Date(this.search2StartDateControl.value);

    let diagnosticCode;
    if (this.search2DiagnosticControl.value) {
      diagnosticCode = this.search2Diagnostics.find(
        (value) => this.search2DiagnosticControl.value?.id == value.id
      )?.id
    } else {
      diagnosticCode = ""
    }

    this.patientVisitService.printRequetesJaunesReport({
      minDate: minDate,
      maxDate: maxDate,
      diagnostic: diagnosticCode,
      vue: "multi"
    }).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Génération de l'état."],
          type: ToastType.Success,
        });

        const pdfModalRef = this.modalService.open(PdfModalComponent, {
          size: "xl",
          centered: true,
          scrollable: true,
          backdrop: "static",
        });

        pdfModalRef.componentInstance.title = "Reçu";
        pdfModalRef.componentInstance.pdfSrc = data;
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Echec de la génération de l'état."],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  async submitForm3() {
    if (this.search3Form.invalid) {
      this.toastService.show({
        messages: ["Veuillez renseigner tous les champs obligatoires."],
        type: ToastType.Warning,
      });

      return;
    }

    const minDate = new Date(this.search3StartDateControl.value);
    const maxDate = this.search3EndDateControl.value
      ? new Date(this.search3EndDateControl.value)
      : new Date(this.search3StartDateControl.value);

    let secteurCode;
    if (this.search3SecteurControl.value) {
      secteurCode = this.search3Secteurs.find(
        (value) => this.search3SecteurControl.value?.id == value.id
      )?.id
    } else {
      secteurCode = ""
    }

    this.patientVisitService.printRequetesJaunesReport({
      minDate: minDate,
      maxDate: maxDate,
      sector: secteurCode,
      vue: "secteur"
    }).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Génération de l'état."],
          type: ToastType.Success,
        });

        const pdfModalRef = this.modalService.open(PdfModalComponent, {
          size: "xl",
          centered: true,
          scrollable: true,
          backdrop: "static",
        });

        pdfModalRef.componentInstance.title = "Reçu";
        pdfModalRef.componentInstance.pdfSrc = data;
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Echec de la génération de l'état."],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }
  async submitForm4() {
    if (this.search4Form.invalid) {
      this.toastService.show({
        messages: ["Veuillez renseigner tous les champs obligatoires."],
        type: ToastType.Warning,
      });

      return;
    }

    const minDate = new Date(this.search4StartDateControl.value);
    const maxDate = this.search4EndDateControl.value
      ? new Date(this.search4EndDateControl.value)
      : new Date(this.search4StartDateControl.value);

    let docteurCode;
    if (this.search4MedecinControl.value) {
      docteurCode = this.search4Medecins.find(
        (value) => this.search4MedecinControl.value?.id == value.id
      )?.id
    } else {
      docteurCode = ""
    }

    this.patientVisitService.printRequetesJaunesReport({
      minDate: minDate,
      maxDate: maxDate,
      doctor: docteurCode,
      vue: "docteur"
    }).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Génération de l'état."],
          type: ToastType.Success,
        });

        const pdfModalRef = this.modalService.open(PdfModalComponent, {
          size: "xl",
          centered: true,
          scrollable: true,
          backdrop: "static",
        });

        pdfModalRef.componentInstance.title = "Reçu";
        pdfModalRef.componentInstance.pdfSrc = data;
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Echec de la génération de l'état."],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  async submitForm5() {
    if (this.search5Form.invalid) {
      this.toastService.show({
        messages: ["Veuillez renseigner tous les champs obligatoires."],
        type: ToastType.Warning,
      });

      return;
    }

    
    const minDate = new Date(this.search5StartDateControl.value);
    const maxDate = this.search5EndDateControl.value
      ? new Date(this.search5EndDateControl.value)
      : new Date(this.search5StartDateControl.value);

    let acteCode;
    if (this.search5ActeControl.value) {
      acteCode = this.search5Actes.find(
        (value) => this.search5ActeControl.value?.id == value.id
      )?.id
    } else {
      acteCode = ""
    }

    let motifCode;
    if (this.search5MotifControl.value) {
      motifCode = this.search5Motifs.find(
        (value) => this.search5MotifControl.value?.id == value.id
      )?.id
    } else {
      motifCode = ""
    }

    let diagnosticCode;
    if (this.search5DiagnosticControl.value) {
      diagnosticCode = this.search5Diagnostics.find(
        (value) => this.search5DiagnosticControl.value?.id == value.id
      )?.id
    } else {
      diagnosticCode = ""
    }

    this.patientVisitService.printRequetesJaunesReport({
      minDate: minDate,
      maxDate: maxDate,
      acte: acteCode,
      motif: motifCode,
      diagnostic: diagnosticCode,
      vue: "multi"
    }).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Génération de l'état."],
          type: ToastType.Success,
        });

        const pdfModalRef = this.modalService.open(PdfModalComponent, {
          size: "xl",
          centered: true,
          scrollable: true,
          backdrop: "static",
        });

        pdfModalRef.componentInstance.title = "Reçu";
        pdfModalRef.componentInstance.pdfSrc = data;
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Echec de la génération de l'état."],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  async submitForm6() {
    if (this.search6Form.invalid) {
      this.toastService.show({
        messages: ["Veuillez renseigner tous les champs obligatoires."],
        type: ToastType.Warning,
      });

      return;
    }
  }
  
  async submitForm7() {
    if (this.search7Form.invalid) {
      this.toastService.show({
        messages: ["Veuillez renseigner tous les champs obligatoires."],
        type: ToastType.Warning,
      });

      return;
    }
  }
}
