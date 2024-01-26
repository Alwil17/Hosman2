import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: "app-tariff-quantity-modal",
  template: `
    <app-simple-modal title="Saisissez la quantité">
      <form [formGroup]="quantityForm">
        <app-input
          label="Quantité"
          [isMandatory]="true"
          [alignRight]="true"
          [control]="quantityControl"
          [isFormSubmitted]="isQuantityFormSubmitted"
          maskFormat="separator"
          size="lg"
          #quantityFieldInput
        >
        </app-input>

        <div class="d-flex justify-content-end mt-2">
          <button type="button" class="btn btn-success" (click)="validate()">
            <!-- ngbAutofocus -->
            Valider
          </button>
        </div>
      </form>
    </app-simple-modal>
  `,
  styles: [],
})
export class TariffQuantityModalComponent implements OnInit, AfterViewInit {
  @ViewChild("quantityFieldInput", { read: ElementRef })
  quantityFieldInput!: ElementRef;

  @Output()
  quantityData = new EventEmitter<number>();

  quantityControl = new FormControl(1, Validators.required);
  quantityForm!: FormGroup;
  isQuantityFormSubmitted = false;

  constructor() {}

  ngOnInit(): void {
    this.quantityForm = new FormGroup({
      quantityControl: this.quantityControl,
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      // this.quantityFieldInput.nativeElement.querySelector("input").focus();
      this.quantityFieldInput.nativeElement.querySelector("input").select();
    });
  }

  // LISTENER TO HANDLE ENTER KEY UP
  @HostListener("window:keydown.enter", ["$event"])
  handleEnterKeyUp(event: KeyboardEvent) {
    event.preventDefault();

    this.validate();
  }

  validate() {
    this.isQuantityFormSubmitted = true;

    if (!this.quantityForm.valid) {
      return;
    }

    this.quantityData.emit(this.quantityControl.value);
  }
}
