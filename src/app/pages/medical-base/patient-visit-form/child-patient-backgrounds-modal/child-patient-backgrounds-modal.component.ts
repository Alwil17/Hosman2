import { Component, Input, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { SelectOption } from "src/app/models/extras/select.model";
import { MultiInputModalComponent } from "../multi-input-modal/multi-input-modal.component";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { BackgroundsRequest } from "src/app/models/secretariat/patients/requests/backgrounds-request.model";
import { PatientVisitInfoRequest } from "src/app/models/secretariat/patients/requests/patient-visit-info-request.model";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ToastType } from "src/app/models/extras/toast-type.model";

@Component({
  selector: "app-child-patient-backgrounds-modal",
  templateUrl: "./child-patient-backgrounds-modal.component.html",
  styleUrls: ["./child-patient-backgrounds-modal.component.scss"],
})
export class ChildPatientBackgroundsModalComponent implements OnInit {
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

  birthedByOptions: SelectOption[] = [
    {
      id: 1,
      text: "voie basse",
    },
    {
      id: 2,
      text: "césarienne",
    },
  ];
  birthedByDetailOptions: SelectOption[] = [
    {
      id: 1,
      text: "Souffrance foetale",
    },
    {
      id: 2,
      text: "X",
    },
  ];

  revivedOptions: SelectOption[] = this.yesOrNoOptions;

  scholarizedOptions: SelectOption[] = this.yesOrNoOptions;

  birthedByControl = new FormControl(null);
  birthedByDetailControl = new FormControl(null);

  revivedControl = new FormControl(null);

  hospitalisationsControl = new FormControl(null);

  allergiesControl = new FormControl(null);

  scholarizedControl = new FormControl(null);

  scholarGradeControl = new FormControl(null);

  schoolNameControl = new FormControl(null);
  schoolAddressControl = new FormControl(null);
  schoolContactControl = new FormControl(null);

  backgroundsForm!: FormGroup;

  allMSH = [
    {
      id: "hospi",
      title: "Liste des hospitalisations",
      label: "Hospitalisation",
    },
  ];

  selectedMSH: any;

  matGrades = ["Maternelle 1", "Maternelle 2"];
  primGrades = ["CP1", "CP2", "CE1", "CE2", "CM1", "CM2"];
  middleGrades = ["6è", "5è", "4è", "3è"];
  highGrades = ["2nde", "1ère", "Terminale", "Université"];

  hospitalisations: string[] = [];

  constructor(
    private modalService: NgbModal,
    private patientVisitService: PatientVisitService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.backgroundsForm = new FormGroup({
      birthedBy: this.birthedByControl,
      birthedByDetail: this.birthedByDetailControl,
      revived: this.revivedControl,
      hospitalisations: this.hospitalisationsControl,
      allergies: this.allergiesControl,
      scholarGrade: this.scholarGradeControl,
      schoolName: this.schoolNameControl,
      schoolAddress: this.schoolAddressControl,
      schoolContact: this.schoolContactControl,
    });

    this.initFieldsValue();
  }

  initFieldsValue() {
    if (this.patientInfos.antecedant) {
      const backgrounds = this.patientInfos.antecedant;

      this.birthedByControl.setValue(
        this.birthedByOptions.find(
          (value) =>
            String(value.text).trim() === backgrounds?.voie_accouch?.trim()
        )
      );

      this.birthedByDetailControl.setValue(
        this.birthedByDetailOptions.find(
          (value) =>
            String(value.text).trim() === backgrounds?.voie_cause?.trim()
        )
      );

      this.revivedControl.setValue(
        this.revivedOptions.find(
          (value) => value.id === backgrounds?.is_reanime
        )
      );

      this.hospitalisationsControl.setValue(backgrounds?.nb_hospit);

      this.allergiesControl.setValue(backgrounds?.allergies);

      this.scholarizedControl.setValue(
        this.scholarizedOptions.find(
          (value) => value.id === backgrounds?.is_scolarise
        )
      );

      this.scholarGradeControl.setValue(
        [
          ...this.matGrades,
          ...this.primGrades,
          ...this.middleGrades,
          ...this.highGrades,
        ].find(
          (value) => value.trim() === backgrounds?.classe_scolarise?.trim()
        )
      );
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
        this.hospitalisations = formData;
      }

      mshModalRef.close();
    });
  }

  getBackgroundsFormData() {
    if (this.patientInfos.antecedant) {
      if (
        this.patientInfos.antecedant?.hospits?.length !== 0 &&
        this.hospitalisations.length === 0
      ) {
        this.hospitalisations = this.patientInfos.antecedant?.hospits!;
      }
    }

    return new BackgroundsRequest({
      type: "enfant",

      voie_accouch: this.birthedByControl.value?.text,
      voie_cause: this.birthedByDetailControl.value?.text,

      is_reanime: this.revivedControl.value?.id,

      nb_hospit: this.hospitalisations.length,
      hospits: this.hospitalisations,

      allergies: this.allergiesControl.value,

      is_scolarise: this.scholarizedControl.value?.id,
      classe_scolarise: this.scholarGradeControl.value,
    });
  }

  registerBackgrounds() {
    if (this.backgroundsForm.invalid) {
      // const notificationMessages = this.getInvalidFields();

      // this.toastService.show({
      //   messages: notificationMessages,
      //   type: ToastType.Warning,
      // });

      return;
    }

    const backgroundsData = this.getBackgroundsFormData();

    const patientVisitInfo = new PatientVisitInfoRequest({
      antecedant: backgroundsData,
    });

    console.log(JSON.stringify(patientVisitInfo, null, 2));

    // this.patientVisitService
    //   .updateVisitInfo(this.patientInfos.reference, patientVisitInfo)
    //   .subscribe({
    //     next: (data) => {
    //       this.toastService.show({
    //         messages: ["Les antécédents ont été enregistrés avec succès."],
    //         type: ToastType.Success,
    //       });
    //     },
    //     error: (e) => {
    //       console.error(e);

    //       this.toastService.show({
    //         messages: ["Désolé, une erreur s'est produite"],
    //         delay: 10000,
    //         type: ToastType.Error,
    //       });
    //     },
    //   });
  }
}
