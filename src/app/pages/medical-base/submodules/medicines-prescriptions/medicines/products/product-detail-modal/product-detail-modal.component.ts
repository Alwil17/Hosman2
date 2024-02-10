import { Component, Input, OnInit } from "@angular/core";
import { Product } from "src/app/models/medical-base/submodules/medicines-prescriptions/product.model";

@Component({
  selector: "app-product-detail-modal",
  templateUrl: "./product-detail-modal.component.html",
  styleUrls: ["./product-detail-modal.component.scss"],
})
export class ProductDetailModalComponent implements OnInit {
  @Input()
  productInfos!: Product;

  constructor() {}

  ngOnInit(): void {}
}
