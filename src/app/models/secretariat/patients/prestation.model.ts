export class Prestation {
  id: number;
  sector?: string;
  consultingDoctor?: string;
  date?: Date;
//   quantity?: number;
  origin?: string;
  doctorType?: string;
  doctor?: string;
  performedBy?: string;

  constructor(
    id: number,
    sector: string,
    consultingDoctor: string,
    date: Date,
    // quantity: number,
    origin: string,
    doctorType: string,
    doctor: string,
    performedBy: string
  ) {
    this.id = id;
    this.sector = sector;
    this.consultingDoctor = consultingDoctor;
    this.date = date;
    // this.quantity = quantity;
    this.origin = origin;
    this.doctorType = doctorType;
    this.doctor = doctor;
    this.performedBy = performedBy;
  }
}
