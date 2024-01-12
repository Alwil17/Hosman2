import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Expense } from "src/app/models/secretariat/activities/expense.model";
import { ExpenseRequest } from "src/app/models/secretariat/activities/requests/expense-request.model";
import { ExpenseResponse } from "src/app/models/secretariat/activities/responses/expense-response.model";
import { ExpenseRubricResponse } from "src/app/models/secretariat/activities/responses/expense-rubric-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.baseUrl + "depenses";

@Injectable({
  providedIn: "root",
})
export class ExpenseService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Expense[]> {
    return this.http
      .get<ExpenseResponse[]>(apiEndpoint)
      .pipe(
        map((expenses) =>
          expenses.map((expense) => Expense.fromResponse(expense))
        )
      );
  }

  create(data: ExpenseRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  delete(id: any): Observable<void> {
    return this.http.delete<void>(`${apiEndpoint}/${id}`);
  }

  getExpenseRubrics(): Observable<ExpenseRubricResponse[]> {
    return this.http.get<ExpenseRubricResponse[]>(
      environment.baseUrl + "rubrique-depenses"
    );
  }

  // get(id: any): Observable<Expense> {
  //   return this.http.get(`${baseUrl}/${id}`);
  // }

  // update(id: any, data: any): Observable<any> {
  //   return this.http.put(`${baseUrl}/${id}`, data);
  // }
}
