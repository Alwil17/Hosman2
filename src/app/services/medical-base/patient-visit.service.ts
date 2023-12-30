import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { Consultation } from "src/app/models/medical-base/consultation.model";
import { ConsultationRequest } from "src/app/models/medical-base/requests/consultation-request.model";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base + "consultations";

@Injectable({
  providedIn: "root",
})
export class PatientVisitService {
  private _selectedWaitingListItem?: WaitingListItem;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Consultation[]> {
    return this.http.get<Consultation[]>(apiEndpoint).pipe(
      map((consultations) => {
        // consultations.map((consultation) => Consultation.fromResponse(consultation))

        return consultations;
      })
    );
  }

  create(data: ConsultationRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  get(id: any): Observable<Consultation> {
    return this.http.get<Consultation>(`${apiEndpoint}/${id}`);
  }

  getConsultationsByPatientReference(
    patientReference: string
  ): Observable<Consultation[]> {
    console.log(apiEndpoint + "/patient/" + patientReference);
    
    return this.http.get<Consultation[]>(
      apiEndpoint + "/patient/" + patientReference
    );
  }

  startVisit(patientReference: string) {
    return this.http.get(
      environment.medical_base +
        "attentes/" +
        patientReference +
        "/update?start=true"
    );
  }

  cancelVisit(patientReference: string) {
    return this.http.get(
      environment.medical_base +
        "attentes/" +
        patientReference +
        "/update?start=false"
    );
  }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${apiEndpoint}/${id}`, data);
  // }

  selectWaitingListItem(waitingListItem: WaitingListItem) {
    this._selectedWaitingListItem = waitingListItem;
  }

  public get selectedWaitingListItem(): WaitingListItem | undefined {
    return this._selectedWaitingListItem;
  }
}
