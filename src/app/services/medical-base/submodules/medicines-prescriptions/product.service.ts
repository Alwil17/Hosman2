import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Product } from "src/app/models/medical-base/submodules/medicines-prescriptions/product.model";
import { ProductRequest } from "src/app/models/medical-base/submodules/medicines-prescriptions/requests/product-request.model";
import { environment } from "src/environments/environment";

const apiEndpoint = environment.medical_base + "produits";

@Injectable({
  providedIn: "root",
})
export class ProductService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(apiEndpoint);
  }

  create(data: ProductRequest): Observable<any> {
    return this.http.post(apiEndpoint, data);
  }

  get(id: any): Observable<Product> {
    return this.http.get<Product>(`${apiEndpoint}/${id}`);
  }

  update(id: any, data: ProductRequest): Observable<any> {
    return this.http.put(`${apiEndpoint}/${id}`, data);
  }

  delete(id: any): Observable<void> {
    return this.http.delete<void>(`${apiEndpoint}/${id}`);
  }

  searchBy(criteria: { criteria: string; q: string }): Observable<Product[]> {
    let apiComplementary = "";

    apiComplementary += "criteria=" + criteria.criteria;
    apiComplementary += "&q=" + criteria.q;

    const apiComplete = `${apiEndpoint}/search?${apiComplementary}`;
    console.log(apiComplete);

    return this.http.get<Product[]>(apiComplete);
  }
}
