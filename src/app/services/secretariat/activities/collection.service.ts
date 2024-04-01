import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CollectionRequest } from "src/app/models/secretariat/activities/requests/collection-request.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "encaissements";

@Injectable({
  providedIn: "root",
})
export class CollectionService {
  constructor(private http: HttpClient) {}

  create(data: CollectionRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }
}
