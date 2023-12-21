import { Injectable } from "@angular/core";
import { ObservableStore } from '@codewithdan/observable-store';
import { Chambre, ChambreResponse } from '../models/hospitalisation/chambre';
import { Observable, from, of } from "rxjs";
import { map } from "rxjs/operators";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "src/environments/environment";

const apiEndpoint = '/api/chambres';

export interface ChambreStateModel {
    chambres: Chambre[],
  }


@Injectable({providedIn: 'root'})
export class ChambreStore extends ObservableStore<ChambreStateModel> {
  initialState = {
    chambres: [],
  };

  constructor(private http: HttpClient) {
    super({
        logStateChanges: true,
    })
    this.setState(this.initialState, 'INIT_STATE');
  }

  get(): Observable<ChambreStateModel> {
    const state = this.getState();
    return of(state);
  }

  getAll(): void {

    const res : Observable<Chambre[]> = this.http
    .get<ChambreResponse[]>(apiEndpoint)
    .pipe(
      map((chambres) =>
      chambres.map((chambre) => Chambre.fromResponse(chambre))
      )
    );

    res
      .subscribe({
        next: (chambres: Chambre[]) => {
          const updatedState = {
            ...this.initialState,
            chambres,
          }
          this.setState(updatedState, 'FETCHED_DATA');
        },
        error: response => {
          console.log("Erro : " + response)
        }
      });
  }
}