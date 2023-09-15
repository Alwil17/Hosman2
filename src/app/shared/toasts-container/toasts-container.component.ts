import { Component, OnInit, TemplateRef } from "@angular/core";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-toasts-container",
  template: `
    <ngb-toast
      *ngFor="let toast of toastService.toasts"
      [class]="toast.type"
      [autohide]="true"
      [delay]="toast.delay || 500000"
      (hidden)="toastService.remove(toast)"
    >
      <ng-template ngbToastHeader>
        <img
          src="assets/images/logo-sm.png"
          class="rounded me-2"
          alt="..."
          height="20"
        />
        <span class="fw-semibold me-auto">{{ toast.title }}</span>
        <!-- <small>06 mins ago</small> -->
      </ng-template>

      {{ toast.message }}
    </ngb-toast>

    <!-- <ngb-toast
      *ngFor="let toast of toastService.toasts"
      [class]="toast.classname"
      [autohide]="true"
      [delay]="toast.delay || 5000"
      (hidden)="toastService.remove(toast)"
    >
      <ng-template [ngIf]="isTemplate(toast)" [ngIfElse]="text">
        <ng-template [ngTemplateOutlet]="toast.textOrTpl"></ng-template>
      </ng-template>

      <ng-template #text>{{ toast.textOrTpl }}</ng-template>
    </ngb-toast> -->
  `,
  host: {
    class: "toast-container position-fixed top-0 end-0 p-3",
    style: "z-index: 1200",
  },
  styles: [],
  // host: { "[class.ngb-toasts]": "true" },
})
export class ToastsContainerComponent implements OnInit {
  constructor(public toastService: ToastService) {}

  ngOnInit(): void {}

  isTemplate(toast: { textOrTpl: any }) {
    return toast.textOrTpl instanceof TemplateRef;
  }
}
