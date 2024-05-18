import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { Appointment } from "src/app/models/medical-base/appointment.model";
import { DoctorAppointment } from "src/app/models/medical-base/doctor-appointment.model";
import { AppointmentRequest } from "src/app/models/medical-base/requests/appointment-request.model";
import { AppointmentResponse } from "src/app/models/medical-base/responses/appointment-response.model";
import { DoctorAppointmentResponse } from "src/app/models/medical-base/responses/doctor-appointment-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "rdvs";

@Injectable({
  providedIn: "root",
})
export class AppointmentService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Appointment[]> {
    return this.http
      .get<AppointmentResponse[]>(apiEndpoint)
      .pipe(
        map((appointments) =>
          appointments.map((appointment) =>
            Appointment.fromResponse(appointment)
          )
        )
      );
  }

  create(data: AppointmentRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}`, data);
  }

  cancelAppointment(id: any): Observable<any> {
    return this.http.put<any>(`${apiEndpoint}/${id}/cancel`, id);
  }

  getAppointmentObjects(): Observable<string[]> {
    return this.http.get<string[]>(apiEndpoint + "/objets");
  }

  getAppointmentsByDoctorMatricule(
    doctor_matricule: string
  ): Observable<Appointment[]> {
    let apiComplementary = "medecin/" + doctor_matricule;

    return this.http
      .get<AppointmentResponse[]>(`${apiEndpoint}/${apiComplementary}`)
      .pipe(
        map((appointments) => {
          const mapped: Appointment[] = appointments.map((appointment) =>
            Appointment.fromResponse(appointment)
          );

          return mapped;
        })
      );
  }

  getDoctorsAppointmentsByPeriod(criteria: {
    minDate: Date;
    maxDate?: Date;
  }): Observable<DoctorAppointment[]> {
    let apiComplementary =
      "datemin=" + criteria.minDate.toLocaleDateString("fr-ca");

    if (criteria.maxDate) {
      apiComplementary +=
        "&datemax=" + criteria.maxDate.toLocaleDateString("fr-ca");
    }

    return this.http
      .get<DoctorAppointmentResponse>(
        `${apiEndpoint}/medecins?${apiComplementary}`
      )
      .pipe(
        map((doctorsAppointmentsResponse) => {
          const doctorsAppointments: DoctorAppointment[] = [];

          for (const [key, value] of Object.entries(
            doctorsAppointmentsResponse
          )) {
            console.log(`${key}: ${value}`);

            doctorsAppointments.push({
              doctorFullname: key,
              appointments: value,
            });
          }

          return doctorsAppointments;
        })
      );
  }
}
