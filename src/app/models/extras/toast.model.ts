import { ToastType } from "./toast-type.model";

export interface ToastInfo {
  title?: string;
  messages?: string[];
  delay?: number;
  type: ToastType;
  classname?: string;
}
