import { Component, Input, OnInit } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "app-simple-modal",
  templateUrl: "./simple-modal.component.html",
  styleUrls: ["./simple-modal.component.scss"],
})
export class SimpleModalComponent implements OnInit {
  @Input()
  title = "";

  @Input()
  closeButtonText = "Annuler et Fermer";

  constructor(public modal: NgbActiveModal) {}

  ngOnInit(): void {}
}
