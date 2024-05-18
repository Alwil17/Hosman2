import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";

@Component({
  selector: "app-confirm-modal",
  templateUrl: "./confirm-modal.component.html",
  styleUrls: ["./confirm-modal.component.scss"],
})
export class ConfirmModalComponent implements OnInit {
  @Input()
  title = "Confirmation";

  @Input()
  message = "";

  @Input()
  subMessage = "";

  @Input()
  cancelButtonText = "Annuler";

  @Input()
  confirmButtonText = "Confirmer";

  @Output()
  isConfirmed = new EventEmitter<boolean>();

  isDangerButton = false;

  constructor() {}

  ngOnInit(): void {}

  confirm(isConfirmed: boolean) {
    if (isConfirmed) {
      this.isConfirmed.emit(true);
    } else {
      this.isConfirmed.emit(false);
    }
  }
}
