import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Debt } from "src/app/models/secretariat/patients/debt.model";
import { DebtResponse } from "src/app/models/secretariat/patients/responses/debt-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "creances";

@Injectable({
  providedIn: "root",
})
export class DebtService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Debt[]> {
    return this.http
      .get<DebtResponse[]>(apiEndpoint)
      .pipe(map((debts) => debts.map((debt) => Debt.fromResponse(debt))));
  }
}
