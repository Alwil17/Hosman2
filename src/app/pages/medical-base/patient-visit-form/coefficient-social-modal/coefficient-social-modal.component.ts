import { Component, Input, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { merge } from "rxjs";
import { parseFloatOrZero, parseIntOrZero } from "src/app/helpers/parsers";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { CoefficientSocialRequest } from "src/app/models/secretariat/patients/requests/coefficient-social-request.model";
import { PatientVisitInfoRequest } from "src/app/models/secretariat/patients/requests/patient-visit-info-request.model";
import { PatientVisitService } from "src/app/services/medical-base/patient-visit.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-coefficient-social-modal",
  templateUrl: "./coefficient-social-modal.component.html",
  styleUrls: ["./coefficient-social-modal.component.scss"],
})
export class CoefficientSocialModalComponent implements OnInit {
  @Input()
  patientInfos!: Patient;

  nbeOptions: SelectOption[] = [
    // {
    //   id: 1,
    //   text: "0 - Pas d'épouse",
    // },
    {
      id: 1,
      text: "1 - 1e épouse",
    },
    {
      id: 2,
      text: "2 - 2 épouses",
    },
    {
      id: 3,
      text: "3 - 3 épouses",
    },
  ];

  niOptions: SelectOption[] = [
    {
      id: 0,
      text: "0 - Pas d'instructions",
    },
    {
      id: 0.1,
      text: "0,1 - CP1",
    },
    {
      id: 0.2,
      text: "0,2 - CP2",
    },
    {
      id: 0.3,
      text: "0,3 - CE1",
    },
    {
      id: 0.4,
      text: "0,4 - CE2",
    },
    {
      id: 0.5,
      text: "0,5 - CM1",
    },
    {
      id: 0.6,
      text: "0,6 - CM2",
    },
    {
      id: 0.7,
      text: "0,7 - 6ème",
    },
    {
      id: 0.8,
      text: "0,8 - 5ème",
    },
    {
      id: 0.9,
      text: "0,9 - 4ème",
    },
    {
      id: 1,
      text: "1 - 3ème (BEPC)",
    },
    {
      id: 1.2,
      text: "1,2 - 2nde ",
    },
    {
      id: 1.5,
      text: "1,5 - 1ère (Avec ou sans Proba) ",
    },
    {
      id: 2,
      text: "2 - Terminale (Avec ou sans BAC)",
    },
    {
      id: 2.1,
      text: "2,1 - BAC +1",
    },
    {
      id: 2.2,
      text: "2,2 - BAC +2",
    },
    {
      id: 2.3,
      text: "2,3 - BAC +3",
    },
    {
      id: 2.4,
      text: "2,4 - BAC +4",
    },
    {
      id: 2.5,
      text: "2,5 - BAC +5",
    },
    {
      id: 2.6,
      text: "2,6 - BAC +6",
    },
    {
      id: 2.7,
      text: "2,7 - BAC +7",
    },
    {
      id: 2.8,
      text: "2,8 - BAC +8",
    },
    {
      id: 2.9,
      text: "2,9 - BAC +9",
    },
    {
      id: 3,
      text: "3 - BAC +10",
    },
  ];

  mfOptions: SelectOption[] = [
    {
      id: 0,
      text: "0 - Mère absente du foyer",
    },
    {
      id: 0.5,
      text: "0,5 - Mère divorcée",
    },
    {
      id: 1,
      text: "1 - Mère présente au foyer",
    },
  ];

  pfOptions: SelectOption[] = [
    {
      id: 0,
      text: "0 - Père absent du foyer",
    },
    {
      id: 0.5,
      text: "0,5 - Père divorcé",
    },
    {
      id: 1,
      text: "1 - Père présent au foyer",
    },
  ];

  assOptions: SelectOption[] = [
    {
      id: 0,
      text: "0 - Non assuré",
    },
    {
      id: 1,
      text: "1 - Assurance locale",
    },
    {
      id: 2,
      text: "2 - Assurance étrangère",
    },
  ];

