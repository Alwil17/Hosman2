import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { PhoneBookGroup } from "src/app/models/secretariat/informations/phone-book-group.model";
import { PhoneBook } from "src/app/models/secretariat/informations/phone-book.model";
import { PhoneBookRequest } from "src/app/models/secretariat/informations/requests/phone-book-request.model";
import { PhoneBookGroupResponse } from "src/app/models/secretariat/informations/responses/phone-book-group-response.model";
import { PhoneBookResponse } from "src/app/models/secretariat/informations/responses/phone-book-response.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.secretariat + "annuaire";

@Injectable({
  providedIn: "root",
})
export class PhoneBookService {
  constructor(private http: HttpClient) {}

  searchBy(criteria: {
    q?: string;
    categorie?: string;
  }): Observable<PhoneBook[]> {
    let apiComplementary = "";

    if (criteria.q && criteria.categorie) {
      apiComplementary += "q=" + criteria.q;
      apiComplementary += "&categorie=" + criteria.categorie;
    } else {
      if (criteria.q) {
        apiComplementary += "q=" + criteria.q;
      }

      if (criteria.categorie) {
        apiComplementary += "categorie=" + criteria.categorie;
      }
    }

    const apiComplete = apiComplementary
      ? `${apiEndpoint}/search?${apiComplementary}`
      : `${apiEndpoint}/search`;

    // console.log(apiComplete);

    return this.http.get<PhoneBookResponse[]>(apiComplete).pipe(
      map((phoneBooks) => {
        // console.log(JSON.stringify(phoneBooks, null, 2));

        const mapped: PhoneBook[] = phoneBooks.map((phoneBook) =>
          PhoneBook.fromResponse(phoneBook)
        );

        return mapped;
      })
    );
  }

  getAll(): Observable<PhoneBook[]> {
    return this.http
      .get<PhoneBookResponse[]>(apiEndpoint)
      .pipe(
        map((phoneBooks) =>
          phoneBooks.map((phoneBook) => PhoneBook.fromResponse(phoneBook))
        )
      );
  }

  getAllPhoneBookGroups(): Observable<PhoneBookGroup[]> {
    return this.http
      .get<PhoneBookGroupResponse[]>(apiEndpoint + "/categories")
      .pipe(
        map((phoneBookGroups) =>
          phoneBookGroups.map((phoneBookGroup) =>
            PhoneBookGroup.fromResponse(phoneBookGroup)
          )
        )
      );
  }

  create(data: PhoneBookRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}`, data);
  }

  delete(id: any): Observable<void> {
    return this.http.delete<void>(`${apiEndpoint}/${id}`);
  }
}
