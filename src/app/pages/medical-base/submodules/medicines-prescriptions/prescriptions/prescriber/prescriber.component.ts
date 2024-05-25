import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MedicalBaseRouterService } from "@services/medical-base/router/medical-base-router.service";
import { BehaviorSubject, Observable, Subject, forkJoin } from "rxjs";
import { PrescriptionFields } from "src/app/models/enums/prescription-fields.enum";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Consultation } from "src/app/models/medical-base/consultation.model";
import { Form } from "src/app/models/medical-base/submodules/medicines-prescriptions/form.model";
import { Prescription } from "src/app/models/medical-base/submodules/medicines-prescriptions/prescription.model";
import { Product } from "src/app/models/medical-base/submodules/medicines-prescriptions/product.model";
import { PrescriptionListRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/prescription-list-request.model";
import { PrescriptionRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/prescription-request.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { PrescriberService } from "src/app/services/medical-base/submodules/medicines-prescriptions/prescriber.service";
import { ProductService } from "src/app/services/medical-base/submodules/medicines-prescriptions/product.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-prescriber",
  templateUrl: "./prescriber.component.html",
  styleUrls: ["./prescriber.component.scss"],
})
export class PrescriberComponent implements OnInit {
  @Input()
  patientInfos?: Patient;

  @Input()
  consultationId$ = new BehaviorSubject<number | null>(null);

  @Output()
  prescriptionsRegistering = new EventEmitter<boolean>();

  productData: Product[] = [];
  selectedProduct!: Product;
  products: SelectOption[] = [];
  doses: SelectOption[] = [];
  adverbs: SelectOption[] = [];
  durations: SelectOption[] = [];
  periods: SelectOption[] = [];

  productForms: Form[] = [];
  selectedProductForm!: Form;

  // timeUnits: SelectOption[] = [
  //   {
  //     id: 1,
  //     text: "heure(s)",
  //   },
  //   {
  //     id: 2,
  //     text: "jour(s)",
  //   },
  //   {
  //     id: 3,
  //     text: "mois",
  //   },
  //   {
  //     id: 4,
  //     text: "année(s)",
  //   },
  // ];

  @Input()
  prescriptions$ = new Subject<Prescription[]>();

  @Input()
  prescriptions: Prescription[] = [];
  prescriptionsRequest: PrescriptionRequest[] = [];
  prescriptionListRequest?: PrescriptionListRequest;

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

  constructor(
    private medicalBaseRouter: MedicalBaseRouterService,
    private productService: ProductService,
    private toastService: ToastService,
    private prescriberService: PrescriberService
  ) {}

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

    // if (this.consultationId && this.patientInfos) {
    this.consultationId$.subscribe({
      next: (consultationId) => {
        if (consultationId) {
          this.prescriptionListRequest = new PrescriptionListRequest({
            consultation_id: consultationId!,
            patient_ref: this.patientInfos!.reference,
            prescriptions: this.prescriptionsRequest,
          });

          console.log(JSON.stringify(this.prescriptionListRequest, null, 2));

          this.prescriberService
            .registerPrescriptions(this.prescriptionListRequest)
            .subscribe({
              next: (data) => {
                console.log(data, "\nHere");

                this.prescriptions$.next(this.prescriptions);

                this.toastService.show({
                  messages: ["La prescription a été enregistrée avec succès."],
                  type: ToastType.Success,
                });
              },
              error: (e) => {
                console.error(e);

                this.toastService.show({
                  messages: ["Désolé, une erreur s'est produite."],
                  delay: 10000,
                  type: ToastType.Error,
                });
              },
            });
        }
      },
    });
    // }

    // If prescriptions has elements, maps prescriptions into prerscriptionRequests
    if (this.prescriptions.length !== 0) {
      this.prescriptionsRequest = this.prescriptions.map(
        (prescription) =>
          new PrescriptionRequest({
            ...prescription,
            forme_id: prescription.forme.id,
            produit_id: prescription.produit.id,
          })
      );
    }
  }

  fetchSelectData() {
    forkJoin({
      products: this.productService.getAll(),

      doses: this.prescriberService.getPrescriptionTexts(
        PrescriptionFields.DOSE
      ),
      adverbs: this.prescriberService.getPrescriptionTexts(
        PrescriptionFields.ADVERB
      ),
      durations: this.prescriberService.getPrescriptionTexts(
        PrescriptionFields.DURATION
      ),
      periods: this.prescriberService.getPrescriptionTexts(
        PrescriptionFields.PERIOD
      ),
    }).subscribe({
      next: (data) => {
        this.productData = data.products;
        this.products = data.products.map((product) => ({
          id: product.id,
          text: product.nom,
        }));

        this.doses = data.doses.map((dose) => ({
          id: 0,
          text: dose,
        }));

        this.adverbs = data.adverbs.map((adverb) => ({
          id: 0,
          text: adverb,
        }));

        this.durations = data.durations.map((duration) => ({
          id: 0,
          text: duration,
        }));

        this.periods = data.periods.map((period) => ({
          id: 0,
          text: period,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    });

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

    this.isPrescriptionFormSubmitted = false;

    this.productNameController.reset();
    this.quantityController.reset();
    this.packagingController.reset();
  }

  getPrescriptionFormData() {
    return new PrescriptionRequest({
      presentation: this.productFormController.value,
      qte: this.quantityController.value,
      conditionnement: this.packagingController.value,

      dose_qte: this.dosageQuantityController.value,
      dose: this.dosageController.value?.text,
      periode: this.periodController.value?.text,
      adverbe: this.adverbController.value?.text,
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

    const prescriptionRequest = this.getPrescriptionFormData();

    console.log(JSON.stringify(prescriptionRequest, null, 2));

    this.prescriptionsRequest.push(prescriptionRequest);

    const prescription = new Prescription({
      id: -1,
      ...prescriptionRequest,
      produit: this.selectedProduct,
      forme: this.selectedProductForm,
    });

    this.prescriptions.push(prescription);

    this.emptyPrescriptionFields();
  }

  registerPrescriptions() {
    if (this.prescriptionsRequest.length === 0) {
      this.toastService.show({
        messages: ["Veuillez valider au moins une prescription."],
        type: ToastType.Warning,
      });

      return;
    }

    this.prescriptionsRegistering.emit(true);
  }

  goToPreviousPage() {
    this.medicalBaseRouter.navigateToMedicinesAndPrescriptions();
  }
}
