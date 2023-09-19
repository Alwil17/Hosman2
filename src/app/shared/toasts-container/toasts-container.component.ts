import { Component, OnInit, TemplateRef } from "@angular/core";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-toasts-container",
  template: `
    <ngb-toast
      *ngFor="let toast of toastService.toasts"
      [class]="toast.classname"
      [autohide]="true"
      [delay]="toast.delay || 7500"
      (hidden)="toastService.remove(toast)"
    >
      <!-- Header of the toast -->
      <ng-template ngbToastHeader>
        <!-- TITLE. Change depending on the type of the toast -->
        <ng-container *ngIf="toast.type == 1">
          <i
            class="ri-check-double-fill align-middle fs-20 me-2 text-success"
          ></i>
          <span class="fw-semibold me-auto">{{ toast.title ?? "Succès" }}</span>
        </ng-container>

        <ng-container *ngIf="toast.type == 2">
          <i class="ri-alert-fill align-middle fs-20 me-2 text-warning"></i>
          <span class="fw-semibold me-auto">{{
            toast.title ?? "Avertissement"
          }}</span>
        </ng-container>

        <ng-container *ngIf="toast.type == 3">
          <i
            class="ri-error-warning-fill align-middle fs-20 me-2 text-danger"
          ></i>
          <span class="fw-semibold me-auto">{{ toast.title ?? "Erreur" }}</span>
        </ng-container>

        <!-- <span class="fw-semibold me-auto">{{ toast.title }}</span> -->
        <!-- <small>06 mins ago</small> -->
      </ng-template>

      <!-- CONTENT/DESCRIPTION.  -->
      <b>
        {{
          toast.type == 3 && !toast.message
            ? "Désolé, une erreur s'est produite."
            : toast.message
        }}
      </b>
    </ngb-toast>
  `,
  host: {
    class: "toast-container position-fixed top-25 end-0 p-3",
    style: "z-index: 1200",
  },
  styles: [],
  // host: { "[class.ngb-toasts]": "true" },
})
export class ToastsContainerComponent implements OnInit {
  constructor(public toastService: ToastService) {}

  ngOnInit(): void {}

  // isTemplate(toast: { textOrTpl: any }) {
  //   return toast.textOrTpl instanceof TemplateRef;
  // }
}
