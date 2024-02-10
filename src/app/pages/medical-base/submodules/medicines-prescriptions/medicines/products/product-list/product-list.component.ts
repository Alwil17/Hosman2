import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { Product } from "src/app/models/medical-base/submodules/medicines-prescriptions/product.model";
import { ProductService } from "src/app/services/medical-base/submodules/medicines-prescriptions/product.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { ProductFormModalComponent } from "../product-form-modal/product-form-modal.component";

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
})
export class ProductListComponent implements OnInit {
  searchCriteria: SelectOption[] = [
    {
      id: "all",
      text: "Tous",
    },
    {
      id: "nom",
      text: "Nom de produit",
    },
    {
      id: "dci",
      text: "Molécule (DCI)",
    },
    {
      id: "indication",
      text: "Indication",
    },
    {
      id: "classe",
      text: "Classe thérapeutique",
    },
    {
      id: "labo",
      text: "Laboratoire",
    },
  ];

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  searchControl = new FormControl("");

  searchCriterionControl = new FormControl(this.searchCriteria[0]);

  products: Product[] = [];

  constructor(
    private productService: ProductService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.getProductsList();

    this.searchControl.valueChanges.subscribe((value) => {
      if (value != null) {
        this.searchProductsList();
      }
    });

    this.searchCriterionControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.searchProductsList();
      }
    });
  }

  getProductsList() {
    this.productService.getAll().subscribe({
      next: (data) => {
        this.products = data;

        // this.toastService.show({
        //   messages: ["Rafraîchissement de la liste."],
        //   type: ToastType.Success,
        // });
      },
      error: (error) => {
        console.error(error);

        this.toastService.show({
          messages: [
            "Une erreur s'est produite lors de la récupération de la liste.",
          ],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }

  searchProductsList() {
    const searchTerm = this.searchControl.value
      ? String(this.searchControl.value)
      : "";

    const criteria = this.searchCriterionControl.value.id;

    console.log(searchTerm + " - " + criteria);

    this.productService
      .searchBy({
        criteria: criteria,
        q: searchTerm,
      })
      .subscribe({
        next: (data) => {
          this.products = data;

          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
          // });
        },
        error: (error) => {
          console.error(error);

          this.toastService.show({
            messages: [
              "Une erreur s'est produite lors du rafraîchissment de la liste.",
            ],
            delay: 10000,
            type: ToastType.Error,
          });
        },
      });
  }

  openProductFormModal(product?: Product) {
    const productFormModalRef = this.modalService.open(
      ProductFormModalComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        backdrop: "static",
        keyboard: false,
      }
    );

    if (product) {
      productFormModalRef.componentInstance.productInfos = product;

      productFormModalRef.componentInstance.isProductModified.subscribe(
        (isProductModified: boolean) => {
          console.log("Product modified : " + isProductModified);

          if (isProductModified) {
            productFormModalRef.close();
            this.searchProductsList();
          }
        }
      );
    } else {
      productFormModalRef.componentInstance.isProductCreated.subscribe(
        (isProductCreated: boolean) => {
          console.log("Product created : " + isProductCreated);

          if (isProductCreated) {
            productFormModalRef.close();
            this.searchProductsList();
          }
        }
      );
    }
  }

  deleteProduct(id: any) {}
}