  imvOptions: SelectOption[] = [
    {
      id: 0,
      text: "0 - Non vacciné",
    },
    {
      id: 0.5,
      text: "0,5 - Moitié des vaccins",
    },
    {
      id: 1,
      text: "1 - Tous les vaccins",
    },
  ];

  nbeControl = new FormControl(null, Validators.required);
  nimControl = new FormControl(null, Validators.required);
  nipControl = new FormControl(null, Validators.required);

  smfControl = new FormControl(null);
  mniControl = new FormControl(null);
  mfControl = new FormControl(null, Validators.required);
  pfControl = new FormControl(null, Validators.required);
  assControl = new FormControl(null, Validators.required);
  imvControl = new FormControl(null, Validators.required);

  coefficientSocialControl = new FormControl(null);

  interpretationControl = new FormControl(null);

  coefficientSocialForm!: FormGroup;

  constructor(
    private patientVisitService: PatientVisitService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.coefficientSocialForm = new FormGroup({
      nbe: this.nbeControl,
      nim: this.nimControl,
      nip: this.nipControl,

      smf: this.smfControl,
      mni: this.mniControl,
      mf: this.mfControl,
      pf: this.pfControl,
      ass: this.assControl,
      imv: this.imvControl,

      coefficientSocial: this.coefficientSocialControl,

      interpretation: this.interpretationControl,
    });

    this.onFieldsValueChanges();

    this.initFieldsValue();
  }

  onFieldsValueChanges() {
    this.nbeControl.valueChanges.subscribe((value) => {
      if (value) {
        const valueNum = parseFloatOrZero(value.id);

        if (valueNum === 1) {
          this.smfControl.setValue(2);
        } else if (valueNum === 2) {
          this.smfControl.setValue(1);
        } else if (valueNum === 3) {
          this.smfControl.setValue(0);
        }

        this.calculateCoefficientSocialAndInterprete();
      }
    });

    merge(this.nimControl.valueChanges, this.nipControl.valueChanges).subscribe(
      {
        next: (value) => {
          if (value && this.nimControl.valid && this.nipControl.valid) {
            const nimValue = parseFloatOrZero(this.nimControl.value.id);
            const nipValue = parseFloatOrZero(this.nipControl.value.id);

            const mniValue = parseFloatOrZero((nimValue + nipValue) / 2);

            console.log("MNI : " + mniValue);

            this.mniControl.setValue(mniValue);

            setTimeout(() => {
              this.calculateCoefficientSocialAndInterprete();
            });
          }
        },
      }
    );

    merge(
      this.mfControl.valueChanges,
      this.pfControl.valueChanges,
      this.assControl.valueChanges,
      this.imvControl.valueChanges
    ).subscribe({
      next: (value) => {
        setTimeout(() => {
          this.calculateCoefficientSocialAndInterprete();
        });
      },
    });

    // this.mfControl.valueChanges.subscribe((value) => {
    //   if (value) {
    //     this.calculateCoefficientSocialAndInterprete();
    //   }
    // });
    // this.pfControl.valueChanges.subscribe((value) => {
    //   if (value) {
    //     this.calculateCoefficientSocialAndInterprete();
    //   }
    // });
    // this.assControl.valueChanges.subscribe((value) => {
    //   if (value) {
    //     this.calculateCoefficientSocialAndInterprete();
    //   }
    // });
    // this.imvControl.valueChanges.subscribe((value) => {
    //   if (value) {
    //     this.calculateCoefficientSocialAndInterprete();
    //   }
    // });
  }

