import { Injectable, TemplateRef } from "@angular/core";
import { ToastInfo } from "src/app/models/extras/toast.model";

@Injectable({
  providedIn: "root",
})
export class ToastService {
  toasts: ToastInfo[] = [];

  constructor() {}

  show(toast: ToastInfo) {
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
