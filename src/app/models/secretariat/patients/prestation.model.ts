import { Doctor } from "../shared/doctor.model";
import { Sector } from "../shared/sector.model";
import { Tariff } from "../shared/tariff.model";
import { Invoice } from "./invoice.model";
import { Patient } from "./patient.model";
import { PrestationResponse } from "./responses/prestation-response.model";

export interface IPrestation {
  id: number;
  patient: Patient;
  provenance?: string;
  demandeur?: Doctor;
  consulteur?: Doctor;
  secteur?: Sector;
  date_prestation: Date;
  tarifs: Tariff[];
  // facture: Invoice;
}
export class Prestation {
  id: number;
  patient: Patient;
  provenance?: string;
  demandeur?: Doctor;
  consulteur?: Doctor;
  secteur?: Sector;
  date_prestation: Date;
  tarifs: Tariff[];
  // facture: Invoice;

  constructor(iPrestation: IPrestation) {
    this.id = iPrestation.id;
    this.patient = iPrestation.patient;
    this.provenance = iPrestation.provenance;
    this.demandeur = iPrestation.demandeur;
    this.consulteur = iPrestation.consulteur;
    this.secteur = iPrestation.secteur;
    this.date_prestation = iPrestation.date_prestation;
    this.tarifs = iPrestation.tarifs;
    // this.facture = iPrestation.facture;
  }

  static fromResponse(prestation: PrestationResponse) {
    return new Prestation({
      id: prestation.id,
      patient: Patient.fromResponse(prestation.patient),
      provenance: prestation.provenance,
      demandeur: prestation.demandeur
        ? Doctor.fromResponse(prestation.demandeur)
        : undefined,
      consulteur: prestation.consulteur
        ? Doctor.fromResponse(prestation.consulteur)
        : undefined,
      secteur: prestation.secteur,
      date_prestation: prestation.date_prestation,
      tarifs: prestation.tarifs.map((tariffResponse) =>
        Tariff.fromResponse(tariffResponse)
      ),
      // facture: Invoice.fromResponse(prestation.facture),
    });
  }
}

// export class Prestation {
//   id: number;
//   sector?: string;
//   consultingDoctor?: string;
//   date?: Date;
// //   quantity?: number;
//   origin?: string;
//   doctorType?: string;
//   doctor?: string;
//   performedBy?: string;

//   constructor(
//     id: number,
//     sector: string,
//     consultingDoctor: string,
//     date: Date,
//     // quantity: number,
//     origin: string,
//     doctorType: string,
//     doctor: string,
//     performedBy: string
//   ) {
//     this.id = id;
//     this.sector = sector;
//     this.consultingDoctor = consultingDoctor;
//     this.date = date;
//     // this.quantity = quantity;
//     this.origin = origin;
//     this.doctorType = doctorType;
//     this.doctor = doctor;
//     this.performedBy = performedBy;
//   }
// }