  initFieldsValue() {
    if (this.patientInfos.coefficient) {
      const coefficient = this.patientInfos.coefficient;

      this.nbeControl.setValue(
        this.nbeOptions.find((value) => value.id == coefficient.nbe)
      );

      this.nimControl.setValue(
        this.niOptions.find((value) => value.id == coefficient.nim)
      );

      this.nipControl.setValue(
        this.niOptions.find((value) => value.id == coefficient.nip)
      );

      this.smfControl.setValue(coefficient.smf);

      this.mniControl.setValue(coefficient.mni);

      this.mfControl.setValue(
        this.mfOptions.find((value) => value.id == coefficient.mf)
      );

      this.pfControl.setValue(
        this.pfOptions.find((value) => value.id == coefficient.pf)
      );

      this.assControl.setValue(
        this.assOptions.find((value) => value.id == coefficient.ass)
      );

      this.imvControl.setValue(
        this.imvOptions.find((value) => value.id == coefficient.imv)
      );
    }
  }

  getCoefficientSocialFormData() {
    return new CoefficientSocialRequest({
      nbe: this.nbeControl.value.id,
      nim: this.nimControl.value.id,
      nip: this.nipControl.value.id,
      smf: this.smfControl.value,
      mni: this.mniControl.value,
      mf: this.mfControl.value.id,
      pf: this.pfControl.value.id,
      ass: this.assControl.value.id,
      imv: this.imvControl.value.id,
      coef: this.coefficientSocialControl.value,
      commentaire: this.interpretationControl.value,
    });
  }

  calculateCoefficientSocialAndInterprete() {
    console.log("Form valid : " + this.coefficientSocialForm.valid);
    console.log(
      "Values : " +
        JSON.stringify(this.coefficientSocialForm.getRawValue(), null, 2)
    );
    console.log(this.coefficientSocialForm.controls);

    console.log("nbe : " + this.nbeControl.valid);
    console.log("nim : " + this.nimControl.valid);
    console.log("nip : " + this.nipControl.valid);

    console.log("mf : " + this.mfControl.valid);
    console.log("pf : " + this.pfControl.valid);
    console.log("ass : " + this.assControl.valid);
    console.log("imv : " + this.imvControl.valid);

    if (this.coefficientSocialForm.valid) {
      const smf = parseFloatOrZero(this.smfControl.value);
      const mni = parseFloatOrZero(this.mniControl.value);
      const mf = parseFloatOrZero(this.mfControl.value.id);
      const pf = parseFloatOrZero(this.pfControl.value.id);
      const ass = parseFloatOrZero(this.assControl.value.id);
      const imv = parseFloatOrZero(this.imvControl.value.id);

      const coefficientSocial = smf + mni + mf + pf + ass + imv;

      this.coefficientSocialControl.setValue(coefficientSocial);

      let interpretation = "";

      if (coefficientSocial >= 0 && coefficientSocial < 2) {
        interpretation = "Mauvais";
      } else if (coefficientSocial >= 2 && coefficientSocial < 4) {
        interpretation = "Médiocre";
      } else if (coefficientSocial >= 4 && coefficientSocial < 5) {
        interpretation = "Passable";
      } else if (coefficientSocial >= 5 && coefficientSocial < 6) {
        interpretation = "Moyen";
      } else if (coefficientSocial >= 6 && coefficientSocial < 7) {
        interpretation = "Acceptable";
      } else if (coefficientSocial >= 7 && coefficientSocial < 9) {
        interpretation = "Bien";
      } else if (coefficientSocial >= 9 && coefficientSocial <= 10) {
        interpretation = "Excellent";
      }

      this.interpretationControl.setValue(interpretation);
    }
  }

  registerCoefficientSocial() {
    if (this.coefficientSocialForm.invalid) {
      // const notificationMessages = this.getInvalidFields();

      // this.toastService.show({
      //   messages: notificationMessages,
      //   type: ToastType.Warning,
      // });

      return;
    }

    // this.calculateCoefficientSocialAndInterprete();

    const coefficientSocialData = this.getCoefficientSocialFormData();

    const patientVisitInfo = new PatientVisitInfoRequest({
      coefficient: coefficientSocialData,
    });

    console.log(JSON.stringify(patientVisitInfo, null, 2));

    this.patientVisitService
      .updateVisitInfo(this.patientInfos.reference, patientVisitInfo)
      .subscribe({
        next: (data) => {
          this.toastService.show({
            messages: ["Le coefficient a été enregistré avec succès."],
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
