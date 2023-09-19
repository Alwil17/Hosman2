import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { delay, map } from "rxjs/operators";
import { CITIES } from "src/app/data/secretariat/cities.data";
import { COUNTRIES } from "src/app/data/secretariat/countries.data";
import { EMPLOYERS } from "src/app/data/secretariat/employers.data";
import { HAS_INSURANCES } from "src/app/data/secretariat/has-insurance.data";
import { INSURANCES } from "src/app/data/secretariat/insurances.data";
import { NEIGHBORHOODS } from "src/app/data/secretariat/neighborhoods.data";
// import { PATIENT_INSURANCES } from "src/app/data/secretariat/patient-insurance.data";
import { PATIENTS } from "src/app/data/secretariat/patients.data";
import { PROFESSIONS } from "src/app/data/secretariat/professions.data";
import { Address } from "src/app/models/secretariat/patients/address.model";
import { HasInsuranceCode } from "src/app/models/secretariat/patients/has-insurance.model";
import { Insurance } from "src/app/models/secretariat/patients/insurance.model";
import { Patient } from "src/app/models/secretariat/patients/patient.model";
import { PersonToContact } from "src/app/models/secretariat/patients/person-to-contact.model";
import { PatientRequest } from "src/app/models/secretariat/patients/requests/patient-request.model";
import { PatientResponse } from "src/app/models/secretariat/patients/responses/patient-response.model";
import { CityService } from "./city.service";
import { CountryService } from "./country.service";
import { EmployerService } from "./employer.service";
import { InsuranceTypeService } from "./insurance-type.service";
import { InsuranceService } from "./insurance.service";
import { NeighborhoodService } from "./neighborhood.service";
import { ProfessionService } from "./profession.service";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "patients";

@Injectable({
  providedIn: "root",
})
export class PatientService {
  private activePatient: Patient = PATIENTS[0];

  allPatients: Patient[] = PATIENTS;

  constructor(
    private http: HttpClient,

    private cityService: CityService,
    private neighborhoodService: NeighborhoodService,
    private insuranceService: InsuranceService,
    private insuranceTypeService: InsuranceTypeService,
    private countryService: CountryService,
    private professionService: ProfessionService,
    private employerService: EmployerService
  ) {}

  registerPatient(patientRequest: PatientRequest): Observable<any> {
    // PATIENT

    // const personToContact = new PersonToContact({
    //   id: Math.floor(Math.random() * 1000),
    //   ...patientRequest.personne_a_prevenir,
    // });

    // const city = this.cityService.cities.find(
    //   (city) => patientRequest.adresse.ville_id == city.id
    // )!;

    // const neighborhood = this.neighborhoodService.neighborhoods.find(
    //   (neighborhood) => patientRequest.adresse.quartier_id == neighborhood.id
    // )!;

    // const address = new Address({
    //   id: Math.floor(Math.random() * 1000),
    //   ville: city!,
    //   quartier: neighborhood!,
    //   ...patientRequest.adresse,
    // });

    // const homeland = this.countryService.countries.find(
    //   (country) => patientRequest.pays_origine_id == country.id
    // )!;

    // //INSURANCE
    // let insurance;
    // if (patientRequest.assurance) {
    //   insurance = this.insuranceService.insurances.find(
    //     (ins) => patientRequest.assurance!.id == ins.id
    //   );
    // }

    // const nationality = this.countryService.countries.find(
    //   (country) => patientRequest.nationalite_id == country.id
    // )!;

    // const profession = this.professionService.professions.find(
    //   (profession) => patientRequest.profession_id == profession.id
    // );

    // const employer = this.employerService.employers.find(
    //   (employer) => patientRequest.employeur_id == employer.id
    // );

    // //PATIENT_INSURANCE
    // // if (insurance && patientInsurance) {
    // //   this.registerPatientInsurance({
    // //     id: -1,
    // //     patient_id: patientRequest.id,
    // //     assurance_id: insurance!.id,
    // //     taux: patientInsurance.taux,
    // //     date_expiration: patientInsurance.date_expiration,
    // //   });
    // // }

    // const patient = new Patient({
    //   ...patientRequest,
    //   id: this.allPatients.length + 1,
    //   reference: "PAT" + (this.allPatients.length + 1),
    //   personne_a_prevenir: personToContact,
    //   adresse: address,
    //   pays_origine: homeland,
    //   assurance: insurance,
    //   nationalite: nationality,
    //   profession: profession,
    //   employeur: employer,
    // }); // Patient.emptyPatient()

    // console.log("Registered patient \n" + JSON.stringify(patient, null, 2));

    // this.allPatients = [...this.allPatients, patient];

    // this.activePatient = patient;

    return this.http.post<any>(apiEndpoint, patientRequest);
  }

  getPatient(patientId: number) {
    return this.allPatients.find((value) => value.id == patientId);
  }

  getActivePatient() {
    return new Patient(this.activePatient);
  }

  setActivePatient(patientId: number): Observable<void> {
    return this.get(patientId).pipe(
      map((patient) => {
        this.activePatient = patient;

        return;
      })
    );

    // subscribe({
    //   next: (data) => {

    //     this.activePatient = data

    //   },
    //   error: (e) => {
    //     console.error(e);
    //   },
    // });

    // this.activePatient = this.allPatients.find(
    //   (patient) => patientId == patient.id
    // )!;
  }

  getActivePatientType() {
    // console.log("active patient type", this.activePatient.type_patient.id);

    return this.activePatient.is_assure;
  }

  getActivePatientRate() {
    // console.log("active patient type", this.activePatient.type_patient.id);

    // const patientInsurance = this.patientInsurances.find(
    //   (value) => value.patient_id == this.activePatient.id
    // );

    return this.activePatient?.taux_assurance ?? 0;
  }

  getAll(): Observable<Patient[]> {
    // return of([...this.allPatients]);

    return this.http.get<PatientResponse[]>(apiEndpoint).pipe(
      map((patients) => {
        const mapped: Patient[] = patients.map((patient) =>
          Patient.fromResponse(patient)
        );

        return mapped;
      })
    );
  }

  get(id: any): Observable<Patient> {
    return this.http
      .get<PatientResponse>(`${apiEndpoint}/${id}`)
      .pipe(map((patient) => Patient.fromResponse(patient)));
  }

  // registerPatientInsurance(patientInsurance: IPatientInsurance) {
  //   patientInsurance.id = Math.floor(Math.random() * (1000000 - 1 + 1) + 1);
  //   this.patientInsurances = [...this.patientInsurances, patientInsurance];

  //   console.log(patientInsurance);
  // }

  // getPatientInsurance(patientId: number, insuranceId: number) {
  //   return this.patientInsurances.find(
  //     (value) =>
  //       value.patient_id == patientId && value.assurance_id == insuranceId
  //   );
  // }

  // getInsurance(patientId: number) {
  //   const patientInsurance = this.patientInsurances.find(
  //     (value) => value.patient_id == patientId
  //   );

  //   const insurance = INSURANCES.find(
  //     (ins) => patientInsurance?.assurance_id == ins.id
  //   );

  //   return insurance;
  // }

  // create(data: any): Observable<any> {
  //   return this.http.post(apiEndpoint, data);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${apiEndpoint}/${id}`, data);
  // }
}
