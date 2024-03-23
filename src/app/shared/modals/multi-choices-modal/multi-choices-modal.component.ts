import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { MutliChoicesButtonProps } from "src/app/models/extras/multi-choices-button-props.model";

@Component({
  selector: "app-multi-choices-modal",
  template: `
    <app-simple-modal
      [title]="title"
      [defaultFooter]="false"
      [isFullscreenControlDisplayed]="false"
    >
      <ng-container *ngIf="messages.length !== 0">
        <p *ngFor="let message of messages">
          <strong>{{ message }}</strong>
        </p>
      </ng-container>

      <ng-container *ngIf="subMessages.length !== 0">
        <p *ngFor="let subMessage of subMessages">
          {{ subMessage }}
        </p>
      </ng-container>

      <ng-container simpleModalFooter>
        <ng-container *ngFor="let button of buttons; index as i">
          <button
            [class]="
              'btn' +
              ' ' +
              button.buttonColorClass +
              ' ' +
              (button.buttonColorClass === 'btn-outline-warning' ||
              button.buttonColorClass === 'btn-warning'
                ? 'text-dark'
                : '') +
              ' ' +
              (button.hasSaveIcon ? 'btn-label waves-effect waves-light' : '')
            "
            (click)="emitIndex(i)"
          >
            <i
              *ngIf="button.hasSaveIcon"
              class="ri-save-2-fill label-icon align-middle fs-16 me-2"
            ></i>
            {{ button.text }}
          </button>
        </ng-container>
      </ng-container>
    </app-simple-modal>
  `,
  styles: [],
})
export class MultiChoicesModalComponent implements OnInit {
  @Input()
  title = "Choix";

  @Input()
  messages: string[] = [];

  @Input()
  subMessages: string[] = [];

  @Input()
  buttons: MutliChoicesButtonProps[] = [];

  @Output()
  choiceIndex = new EventEmitter<number>();

  constructor() {}

  ngOnInit(): void {}

  emitIndex(index: number) {
    this.choiceIndex.emit(index);
  }
}
