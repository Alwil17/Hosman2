import { Injectable } from "@angular/core";
import { ObservableStore } from '@codewithdan/observable-store';
import { Chambre, ChambreResponse } from '../models/hospitalisation/chambre';
import { Observable, from, of, Subject } from "rxjs";
import { map } from "rxjs/operators";
import { HttpClient, HttpHeaders } from "@angular/common/http";

const apiEndpoint = '/api/chambres';

export interface ChambreStateModel {
  chambres: Chambre[],
}

@Injectable({ providedIn: 'root' })
export class ChambreStore extends ObservableStore<ChambreStateModel> {
  private chambresSubject = new Subject<Chambre[]>(); // Ajoutez un Subject

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

  // Exposez le Subject comme Observable pour être écouté depuis le composant
  getChambresObservable(): Observable<Chambre[]> {
    return this.chambresSubject.asObservable();
  }

  getAll(): void {
    const res: Observable<Chambre[]> = this.http
      .get<ChambreResponse[]>(apiEndpoint)
      .pipe(
        map((chambres) =>
          chambres.map((chambre) => Chambre.fromResponse(chambre))
        )
      );

    res.subscribe({
      next: (chambres: Chambre[]) => {
        const updatedState = {
          ...this.initialState,
          chambres,
        };
        this.setState(updatedState, 'FETCHED_DATA');
        this.chambresSubject.next(chambres); // Émettez la nouvelle valeur du tableau de chambres
      },
      error: response => {
        console.log("Error: " + response);
      }
    });
  }
}
