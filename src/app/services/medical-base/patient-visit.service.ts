import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map, tap } from "rxjs";
import { Consultation } from "src/app/models/medical-base/consultation.model";
import { ConsultationRequest } from "src/app/models/medical-base/requests/consultation-request.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { PatientVisitInfoRequest } from "src/app/models/secretariat/patients/requests/patient-visit-info-request.model";
import { WaitingListItem } from "src/app/models/secretariat/patients/waiting-list-item.model";
import { environment } from "src/environments/environment";
import { PatientService } from "../secretariat/patients/patient.service";

const apiEndpoint = environment.medical_base + "consultations";

const apiEndpoint2 = environment.medical_base + "patients";

@Injectable({
  providedIn: "root",
})
export class PatientVisitService {
  private _selectedWaitingListItem?: WaitingListItem;
  // private _selectedPatient?: Patient;

  constructor(
    private http: HttpClient,
    private patientService: PatientService
  ) {
    // if (this._selectedWaitingListItem && this._selectedPatient) {
    //   throw new Error("Only one defined at a time");
    // }
  }

  getAll(): Observable<Consultation[]> {
    return this.http.get<Consultation[]>(apiEndpoint).pipe(
      map((consultations) => {
        const mapped = consultations.map(
          (consultation) => new Consultation(consultation)
        );

        return mapped;
      })
    );
  }

  create(data: ConsultationRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  update(id: any, data: ConsultationRequest): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}`, data);
  }

  get(id: any): Observable<Consultation> {
    return this.http.get<Consultation>(`${apiEndpoint}/${id}`);
  }

  getConsultationsByPeriod(period: any): Observable<Consultation[]> {
    console.log(apiEndpoint + "/search?period=" + period);

    return this.http.get<Consultation[]>(apiEndpoint + "/search?period=" + period).pipe(
      map((consultations) => {
        const mapped = consultations.map(
          (consultation) => new Consultation(consultation)
        );

        return mapped;
      })
    );
  }

  printConsultationReport(criteria: {
    minDate: Date;
    maxDate: Date;
    sector?: string;
    doctor?: string;
    vue?: string;
  }): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set("Accept", "application/pdf");

    let apiComplementary =
      "datemin=" + criteria.minDate.toLocaleDateString("fr-ca");

    if (criteria.maxDate) {
      apiComplementary +=
        "&datemax=" + criteria.maxDate.toLocaleDateString("fr-ca");
    }
    if (criteria.sector && criteria.sector != "") {
      apiComplementary += "&secteur_code=" + criteria.sector;
    }
    if (criteria.doctor && criteria.doctor != "") {
      apiComplementary += "&docteur_ref=" + criteria.doctor;
    }
    if (criteria.vue && criteria.vue != "") {
      apiComplementary += "&vue=" + criteria.vue;
    }

    return this.http.get(`${apiEndpoint}/report?${apiComplementary}`, {
      headers: headers,
      responseType: "blob",
    });
  }

  printRequetesJaunesReport(criteria: {
    minDate: Date;
    maxDate: Date;
    sector?: string;
    doctor?: string;
    acte?: string;
    motif?: number;
    diagnostic?: string;
    vue: string;
  }): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set("Accept", "application/pdf");

    let apiComplementary =
      "datemin=" + criteria.minDate.toLocaleDateString("fr-ca");

    if (criteria.maxDate) {
      apiComplementary +=
        "&datemax=" + criteria.maxDate.toLocaleDateString("fr-ca");
    }

    if(criteria.vue === "secteur"){
      apiComplementary += "&secteur_code=" + criteria.sector;
    }else if(criteria.vue === "docteur"){
      apiComplementary += "&docteur_ref=" + criteria.doctor;
    }else if(criteria.vue === "multi"){
      if(criteria.acte && criteria.acte != ""){
        apiComplementary += "&acte_code=" + criteria.acte;
      }
      if(criteria.motif && criteria.motif != 0){
        apiComplementary += "&motif_id=" + criteria.motif;
      }
      if(criteria.diagnostic && criteria.diagnostic != ""){
        apiComplementary += "&diagnostic_code=" + criteria.diagnostic;
      }
    }
    apiComplementary += "&vue=" + criteria.vue;
    console.log(apiComplementary);

    return this.http.get(`${apiEndpoint}/report-jaunes?${apiComplementary}`, {
      headers: headers,
      responseType: "blob",
    });
  }

  searchBy(criteria: {
    minDate: Date;
    maxDate: Date;
    sector?: string;
    doctor?: string;
  }): Observable<Consultation[]> {
    let apiComplementary =
      "datemin=" + criteria.minDate.toLocaleDateString("fr-ca");

    if (criteria.maxDate) {
      apiComplementary +=
        "&datemax=" + criteria.maxDate.toLocaleDateString("fr-ca");
    }
    if (criteria.sector && criteria.sector != "") {
      apiComplementary += "&secteur_code=" + criteria.sector;
    }
    if (criteria.doctor && criteria.doctor != "") {
      apiComplementary += "&docteur_ref=" + criteria.doctor;
    }
    
    return this.http.get<Consultation[]>(`${apiEndpoint}/search?${apiComplementary}`)
    .pipe(
      map((consultations) => {
        const mapped = consultations.map(
          (consultation) => new Consultation(consultation)
        );

        return mapped;
      })
    );
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

  // Patient service API ------------------------------------------------------------------------------------------------
  getPatientById(patientId: number): Observable<Patient> {
    return this.patientService.get(patientId);
  }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${apiEndpoint}/${id}`, data);
  // }

  selectWaitingListItem(waitingListItem: WaitingListItem) {
    this._selectedWaitingListItem = waitingListItem;

    // this._selectedPatient = undefined;
  }

  public get selectedWaitingListItem(): WaitingListItem | undefined {
    return this._selectedWaitingListItem;
  }

  selectPatient(patient: Patient) {
    // this._selectedPatient = patient;

    this._selectedWaitingListItem = undefined;
  }

  // public get selectedPatient(): Patient | undefined {
  //   return this._selectedPatient;
  // }

  // refreshSelectedPatient():Observable<any>  {
  //   const patientId = this.selectedWaitingListItem ? this.selectedWaitingListItem.id : this.selectedPatient!.id

  //   this.patientService.get(patientId).pipe(tap((patient) => {
  //     if (this.selectedWaitingListItem) {
  //       this.selectWaitingListItem(patient)
  //     }
  //       }))
  // }

  updateVisitInfo(id: any, data: PatientVisitInfoRequest): Observable<any> {
    return this.http.put(`${apiEndpoint2}/${id}/bm`, data);
  }
}
