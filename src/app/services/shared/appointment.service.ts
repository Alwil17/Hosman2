import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { Appointment } from "src/app/models/medical-base/appointment.model";
import { AppointmentRequest } from "src/app/models/medical-base/requests/appointment-request.model";
import { AppointmentResponse } from "src/app/models/medical-base/responses/appointment-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "rdvs";

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

  delete(id: any): Observable<void> {
    return this.http.delete<void>(`${apiEndpoint}/${id}`);
  }
}
