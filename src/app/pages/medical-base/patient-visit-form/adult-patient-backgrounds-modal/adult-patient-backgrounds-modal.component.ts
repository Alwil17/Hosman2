import { Component, Input, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { SelectOption } from "src/app/models/extras/select.model";
import { MultiInputModalComponent } from "../multi-input-modal/multi-input-modal.component";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { BackgroundsRequest } from "src/app/models/secretariat/patients/requests/backgrounds-request.model";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { PatientVisitInfoRequest } from "src/app/models/secretariat/patients/requests/patient-visit-info-request.model";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { parseIntOrZero } from "src/app/helpers/parsers";

@Component({
  selector: "app-adult-patient-backgrounds-modal",
  templateUrl: "./adult-patient-backgrounds-modal.component.html",
  styleUrls: ["./adult-patient-backgrounds-modal.component.scss"],
})
export class AdultPatientBackgroundsModalComponent implements OnInit {
  @Input()
  patientInfos!: Patient;

  yesOrNoOptions: SelectOption[] = [
    {
      id: false,
      text: "Non",
    },
    {
      id: true,
      text: "Oui",
    },
  ];

  diabetesOptions: SelectOption[] = this.yesOrNoOptions;
  diabetesDetailOptions: SelectOption[] = [
    {
      id: 1,
      text: "type 1",
    },
    {
      id: 2,
      text: "type 2",
    },
  ];

  ugdOptions: SelectOption[] = this.yesOrNoOptions;
  htaOptions: SelectOption[] = this.yesOrNoOptions;
  asthmaOptions: SelectOption[] = this.yesOrNoOptions;

  drepanocytoseOptions: SelectOption[] = this.yesOrNoOptions;
  drepanocytoseDetailOptions: SelectOption[] = [
    {
      id: 1,
      text: "homozygote AA",
    },
    {
      id: 2,
      text: "homozygote AS",
    },
    {
      id: 3,
      text: "homozygote AC",
    },
    {
      id: 4,
      text: "homozygote SC",
    },
    {
      id: 5,
      text: "homozygote SS",
    },
  ];

  alcoholismOptions: SelectOption[] = this.yesOrNoOptions;

  smokingOptions: SelectOption[] = this.yesOrNoOptions;
  smokingDetailOptions1: SelectOption[] = [
    {
      id: 1,
      text: "unité(s)",
    },
    {
      id: 2,
      text: "paquet(s)",
    },
  ];
  smokingDetailOptions2: SelectOption[] = [
    {
      id: 1,
      text: "par heure",
    },
    {
      id: 2,
      text: "par jour",
    },
    {
      id: 3,
      text: "par semaine",
    },
    {
      id: 4,
      text: "par mois",
    },
  ];

  diabetesControl = new FormControl(null);
  diabetesDetailControl = new FormControl(null);

  ugdControl = new FormControl(null);
  htaControl = new FormControl(null);
  asthmaControl = new FormControl(null);

  drepanocytoseControl = new FormControl(null);
  drepanocytoseDetailControl = new FormControl(null);

  alcoholismControl = new FormControl(null);

  smokingControl = new FormControl(null);
  smokingDetailTextControl = new FormControl(null);
  smokingDetailControl1 = new FormControl(null);
  smokingDetailControl2 = new FormControl(null);

  medicinesInUseControl = new FormControl(null);
  surgeriesControl = new FormControl(null);
  hospitalisationsControl = new FormControl(null);

  allergiesControl = new FormControl(null);
  othersControl = new FormControl(null);

  backgroundsForm!: FormGroup;

  allMSH = [
    {
      id: "meds",
      title: "Liste des médicaments",
      label: "Médicament",
    },
    {
      id: "surg",
      title: "Liste des chirurgies",
      label: "Chirurgie",
    },
    {
      id: "hospi",
      title: "Liste des hospitalisations",
      label: "Hospitalisation",
    },
  ];

  selectedMSH: any;

  medicinesInUse: string[] = [];
  surgeries: string[] = [];
  hospitalisations: string[] = [];

  constructor(
    private modalService: NgbModal,
    private patientVisitService: PatientVisitService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.backgroundsForm = new FormGroup({
      diabetes: this.diabetesControl,
      diabetesDetail: this.diabetesDetailControl,
    });

    this.initFieldsValue();
  }

  initFieldsValue() {
    if (this.patientInfos.antecedant) {
      const backgrounds = this.patientInfos.antecedant;

      if (backgrounds?.type_diabete) {
        this.diabetesControl.setValue(
          this.diabetesOptions.find((value) => value.id === true)
        );
      }

      this.diabetesDetailControl.setValue(
        this.diabetesDetailOptions.find(
          (value) =>
            String(value.text).trim() === backgrounds?.type_diabete?.trim()
        )
      );

      this.ugdControl.setValue(
        this.ugdOptions.find((value) => value.id === backgrounds?.has_ugd)
      );

      this.htaControl.setValue(
        this.htaOptions.find((value) => value.id === backgrounds?.has_hta)
      );

      this.asthmaControl.setValue(
        this.asthmaOptions.find((value) => value.id === backgrounds?.has_asthme)
      );

      this.drepanocytoseControl.setValue(
        this.drepanocytoseOptions.find(
          (value) => value.id === backgrounds?.has_drepano
        )
      );

      this.drepanocytoseDetailControl.setValue(
        this.drepanocytoseDetailOptions.find(
          (value) =>
            String(value.text).trim() === backgrounds?.type_drepano?.trim()
        )
      );

      this.alcoholismControl.setValue(
        this.alcoholismOptions.find(
          (value) => value.id === backgrounds?.has_alcool
        )
      );

      if (backgrounds?.nb_tabac) {
        this.smokingControl.setValue(
          this.smokingOptions.find((value) => value.id === true)
        );
      }

      this.smokingDetailTextControl.setValue(backgrounds?.nb_tabac);

      this.smokingDetailControl1.setValue(
        this.smokingDetailOptions1.find(
          (value) =>
            String(value.text).trim() === backgrounds?.mesure_tabac?.trim()
        )
      );

      this.smokingDetailControl2.setValue(
        this.smokingDetailOptions2.find(
          (value) =>
            String(value.text).trim() === backgrounds?.frequence_tabac?.trim()
        )
      );

      this.medicinesInUseControl.setValue(backgrounds?.nb_medic);

      this.surgeriesControl.setValue(backgrounds?.nb_chirurgie);

      this.hospitalisationsControl.setValue(backgrounds?.nb_hospit);

      this.allergiesControl.setValue(backgrounds?.allergies);

      this.othersControl.setValue(backgrounds?.autre);
    }
  }

  openMultiInputModal(mshIndex: number, nbFields: number, data?: string[]) {
    this.selectedMSH = this.allMSH[mshIndex];

    const mshModalRef = this.modalService.open(MultiInputModalComponent, {
      size: "xl",
      centered: true,
      scrollable: true,
      backdrop: "static",
    });

    mshModalRef.componentInstance.title = this.selectedMSH.title;
    mshModalRef.componentInstance.label = this.selectedMSH.label;
    mshModalRef.componentInstance.numberOfFields = nbFields;

    mshModalRef.componentInstance.data = data;

    mshModalRef.componentInstance.formData.subscribe((formData: string[]) => {
      if (mshIndex === 0) {
        this.medicinesInUse = formData;
      } else if (mshIndex === 1) {
        this.surgeries = formData;
      } else if (mshIndex === 2) {
        this.hospitalisations = formData;
      }

      mshModalRef.close();
    });
  }

  getBackgroundsFormData() {
    // console.log(
    //   this.patientInfos.antecedant?.medics?.length !== 0 &&
    //     this.medicinesInUse.length === 0
    // );

    // console.log(this.patientInfos.antecedant?.medics?.length !== 0);

    // console.log(this.medicinesInUse.length === 0);

    if (this.patientInfos.antecedant) {
      const backgrounds = this.patientInfos.antecedant;

      if (
        backgrounds.medicaments &&
        backgrounds.medicaments.length !== 0 &&
        this.medicinesInUse.length === 0
      ) {
        this.medicinesInUse = backgrounds.medicaments;
      }

      if (
        backgrounds.chirurgies &&
        backgrounds.chirurgies.length !== 0 &&
        this.surgeries.length === 0
      ) {
        this.surgeries = backgrounds.chirurgies;
      }

      if (
        backgrounds.hospitalisations &&
        backgrounds.hospitalisations.length !== 0 &&
        this.hospitalisations.length === 0
      ) {
        this.hospitalisations = backgrounds.hospitalisations;
      }
    }

    return new BackgroundsRequest({
      type: "adulte",

      type_diabete: this.diabetesDetailControl.value?.text,
      has_ugd: this.ugdControl.value?.id,
      has_hta: this.htaControl.value?.id,
      has_asthme: this.asthmaControl.value?.id,
      has_drepano: this.drepanocytoseControl.value?.id,
      type_drepano: this.drepanocytoseDetailControl.value?.text,
      has_alcool: this.alcoholismControl.value?.id,
      nb_tabac: this.smokingDetailTextControl.value,
      mesure_tabac: this.smokingDetailControl1.value?.text,
      frequence_tabac: this.smokingDetailControl2.value?.text,

      nb_medic: this.medicinesInUseControl.value ?? undefined,
      medicaments:
        parseIntOrZero(this.medicinesInUseControl.value) === 0
          ? undefined
          : this.medicinesInUse,
      nb_chirurgie: this.surgeriesControl.value ?? undefined,
      chirurgies:
        parseIntOrZero(this.surgeriesControl.value) === 0
          ? undefined
          : this.surgeries,
      nb_hospit: this.hospitalisationsControl.value ?? undefined,
      hospitalisations:
        parseIntOrZero(this.hospitalisationsControl.value) === 0
          ? undefined
          : this.hospitalisations,

      allergies: this.allergiesControl.value,
      autre: this.othersControl.value,
    });
  }

  registerBackgrounds() {
    if (this.backgroundsForm.invalid) {
      return;
    }

    const backgroundsData = this.getBackgroundsFormData();

    const patientVisitInfo = new PatientVisitInfoRequest({
      antecedant: backgroundsData,
    });

    console.log(JSON.stringify(patientVisitInfo, null, 2));

    this.patientVisitService
      .updateVisitInfo(this.patientInfos.reference, patientVisitInfo)
      .subscribe({
        next: (data) => {
          this.toastService.show({
            messages: ["Les antécédents ont été enregistrés avec succès."],
            type: ToastType.Success,
          });
        },
        error: (e) => {
          console.error(e);

          this.toastService.show({
            messages: ["Désolé, une erreur s'est produite"],
            delay: 10000,
            type: ToastType.Error,
          });
        },
      });
  }
}
