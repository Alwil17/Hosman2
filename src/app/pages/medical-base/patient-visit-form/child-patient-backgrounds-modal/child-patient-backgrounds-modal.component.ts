import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { SelectOption } from "src/app/models/extras/select.model";
import { MultiInputModalComponent } from "../multi-input-modal/multi-input-modal.component";

@Component({
  selector: "app-child-patient-backgrounds-modal",
  templateUrl: "./child-patient-backgrounds-modal.component.html",
  styleUrls: ["./child-patient-backgrounds-modal.component.scss"],
})
export class ChildPatientBackgroundsModalComponent implements OnInit {
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

  unknownOptions: SelectOption[] = this.yesOrNoOptions;

  scholarizedOptions: SelectOption[] = this.yesOrNoOptions;

  birthedByControl = new FormControl(null);
  birthedByDetailControl = new FormControl(null);

  unknownControl = new FormControl(null);

  hospitalisationsControl = new FormControl(null);

  allergiesControl = new FormControl(null);

  scholarizedControl = new FormControl(null);

  scholarGradeControl = new FormControl("");

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

  constructor(private modalService: NgbModal) {}

  ngOnInit(): void {
    this.backgroundsForm = new FormGroup({
      birthedBy: this.birthedByControl,
      birthedByDetail: this.birthedByDetailControl,
      unknown: this.unknownControl,
      hospitalisations: this.hospitalisationsControl,
      allergies: this.allergiesControl,
      scholarGrade: this.scholarGradeControl,
    });

    // this.onFormFieldsChanges();
  }

  // onFormFieldsChanges() {
  //   this.birthedByControl.valueChanges.subscribe((value) => {});
  // }

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
