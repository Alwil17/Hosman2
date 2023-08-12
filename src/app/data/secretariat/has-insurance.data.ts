import {
  HasInsuranceCode,
  HasInsurance,
} from "src/app/models/secretariat/patients/has-insurance.model";

const HAS_INSURANCES: HasInsurance[] = [
  {
    id: 1,
    code: HasInsuranceCode.NO_LOCAL,
  },
  {
    id: 2,
    code: HasInsuranceCode.NO_FOREIGNER,
  },
  {
    id: 3,
    code: HasInsuranceCode.YES_LOCAL,
  },
  {
    id: 4,
    code: HasInsuranceCode.YES_FOREIGNER,
  },
];

export { HAS_INSURANCES };
