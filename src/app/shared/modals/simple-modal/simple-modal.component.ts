import { Component, ElementRef, Input, OnInit, ViewChild } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "app-simple-modal",
  templateUrl: "./simple-modal.component.html",
  styleUrls: ["./simple-modal.component.scss"],

  // Makes modal scrollable (Scroll doesn't work without it. Don't know why.)
  host: {
    style: "display: flex; flex-direction: column; overflow: hidden;",
  },
})
export class SimpleModalComponent implements OnInit {
  @ViewChild("simpleModal")
  simpleModal!: ElementRef;

  @Input()
  title = "";

  @Input()
  closeButtonText = "Fermer";

  @Input()
  defaultFooter = true;

  @Input()
  isFullscreenControlDisplayed = true;

  isInFullscreen = false;

  initialSizeClass = "";

  constructor(public modal: NgbActiveModal) {}

  ngOnInit(): void {
    const modalDialogClassList =
      document.body.querySelector(".modal-dialog")?.classList;

    if (modalDialogClassList?.contains("modal-xl")) {
      this.initialSizeClass = "modal-xl";
    } else if (modalDialogClassList?.contains("modal-lg")) {
      this.initialSizeClass = "modal-lg";
    } else if (modalDialogClassList?.contains("modal-md")) {
      this.initialSizeClass = "modal-md";
    } else if (modalDialogClassList?.contains("modal-sm")) {
      this.initialSizeClass = "modal-sm";
    }
  }

  toggleFullscreen() {
    // const modalDialog = document.body.querySelector(".modal-dialog");
    const modalDialog = (this.simpleModal.nativeElement as HTMLElement).closest(
      ".modal-dialog"
    );

    if (this.isInFullscreen) {
      modalDialog?.classList.remove("modal-fullscreen");
      modalDialog?.classList.add(this.initialSizeClass);
    } else {
      modalDialog?.classList.remove(this.initialSizeClass);
      modalDialog?.classList.add("modal-fullscreen");
    }
    this.isInFullscreen = !this.isInFullscreen;
  }
}
