import { ToastType } from "./toast-type.model";

export interface ToastInfo {
  title: string;
  message: string;
  delay?: number;
  type: ToastType;
}
