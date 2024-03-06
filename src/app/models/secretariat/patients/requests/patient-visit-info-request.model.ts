import { AddressRequest } from "./address-request.model";
import { BackgroundsRequest } from "./backgrounds-request.model";
import { ChronicDiseaseRequest } from "./chronic-disease-request.model";
import { CountryRequest } from "./country-request.model";
import { EmployerRequest } from "./employer-request.model";
import { InsuranceRequest } from "./insurance-request.model";
import { ParentRequest } from "./parent-request.model";
import { PatientInsuranceRequest } from "./patient-insurance-request.model";
import { PersonToContactRequest } from "./person-to-contact-request.model";
import { ProfessionRequest } from "./profession-request.model";

export interface IPatientVisitInfoRequest {
  // Visit/Medical base fileds
  maladies?: ChronicDiseaseRequest[];
  parents?: ParentRequest[];
  commentaire?: string;
  antecedant?: BackgroundsRequest;
}
export class PatientVisitInfoRequest {
  // Visit/Medical base fileds
  maladies?: ChronicDiseaseRequest[];
  parents?: ParentRequest[];
  commentaire?: string;
  antecedant?: BackgroundsRequest;

  constructor(iPatientVisitInfoRequest: IPatientVisitInfoRequest) {
    // Visit/Medical base fileds
    this.maladies = iPatientVisitInfoRequest.maladies;
    this.parents = iPatientVisitInfoRequest.parents;
    this.commentaire = iPatientVisitInfoRequest.commentaire;
    this.antecedant = iPatientVisitInfoRequest.antecedant;
  }
}
