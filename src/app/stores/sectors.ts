import { Injectable } from "@angular/core";
import { ObservableStore } from "@codewithdan/observable-store";
import { Observable, of, Subject } from "rxjs";
import { map } from "rxjs/operators";
import { HttpClient } from "@angular/common/http";
import { Sector } from "../models/secretariat/shared/sector.model";
import { SectorResponse } from "../models/secretariat/shared/responses/sector-response.model";

const apiEndpoint = "/api/secteurs";

export interface LitStateModel {
  sectors: Sector[];
}

@Injectable({ providedIn: "root" })
export class SectorStore extends ObservableStore<LitStateModel> {
  private sectorsSubject = new Subject<Sector[]>();

  initialState = {
    sectors: [],
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

  getSectorsObservable(): Observable<Sector[]> {
    return this.sectorsSubject.asObservable();
  }

  getAll(): void {
    const res: Observable<Sector[]> = this.http
      .get<Sector[]>(apiEndpoint)

    res.subscribe({
      next: (sectors: Sector[]) => {
        const updatedState = {
          ...this.initialState,
          sectors,
        };
        this.setState(updatedState, "FETCHED_DATA");
        this.sectorsSubject.next(sectors);
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }
}
