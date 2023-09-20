import { Injectable, TemplateRef } from "@angular/core";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { ToastInfo } from "src/app/models/extras/toast.model";

@Injectable({
  providedIn: "root",
})
export class ToastService {
  toasts: ToastInfo[] = [];

  constructor() {}

  show(toast: ToastInfo) {
    switch (toast.type) {
      case ToastType.Success:
        toast.classname = "bg-success text-light";
        break;

      case ToastType.Warning:
        toast.classname = "bg-warning text-secondary";
        break;

      case ToastType.Error:
        toast.classname = "bg-danger text-light";
        break;

      default:
        toast.classname = "bg-info text-light";
        break;
    }

    this.toasts.push(toast);
    // console.log(JSON.stringify(this.toasts, null, 2));
  }

  remove(toast: ToastInfo) {
    this.toasts = this.toasts.filter((t) => t !== toast);
  }

  // show(textOrTpl: string | TemplateRef<any>, options: any = {}) {
  //   this.toasts.push({ textOrTpl, ...options });
  // }

  // clear() {
  // 	this.toasts.splice(0, this.toasts.length);
  // }
}
