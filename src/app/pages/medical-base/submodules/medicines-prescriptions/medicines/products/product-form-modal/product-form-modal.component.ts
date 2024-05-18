import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormArray, FormControl, FormGroup, Validators } from "@angular/forms";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Product } from "src/app/models/medical-base/submodules/medicines-prescriptions/product.model";
import { ContraIndicationRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/contra-indication-request.model";
import { FormRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/form-request.model";
import { IndicationRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/indication-request.model";
import { PosologyRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/posology-request.model";
import { ProductRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/product-request.model";
import { SideEffectRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/side-effect-request.model";
import {
  TherapeuticClassProductRequest,
  TherapeuticClassRequest,
} from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/therapeutic-class-request.model";
import { ProductService } from "src/app/services/medical-base/submodules/medicines-prescriptions/product.service";
import { TherapeuticClassService } from "src/app/services/medical-base/submodules/medicines-prescriptions/therapeutic-class.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-product-form-modal",
  templateUrl: "./product-form-modal.component.html",
  styleUrls: ["./product-form-modal.component.scss"],
})
export class ProductFormModalComponent implements OnInit {
  @Input()
  productInfos?: Product;

  @Output()
  isProductCreated = new EventEmitter<boolean>();

  @Output()
  isProductModified = new EventEmitter<boolean>();

  therapeuticClasses: SelectOption[] = [];

  nameController = new FormControl(null, Validators.required);
  dciController = new FormControl(null, Validators.required);

  infosController = new FormControl(null);

  productForm!: FormGroup;
  isProductFormSubmitted = false;

  constructor(
    private productService: ProductService,
    private therapeuticClassService: TherapeuticClassService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.productForm = new FormGroup({
      name: this.nameController,
      dci: this.dciController,

      classes: new FormArray([], Validators.minLength(1)),
      indications: new FormArray([]),
      sideEffects: new FormArray([]),
      contraIndications: new FormArray([]),
      childPosologies: new FormArray([]),
      adultPosologies: new FormArray([]),

      forms: new FormArray([], Validators.minLength(1)),
    });

    this.therapeuticClassService.getAll().subscribe({
      next: (data) => {
        this.therapeuticClasses = data.map((value) => ({
          id: value.id,
          text: value.nom,
        }));

        this.setProductFormFieldsInitialValues();
      },
      error: (error) => {
        console.error(error);

        this.toastService.show({
          messages: [
            "Une erreur s'est produite lors de la récupération des classes thérapeutiques.",
          ],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });

    // this.setProductFormFieldsInitialValues();
  }

  setProductFormFieldsInitialValues() {
    // Check if productInfos has value
    if (this.productInfos) {
      this.nameController.setValue(this.productInfos.nom);
      this.dciController.setValue(this.productInfos.dci);
      this.infosController.setValue(this.productInfos.infos);

      this.productInfos.classes.forEach((value) => {
        this.addClassesField({
          id: value.id,
          text: value.nom,
        });
      });

      this.productInfos.indications?.forEach((value) => {
        this.addIndicationsField(value.libelle);
      });

      this.productInfos.effet_secondaires?.forEach((value) => {
        this.addSideEffectsField(value.libelle);
      });

      this.productInfos.contre_indications?.forEach((value) => {
        this.addContraIndicationsField(value.libelle);
      });

      this.productInfos.posologies?.forEach((value) => {
        if (value.type === "enfant") {
          this.addChildPosologiesField(value.libelle);
        } else if (value.type === "adulte") {
          this.addAdultPosologiesField(value.libelle);
        }
      });

      this.productInfos.formes.forEach((value) => {
        this.addFormsField({
          presentation: value.presentation,
          dosage: value.dosage,
          packaging: value.conditionnement,
          price: value.prix,
        });
      });
    }

    // For each dynamic field, add a field if there is none
    if (this.classesFields.length === 0) {
      this.addClassesField();
    }

    if (this.indicationsFields.length === 0) {
      this.addIndicationsField();
    }

    if (this.sideEffectsFields.length === 0) {
      this.addSideEffectsField();
    }

    if (this.contraIndicationsFields.length === 0) {
      this.addContraIndicationsField();
    }

    if (this.childPosologiesFields.length === 0) {
      this.addChildPosologiesField();
    }

    if (this.adultPosologiesFields.length === 0) {
      this.addAdultPosologiesField();
    }

    if (this.formsFields.length === 0) {
      this.addFormsField();
    }
  }

  emptyProductFormFields() {
    this.isProductFormSubmitted = false;

    this.nameController.setValue(null);
    this.nameController.markAsUntouched();
    this.nameController.markAsPristine();

    this.dciController.setValue(null);
    this.dciController.markAsUntouched();
    this.dciController.markAsPristine();

    this.infosController.setValue(null);
    this.infosController.markAsUntouched();
    this.infosController.markAsPristine();

    while (this.classesFields.controls.length != 0) {
      this.removeClassesField(0);
    }

    while (this.indicationsFields.controls.length != 0) {
      this.removeIndicationsField(0);
    }

    while (this.sideEffectsFields.controls.length != 0) {
      this.removeSideEffectsField(0);
    }

    while (this.contraIndicationsFields.controls.length != 0) {
      this.removeContraIndicationsField(0);
    }

    while (this.childPosologiesFields.controls.length != 0) {
      this.removeChildPosologiesField(0);
    }

    while (this.adultPosologiesFields.controls.length != 0) {
      this.removeAdultPosologiesField(0);
    }

    while (this.formsFields.controls.length != 0) {
      this.removeFormsField(0);
    }

    this.setProductFormFieldsInitialValues();
  }

  // getInvalidFields() {
  //   const invalidInputs: string[] = [];
  //   this.inputFields.forEach((input) => {
  //     if (input.control.invalid) {
  //       invalidInputs.push("- " + input.label);
  //     }
  //   });

  //   const invalidSelects: string[] = [];
  //   this.selectFields.forEach((select) => {
  //     if (select.control.invalid) {
  //       invalidSelects.push("- " + select.label);
  //     }
  //   });

  //   let notificationMessages: string[] = [];
  //   if (invalidInputs.length !== 0) {
  //     notificationMessages.push(
  //       WarningMessages.MANDATORY_INPUT_FIELDS,
  //       ...invalidInputs
  //     );
  //   }

  //   if (notificationMessages.length !== 0) {
  //     notificationMessages.push("");
  //   }

  //   if (invalidSelects.length !== 0) {
  //     notificationMessages.push(
  //       WarningMessages.MANDATORY_SELECT_FIELDS,
  //       ...invalidSelects
  //     );
  //   }

  //   return notificationMessages;
  // }

  getProductFormData() {
    const classes: TherapeuticClassRequest[] = [];
    const indications: IndicationRequest[] = [];
    const sideEffects: SideEffectRequest[] = [];
    const contraIndications: ContraIndicationRequest[] = [];
    const childPosologies: PosologyRequest[] = [];
    const adultPosologies: PosologyRequest[] = [];
    const forms: FormRequest[] = [];

    this.classesFields.controls.forEach((control) => {
      if (control.value) {
        classes.push(new TherapeuticClassRequest({ nom: control.value.text }));
      }
    });

    this.indicationsFields.controls.forEach((control) => {
      if (control.value) {
        indications.push(new IndicationRequest({ libelle: control.value }));
      }
    });

    this.sideEffectsFields.controls.forEach((control) => {
      if (control.value) {
        sideEffects.push(new SideEffectRequest({ libelle: control.value }));
      }
    });

    this.contraIndicationsFields.controls.forEach((control) => {
      if (control.value) {
        contraIndications.push(
          new ContraIndicationRequest({ libelle: control.value })
        );
      }
    });

    this.childPosologiesFields.controls.forEach((control) => {
      if (control.value) {
        childPosologies.push(
          new PosologyRequest({ libelle: control.value, type: "enfant" })
        );
      }
    });

    this.adultPosologiesFields.controls.forEach((control) => {
      if (control.value) {
        adultPosologies.push(
          new PosologyRequest({ libelle: control.value, type: "adulte" })
        );
      }
    });

    this.formsFields.controls.forEach((formGroup) => {
      if (formGroup.value) {
        forms.push(
          new FormRequest({
            presentation: formGroup.get("presentation")!.value,
            dosage: formGroup.get("dosage")!.value,
            conditionnement: formGroup.get("packaging")!.value,
            prix: formGroup.get("price")!.value,
          })
        );
      }
    });

    return new ProductRequest({
      nom: this.nameController.value,
      dci: this.dciController.value,
      infos: this.infosController.value,
      classes: classes,
      indications: indications,
      effet_secondaires: sideEffects,
      contre_indications: contraIndications,
      posologies: [...childPosologies, ...adultPosologies],
      formes: forms,
    });
  }

  registerProduct(emit: boolean = false) {
    this.isProductFormSubmitted = true;

    // this.emptyProductFormFields();

    if (this.productForm.invalid) {
      // const notificationMessages = this.getInvalidFields();

      // this.toastService.show({
      //   messages: notificationMessages,
      //   type: ToastType.Warning,
      // });

      return;
    }

    const productData = this.getProductFormData();

    console.log(JSON.stringify(productData, null, 2));

    this.productService.create(productData).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Le produit a été enregistré avec succès."],
          type: ToastType.Success,
        });

        if (emit) {
          this.isProductCreated.emit(true);
        } else {
          this.emptyProductFormFields();
        }
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Désolé, une erreur s'est produite"],
          delay: 10000,
          type: ToastType.Error,
        });

        if (emit) {
          this.isProductCreated.emit(false);
        }
      },
    });
  }

  registerModifications() {
    this.isProductFormSubmitted = true;

    if (this.productForm.invalid) {
      // const notificationMessages = this.getInvalidFields();

      // this.toastService.show({
      //   messages: notificationMessages,
      //   type: ToastType.Warning,
      // });

      return;
    }

    const productData = this.getProductFormData();

    console.log(JSON.stringify(productData, null, 2));

    this.productService.update(this.productInfos?.id, productData).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Le produit a été modifié avec succès."],
          type: ToastType.Success,
        });

        this.isProductModified.emit(true);
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          messages: ["Désolé, une erreur s'est produite"],
          delay: 10000,
          type: ToastType.Error,
        });

        this.isProductModified.emit(false);
      },
    });
  }

  // CLASSES FIELDS --------------------------------------------------------------------------------------------------------
  get classesFields() {
    return this.productForm.get("classes") as FormArray;
  }

  addClassesField(value: SelectOption | null = null) {
    this.classesFields.push(new FormControl(value, Validators.required));
    console.log(this.classesFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removeClassesField(index: number) {
    this.classesFields.removeAt(index);
    console.log(this.classesFields);
  }

  // INDICATIONS FIELDS --------------------------------------------------------------------------------------------------------
  get indicationsFields() {
    return this.productForm.get("indications") as FormArray;
  }

  addIndicationsField(value: string | null = null) {
    this.indicationsFields.push(new FormControl(value));
    console.log(this.indicationsFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removeIndicationsField(index: number) {
    this.indicationsFields.removeAt(index);
    console.log(this.indicationsFields);
  }

  // SIDE EFFECTS FIELDS --------------------------------------------------------------------------------------------------------
  get sideEffectsFields() {
    return this.productForm.get("sideEffects") as FormArray;
  }

  addSideEffectsField(value: string | null = null) {
    this.sideEffectsFields.push(new FormControl(value));
    console.log(this.sideEffectsFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removeSideEffectsField(index: number) {
    this.sideEffectsFields.removeAt(index);
    console.log(this.sideEffectsFields);
  }

  // CONTRAINDICATIONS FIELDS --------------------------------------------------------------------------------------------------------
  get contraIndicationsFields() {
    return this.productForm.get("contraIndications") as FormArray;
  }

  addContraIndicationsField(value: string | null = null) {
    this.contraIndicationsFields.push(new FormControl(value));
    console.log(this.contraIndicationsFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removeContraIndicationsField(index: number) {
    this.contraIndicationsFields.removeAt(index);
    console.log(this.contraIndicationsFields);
  }

  // CHILD POSOLOGIES FIELDS --------------------------------------------------------------------------------------------------------
  get childPosologiesFields() {
    return this.productForm.get("childPosologies") as FormArray;
  }

  addChildPosologiesField(value: string | null = null) {
    this.childPosologiesFields.push(new FormControl(value));
    console.log(this.childPosologiesFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removeChildPosologiesField(index: number) {
    this.childPosologiesFields.removeAt(index);
    console.log(this.childPosologiesFields);
  }

  // ADULT POSOLOGIES FIELDS --------------------------------------------------------------------------------------------------------
  get adultPosologiesFields() {
    return this.productForm.get("adultPosologies") as FormArray;
  }

  addAdultPosologiesField(value: string | null = null) {
    this.adultPosologiesFields.push(new FormControl(value));
    console.log(this.adultPosologiesFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removeAdultPosologiesField(index: number) {
    this.adultPosologiesFields.removeAt(index);
    console.log(this.adultPosologiesFields);
  }

  // FORMS FIELDS --------------------------------------------------------------------------------------------------------
  get formsFields() {
    return this.productForm.get("forms") as FormArray;
  }

  addFormsField(
    value: {
      presentation: string;
      dosage: string;
      packaging: string;
      price: number;
    } | null = null
  ) {
    this.formsFields.push(
      new FormGroup({
        presentation: new FormControl(
          value?.presentation ?? null,
          Validators.required
        ),
        dosage: new FormControl(value?.dosage ?? null, Validators.required),
        packaging: new FormControl(
          value?.packaging ?? null,
          Validators.required
        ),
        price: new FormControl(value?.price ?? null, Validators.required),
      })
    );
    console.log(this.formsFields);

    // setTimeout(() => {
    //   document.body
    //     .querySelector("#actsSelects")
    //     ?.querySelector("input")
    //     ?.focus();
    // }, 0);
  }

  removeFormsField(index: number) {
    this.formsFields.removeAt(index);
    console.log(this.formsFields);
  }
}
