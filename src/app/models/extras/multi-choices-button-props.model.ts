export enum ButtonColorClass {
  OUTLINE_DARK = "btn-outline-dark",
  OUTLINE_SUCCESS = "btn-outline-success",
  OUTLINE_WARNING = "btn-outline-warning",
  OUTLINE_DANGER = "btn-outline-danger",
  OUTLINE_PRIMARY = "btn-outline-primary",
  OUTLINE_SECONDARY = "btn-outline-secondary",
  OUTLINE_INFO = "btn-outline-info",

  DARK = "btn-dark",
  SUCCESS = "btn-success",
  WARNING = "btn-warning",
  DANGER = "btn-danger",
  PRIMARY = "btn-primary",
  SECONDARY = "btn-secondary",
  INFO = "btn-info",
}

export interface MutliChoicesButtonProps {
  text: string;
  buttonColorClass: ButtonColorClass;
  hasSaveIcon?: boolean;
}
