export enum HasInsuranceCode {
  NO_LOCAL = "Patient non assuré",
  NO_FOREIGNER = "Patient expatrié non assuré",
  YES_LOCAL = "Patient avec une assurance locale",
  YES_FOREIGNER = "Patient avec une assurance étrangère",
}

export interface HasInsurance {
  id: number;
  code: HasInsuranceCode;
}
