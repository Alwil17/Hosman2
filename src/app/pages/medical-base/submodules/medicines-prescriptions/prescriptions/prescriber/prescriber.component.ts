import { Component, Input, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { SelectOption } from "src/app/models/extras/select.model";
import { Form } from "src/app/models/medical-base/submodules/medicines-prescriptions/form.model";
import { Prescription } from "src/app/models/medical-base/submodules/medicines-prescriptions/prescription.model";
import { Product } from "src/app/models/medical-base/submodules/medicines-prescriptions/product.model";
import { PrescriptionRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/prescription-request.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { ProductService } from "src/app/services/medical-base/submodules/medicines-prescriptions/product.service";

@Component({
  selector: "app-prescriber",
  templateUrl: "./prescriber.component.html",
  styleUrls: ["./prescriber.component.scss"],
})
export class PrescriberComponent implements OnInit {
  @Input()
  patientInfos?: Patient;

  productData: Product[] = [];
  products: SelectOption[] = [];
  selectedProduct!: Product;

  productForms: Form[] = [];
  selectedProductForm!: Form;

  timeUnits: SelectOption[] = [
    {
      id: 1,
      text: "heure(s)",
    },
    {
      id: 2,
      text: "jour(s)",
    },
    {
      id: 3,
      text: "mois",
    },
    {
      id: 4,
      text: "année(s)",
    },
  ];

  prescriptions: Prescription[] = [];

  productNameController = new FormControl(null, Validators.required);
  productDciController = new FormControl(null);
  productFormController = new FormControl(null, Validators.required);
  quantityController = new FormControl(null, Validators.required);
  packagingController = new FormControl(null, Validators.required);
  dosageQuantityController = new FormControl(null);
  dosageController = new FormControl(null);
  periodController = new FormControl(null);
  adverbController = new FormControl(null);
  timeController = new FormControl(null);
  timeUnitController = new FormControl(null);
  noteController = new FormControl(null);

  prescriptionForm!: FormGroup;
  isPrescriptionFormSubmitted = false;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.prescriptionForm = new FormGroup({
      productName: this.productNameController,
      productDci: this.productDciController,
      productForm: this.productFormController,
      quantity: this.quantityController,
      packaging: this.packagingController,
      dosageQuantity: this.dosageQuantityController,
      dosage: this.dosageController,
      period: this.periodController,
      adverb: this.adverbController,
      time: this.timeController,
      timeUnit: this.timeUnitController,
      note: this.noteController,
    });

    this.fetchSelectData();

    this.onFieldsValueChanges();
  }

  fetchSelectData() {
    this.productService.getAll().subscribe({
      next: (data) => {
        this.productData = data;
        this.products = data.map((product) => ({
          id: product.id,
          text: product.nom,
        }));
      },
      error: (e) => {
        console.log(e);
      },
    });
  }

  onFieldsValueChanges() {
    this.productNameController.valueChanges.subscribe((value) => {
      if (value) {
        console.log(value);

        this.selectedProduct = this.productData.find(
          (product) => product.id == value.id
        )!;

        this.productDciController.setValue(this.selectedProduct.dci);

        this.productForms = this.selectedProduct.formes;
      }
    });
  }

  selectProductForm(productForm: Form) {
    this.selectedProductForm = productForm;

    this.productFormController.setValue(
      productForm.presentation + " - " + productForm.dosage
    );

    this.packagingController.setValue(productForm.conditionnement);
  }

  emptyPrescriptionFields() {
    this.isPrescriptionFormSubmitted = false;

    this.productNameController.setValue(null);
    this.productDciController.setValue(null);
    this.productFormController.setValue(null);
    this.quantityController.setValue(null);
    this.packagingController.setValue(null);
    this.dosageQuantityController.setValue(null);
    this.dosageController.setValue(null);
    this.periodController.setValue(null);
    this.adverbController.setValue(null);
    this.timeController.setValue(null);
    this.timeUnitController.setValue(null);
    this.noteController.setValue(null);

    this.productForms = [];
  }

  getPrescriptionFormData() {
    return new PrescriptionRequest({
      presentation: this.productFormController.value,
      qte: this.quantityController.value,
      conditionnement: this.packagingController.value,

      dose_qte: this.dosageQuantityController.value,
      dose: this.dosageController.value,
      periode: this.periodController.value,
      adverbe: this.adverbController.value,
      duree_qte: this.timeController.value,
      duree: this.timeUnitController.value?.text,
      note: this.noteController.value,
      produit_id: this.selectedProduct.id,
      forme_id: this.selectedProductForm.id,
    });
  }

  validatePrescription() {
    this.isPrescriptionFormSubmitted = true;

    if (this.prescriptionForm.invalid) {
      return;
    }

    const prescription = this.getPrescriptionFormData();

    console.log(JSON.stringify(prescription, null, 2));

    this.prescriptions.push(
      new Prescription({
        id: -1,
        ...prescription,
        produit: this.selectedProduct,
        forme: this.selectedProductForm,
      })
    );
  }
}
