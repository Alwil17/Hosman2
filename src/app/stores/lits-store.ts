import { Injectable } from "@angular/core";
import { ObservableStore } from "@codewithdan/observable-store";
import { Lit, LitResponse } from "../models/hospitalisation/lit"; // Assurez-vous d'importer le modèle de Lit
import { Observable, of, Subject } from "rxjs";
import { map } from "rxjs/operators";
import { HttpClient } from "@angular/common/http";

const apiEndpoint = "/api/lits";

export interface LitStateModel {
  lits: Lit[];
}

@Injectable({ providedIn: "root" })
export class LitStore extends ObservableStore<LitStateModel> {
  private litsSubject = new Subject<Lit[]>();

  initialState = {
    lits: [],
  };

  constructor(private http: HttpClient) {
    super({
      logStateChanges: true,
    });
    this.setState(this.initialState, "INIT_STATE");
  }

  get(): Observable<LitStateModel> {
    const state = this.getState();
    return of(state);
  }

  // Exposez le Subject comme Observable pour être écouté depuis le composant
  getLitsObservable(): Observable<Lit[]> {
    return this.litsSubject.asObservable();
  }

  getAll(): void {
    const res: Observable<Lit[]> = this.http
      .get<LitResponse[]>(apiEndpoint)
      .pipe(map((lits) => lits.map((lit) => Lit.fromResponse(lit))));

    res.subscribe({
      next: (lits: Lit[]) => {
        const updatedState = {
          ...this.initialState,
          lits,
        };
        this.setState(updatedState, "FETCHED_DATA");
        this.litsSubject.next(lits);
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }
}
