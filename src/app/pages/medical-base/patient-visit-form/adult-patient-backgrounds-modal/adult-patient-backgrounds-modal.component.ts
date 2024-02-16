import { Component, OnInit } from "@angular/core";
import { FormArray, FormControl, FormGroup } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { SelectOption } from "src/app/models/extras/select.model";
import { MultiInputModalComponent } from "../multi-input-modal/multi-input-modal.component";

@Component({
  selector: "app-adult-patient-backgrounds-modal",
  templateUrl: "./adult-patient-backgrounds-modal.component.html",
  styleUrls: ["./adult-patient-backgrounds-modal.component.scss"],
})
export class AdultPatientBackgroundsModalComponent implements OnInit {
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

  constructor(private modalService: NgbModal) {}

  ngOnInit(): void {
    this.backgroundsForm = new FormGroup({
      diabetes: this.diabetesControl,
      diabetesDetail: this.diabetesDetailControl,
    });

    this.onFormFieldsChanges();
  }

  onFormFieldsChanges() {
    this.diabetesControl.valueChanges.subscribe((value) => {});
  }

  openMultiInputModal(mshIndex: number, nbFields: number) {
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
  }
}
