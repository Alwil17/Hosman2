import { HasInsuranceCode } from "src/app/models/secretariat/patients/has-insurance.model";

const HAS_INSURANCES = [
  {
    code: HasInsuranceCode.NO_LOCAL,
    text: "Patient non assuré",
  },
  {
    code: HasInsuranceCode.NO_FOREIGNER,
    text: "Patient expatrié non assuré",
  },
  {
    code: HasInsuranceCode.YES_LOCAL,
    text: "Patient avec une assurance locale",
  },
  {
    code: HasInsuranceCode.YES_FOREIGNER,
    text: "Patient avec une assurance étrangère",
  },
];

// const HAS_INSURANCES: HasInsurance[] = [
//   {
//     id: 1,
//     code: HasInsuranceCode.NO_LOCAL,
//   },
//   {
//     id: 2,
//     code: HasInsuranceCode.NO_FOREIGNER,
//   },
//   {
//     id: 3,
//     code: HasInsuranceCode.YES_LOCAL,
//   },
//   {
//     id: 4,
//     code: HasInsuranceCode.YES_FOREIGNER,
//   },
// ];

export { HAS_INSURANCES };
