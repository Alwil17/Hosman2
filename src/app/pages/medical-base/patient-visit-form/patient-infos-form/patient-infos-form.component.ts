import { Component, Input, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { BehaviorSubject, Subject, forkJoin, merge, tap } from "rxjs";
import { HAS_INSURANCES } from "src/app/data/secretariat/has-insurance.data";
import { isAdult } from "src/app/helpers/age-calculator";
import { SelectOption } from "src/app/models/extras/select.model";
import { Parent } from "src/app/models/secretariat/patients/parent.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { EmployerService } from "src/app/services/secretariat/patients/employer.service";
import { PatientService } from "src/app/services/secretariat/patients/patient.service";
import { ProfessionService } from "src/app/services/secretariat/patients/profession.service";
import { AdultPatientBackgroundsModalComponent } from "../adult-patient-backgrounds-modal/adult-patient-backgrounds-modal.component";
import { ChildPatientBackgroundsModalComponent } from "../child-patient-backgrounds-modal/child-patient-backgrounds-modal.component";
import { SiblingsNumberModalComponent } from "../siblings-number-modal/siblings-number-modal.component";
import { CoefficientSocialModalComponent } from "../coefficient-social-modal/coefficient-social-modal.component";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { ChronicDiseaseRequest } from "src/app/models/secretariat/patients/requests/chronic-disease-request.model";
import { ParentRequest } from "src/app/models/secretariat/patients/requests/parent-request.model";
import { PatientVisitInfoRequest } from "src/app/models/secretariat/patients/requests/patient-visit-info-request.model";

@Component({
  selector: "app-patient-infos-form",
  templateUrl: "./patient-infos-form.component.html",
  styleUrls: ["./patient-infos-form.component.scss"],
})
export class PatientInfosFormComponent implements OnInit {
  @Input()
  patientInfos!: Patient;

  patientInfosForm!: FormGroup;

  // patientInfosForm$ = new BehaviorSubject<FormGroup | null>(null);

  isPatientAdult = false;

  // To set date max
  today = new Date();

  // Patient infos form controls
  // statusControl = new FormControl(null);
  // backgroundControl = new FormControl(null);
  commentsControl = new FormControl(null);
  backgroundsControl = new FormControl(null);

  motherProfessionControl = new FormControl(null);
  motherTelControl = new FormControl(null);
  motherEmployerControl = new FormControl(null);
  motherInsuranceControl = new FormControl(null);
  motherBirthYearControl = new FormControl(null, [
    Validators.min(1800),
    Validators.max(this.today.getFullYear()),
  ]);
  motherAgeControl = new FormControl(null);

  fatherProfessionControl = new FormControl(null);
  fatherTelControl = new FormControl(null);
  fatherEmployerControl = new FormControl(null);
  fatherInsuranceControl = new FormControl(null);
  fatherBirthYearControl = new FormControl(null, [
    Validators.min(1800),
    Validators.max(this.today.getFullYear()),
  ]);
  fatherAgeControl = new FormControl(null);

  professions!: SelectOption[];
  employers!: SelectOption[];
  hasInsurances = HAS_INSURANCES.map((hasInsurance) => ({
    id: hasInsurance.code,
    text: hasInsurance.text,
  }));

  chronicDiseases: SelectOption[] = [];

  constructor(
    public patientService: PatientService,
    private patientVisitService: PatientVisitService,
    private professionService: ProfessionService,
    private employerService: EmployerService,
    private modalService: NgbModal,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.patientInfosForm = new FormGroup({
      // chronicDiseaseControl: this.chronicDiseaseControl,
      chronicDiseases: new FormArray([new FormControl(null)]),
      // statusControl: this.statusControl,
      // backgroundControl: this.backgroundControl,
      commentsControl: this.commentsControl,
      backgroundsControl: this.backgroundsControl,

      motherProfessionControl: this.motherProfessionControl,
      motherTelControl: this.motherTelControl,
      motherEmployerControl: this.motherEmployerControl,
      motherInsuranceControl: this.motherInsuranceControl,
      motherBirthYearControl: this.motherBirthYearControl,
      motherAgeControl: this.motherAgeControl,

      fatherProfessionControl: this.fatherProfessionControl,
      fatherTelControl: this.fatherTelControl,
      fatherEmployerControl: this.fatherEmployerControl,
      fatherInsuranceControl: this.fatherInsuranceControl,
      fatherBirthYearControl: this.fatherBirthYearControl,
      fatherAgeControl: this.fatherAgeControl,
    });

    // this.patientInfosForm$.next(this.patientInfosForm);

    this.isPatientAdult = isAdult(this.patientInfos.date_naissance);

    this.fetchPatientInfosSelectData();

    this.onPatientInfosFormInputsChanges();

    // this.patientInfosForm.valueChanges.subscribe((data) => {
    //   console.log("Changed");

    //   this.patientInfosForm$.next(this.patientInfosForm);
    // });
  }

  // GET PATIENT INFOS SELECT DATA -----------------------------------------------------------------------------------
  fetchPatientInfosSelectData() {
    forkJoin({
      professions: this.professionService.getAll(),
      employers: this.employerService.getAll(),
      // insurances: this.insuranceService.getAll(),
    }).subscribe({
      next: (data) => {
        this.professions = data.professions.map((value) => ({
          id: value.id,
          text: value.denomination,
        }));

        this.employers = data.employers.map((value) => ({
          id: value.id,
          text: value.nom,
        }));

        // this.insurances = data.insurances.map((value) => ({
        //   id: value.id,
        //   text: value.nom,
        // }));

        this.setPatientInfoFieldsInitialValues();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  // SET FIELDS INITIAL VALUES --------------------------------------------------------------------------------------------------------------
  setPatientInfoFieldsInitialValues() {
    const cdLength = this.patientInfos.maladies?.length ?? 0;
    console.log(this.patientInfos);

    let mother: Parent | undefined;
    let father: Parent | undefined;

    if (this.patientInfos.parents) {
      const parents = this.patientInfos.parents;

      parents.forEach((parent) => {
        if (parent.type === "mere") {
          mother = parent;
        } else if (parent.type === "pere") {
          father = parent;
        }
      });
    }

    for (let i = 0; i < cdLength - 1; i++) {
      this.addChronicDiseaseField();
    }

    this.patientInfos.maladies?.map((value) => {
      this.chronicDiseases.push({
        id: value.id,
        text: value.nom,
      });
    });

    console.log(this.chronicDiseases);

    // const cd = this.patientInfos.maladies!

    if (
      this.patientInfos.maladies != null &&
      this.patientInfos.maladies?.length !== 0
    ) {
      this.chronicDiseasesFields.controls.forEach((control, index) => {
        control.setValue({
          id: this.patientInfos.maladies![index].id,
          text: this.patientInfos.maladies![index].nom,
        });
      });
    }

    this.commentsControl.setValue(this.patientInfos.commentaire);
    // this.backgroundsControl.setValue(this.patientInfos.antecedant);

    if (mother) {
      this.motherProfessionControl.setValue(
        this.professions.find((value) => {
          return (
            value.text.toLowerCase() ===
            mother?.profession.denomination.toLowerCase()
          );
        })
      );
      this.motherTelControl.setValue(mother?.telephone);
      this.motherEmployerControl.setValue(
        this.employers.find((value) => {
          return (
            value.text.toLowerCase() === mother?.employeur.nom.toLowerCase()
          );
        })
      );
      this.motherInsuranceControl.setValue(
        // this.insurances.find((value) => {
        //   return (
        //     value.text.toLowerCase() === mother?.assurance.nom.toLowerCase()
        //   );
        // })

        this.hasInsurances.find((value) => mother?.assurance == value.id)
      );
      this.motherBirthYearControl.setValue(mother?.annee_naissance);
    }

    if (father) {
      this.fatherProfessionControl.setValue(
        this.professions.find((value) => {
          return (
            value.text.toLowerCase() ===
            father?.profession.denomination.toLowerCase()
          );
        })
      );
      this.fatherTelControl.setValue(father?.telephone);
      this.fatherEmployerControl.setValue(
        this.employers.find((value) => {
          return (
            value.text.toLowerCase() === father?.employeur.nom.toLowerCase()
          );
        })
      );
      this.fatherInsuranceControl.setValue(
        // this.insurances.find((value) => {
        //   return (
        //     value.text.toLowerCase() === father?.assurance.nom.toLowerCase()
        //   );
        // })

        this.hasInsurances.find((value) => father?.assurance == value.id)
      );
      this.fatherBirthYearControl.setValue(father?.annee_naissance);
    }
  }

  // OPEN PATIENT INFOS MODALS -------------------------------------------------------------------------------------------------------
  openBackgroundsModal() {
    let backgroundsModalRef;

    if (this.isPatientAdult) {
      backgroundsModalRef = this.modalService.open(
        AdultPatientBackgroundsModalComponent,
        {
          size: "lg",
          centered: true,
          scrollable: true,
          backdrop: "static",
        }
      );
    } else {
      backgroundsModalRef = this.modalService.open(
        ChildPatientBackgroundsModalComponent,
        {
          size: "lg",
          centered: true,
          scrollable: true,
          backdrop: "static",
        }
      );
    }

    backgroundsModalRef.componentInstance.patientInfos = this.patientInfos;

    merge(backgroundsModalRef.closed, backgroundsModalRef.dismissed).subscribe({
      next: () => {
        this.patientService.get(this.patientInfos.id).subscribe({
          next: (patient) => (this.patientInfos = patient),
          error: (e) => {
            console.error(e);

            this.toastService.show({
              messages: ["Désolé, une erreur s'est produite"],
              delay: 10000,
              type: ToastType.Error,
            });
          },
        });
      },
    });
  }

  openSiblingsModal() {
    const siblingsModalRef = this.modalService.open(
      SiblingsNumberModalComponent,
      {
        size: "md",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );

    siblingsModalRef.componentInstance.patientInfos = this.patientInfos;
  }

  openCoefficientSocialModal() {
    const coefficientSocialModalRef = this.modalService.open(
      CoefficientSocialModalComponent,
      {
        size: "md",
        centered: true,
        scrollable: true,
        backdrop: "static",
      }
    );

    coefficientSocialModalRef.componentInstance.patientInfos =
      this.patientInfos;

    merge(
      coefficientSocialModalRef.closed,
      coefficientSocialModalRef.dismissed
    ).subscribe({
      next: () => {
        this.patientService.get(this.patientInfos.id).subscribe({
          next: (patient) => (this.patientInfos = patient),
          error: (e) => {
            console.error(e);

            this.toastService.show({
              messages: ["Désolé, une erreur s'est produite"],
              delay: 10000,
              type: ToastType.Error,
            });
          },
        });
      },
    });
  }

  // ON PATIENT INFOS FORM INPUTS CHANGES -----------------------------------------------------------------------------------------------------------
  onPatientInfosFormInputsChanges() {
    this.motherBirthYearControl.valueChanges.subscribe((value) => {
      const age = new Date().getFullYear() - value;
      this.motherAgeControl.setValue(age > 0 ? age : 0);
    });

    this.fatherBirthYearControl.valueChanges.subscribe((value) => {
      const age = new Date().getFullYear() - value;
      this.fatherAgeControl.setValue(age > 0 ? age : 0);
    });
  }

  // SAVE PATIENT INFO ---------------------------------------------------------------------------------------------------------------
  savePatientInfosObservable() {
    let chronicDiseases: ChronicDiseaseRequest[] = [];
    this.chronicDiseasesFields.controls.map((control) =>
      chronicDiseases.push({ maladie: control.value?.text })
    );

    let parents: ParentRequest[] = [];

    let mother = new ParentRequest({
      profession: this.motherProfessionControl.value?.text,
      employeur: this.motherEmployerControl.value?.text,
      assurance: this.motherInsuranceControl.value?.text,
      telephone: this.motherTelControl.value,
      annee_naissance: this.motherBirthYearControl.value,
      sexe: "F",
      type: "mere",
    });

    const father = new ParentRequest({
      profession: this.fatherProfessionControl.value?.text,
      employeur: this.fatherEmployerControl.value?.text,
      assurance: this.fatherInsuranceControl.value?.text,
      telephone: this.fatherTelControl.value,
      annee_naissance: this.fatherBirthYearControl.value,
      sexe: "M",
      type: "pere",
    });

    parents.push(mother, father);

    const patientVisitInfo = new PatientVisitInfoRequest({
      commentaire: this.commentsControl.value,
      maladies: chronicDiseases,
      parents: parents,
    });
    // antecedant: this.backgroundsControl.value,

    console.log(JSON.stringify(patientVisitInfo, null, 2));

    return this.patientVisitService
      .updateVisitInfo(this.patientInfos.reference, patientVisitInfo)
      .pipe(
        tap({
          next: (data) => {
            // this.setFieldsInitialValues();

            this.patientInfosForm.markAsPristine();
            // this.patientInfosForm.markAsUntouched()
          },
          error: (error) => {
            console.log(error);

            this.patientInfosForm.markAsPristine();
            // this.patientInfosForm.markAsUntouched()
          },
        })
      );
  }

  savePatientInfos() {
    this.savePatientInfosObservable().subscribe();
  }

  // CHRONIC DISEASES FIELDS --------------------------------------------------------------------------------------------------------
  get chronicDiseasesFields() {
    return this.patientInfosForm.get("chronicDiseases") as FormArray;
  }

  addChronicDiseaseField() {
    this.chronicDiseasesFields.push(new FormControl(null));
    console.log(this.chronicDiseasesFields);
  }

  removeChronicDiseaseField(index: number) {
    this.chronicDiseasesFields.removeAt(index);
    console.log(this.chronicDiseasesFields);
  }

  isPatientInfosFormDirty() {
    return this.patientInfosForm.dirty;
  }
}
