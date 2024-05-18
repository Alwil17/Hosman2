import { Component, Input, OnInit } from "@angular/core";

@Component({
  selector: "app-pdf-modal",
  templateUrl: "./pdf-modal.component.html",
  styleUrls: ["./pdf-modal.component.scss"],
})
export class PdfModalComponent implements OnInit {
  @Input()
  title = "";

  @Input()
  pdfSrc!: Blob;

  constructor() {}

  ngOnInit(): void {}
}
