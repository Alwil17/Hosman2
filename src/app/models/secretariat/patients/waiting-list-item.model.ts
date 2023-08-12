import { calculateExactAge } from "src/app/helpers/age-calculator";

export class WaitingListItem {
  order: number;
  reference: string;
  lastname: string;
  firstname: string;
  dob: Date;
  //   age: string;
  gender: string;
  acts: string[];
  invoiceTotal: number;
  sector: string;
  doctor: string;
  private dateTimeOfArrival: Date;

  constructor(
    order: number,
    reference: string,
    lastname: string,
    firstname: string,
    dob: Date,
    // age: string,
    gender: string,
    acts: string[],
    invoiceTotal: number,
    sector: string,
    doctor: string,
    dateTimeOfArrival: Date
  ) {
    this.order = order;
    this.reference = reference;
    this.lastname = lastname;
    this.firstname = firstname;
    this.dob = dob;
    // this.age = age;
    this.gender = gender;
    this.acts = acts;
    this.invoiceTotal = invoiceTotal;
    this.sector = sector;
    this.doctor = doctor;
    this.dateTimeOfArrival = dateTimeOfArrival;
  }

  get age() {
    return calculateExactAge(this.dob);
  }

  get dateOfArrival() {
    return this.dateTimeOfArrival;
  }

  get timeOfArrival() {
    return this.dateTimeOfArrival;
  }
}
