import { Injectable } from "@angular/core";
import { ObservableStore } from "@codewithdan/observable-store";
import { HttpClient } from "@angular/common/http";
import { Observable, Subject, of } from "rxjs";
import { Lit, LitResponse } from "../models/hospitalisation/lit";
import { map } from "rxjs/operators";
import { Sector } from "../models/secretariat/shared/sector.model";
import { Chambre, ChambreResponse } from "../models/hospitalisation/chambre";

const consultationEndpoint = "/api/consultations";
const hospitalisationEndpoint = "/api/hospits?repeat=1";
const tabsEndpoint = "/api/produits";

@Injectable({ providedIn: "root" })
export class HospitalisationStore extends ObservableStore<any> {
  // public consultation : any = new Subject<null>();
  // public tabs : any = new Subject<null>();

  init_state = {
    hospitalisation: null,
    hospitalistaion_id: null,
    patient: null,
    consultation: null,
    sectors: null,
    chambres: null,
    tabs: null,
  };

  constructor(private http: HttpClient) {
    super({
      logStateChanges: true,
      trackStateHistory: true,
    });
    this.setState(this.init_state, "Hospitalisation Init");
  }

  updateStore(data: any, tag: string) {
    const state = this.getState()
    const updatedState = Object.assign(state, data)
    this.setState(updatedState, "HOSPITALISATION : " + tag);
  }

  getValue(name: string): Observable<any> | null {
    const state = this.getState();
    if (state && state[name]) {
      return of(state[name]);
    } else {
      return null;
    }
  }

  // setConsultation(consultation: any) {
  //     const updatedState = {
  //         ...this.state,
  //         consultation,
  //     };
  //     this.setState(updatedState, "SetConsultation");
  // }

  // setPatient(patient: any) {
  //     const updatedState = {
  //         ...this.state,
  //         patient,
  //     };
  //     this.setState(updatedState, "SetConsultation");
  // }

  // getConsultation(): Observable<any>|null {
  //     const state = this.getState()
  //     if (state && state.consultation) {
  //         return of(state.consultation)
  //     } else {
  //         return null;
  //     }
  // }

  // updatePatient(patient): Observable<any> {

  // }

  /* API CALLS */
  fetchConsultation(id: number): void {
    const res: Observable<any> = this.http.get<any>(
      consultationEndpoint + "/" + id
    );

    res.subscribe({
      next: (consultation: any) => {
        // const state = this.getState()
        // const updatedState = Object.assign(state, consultation)
        // this.setState(updatedState, "FETCH CONSULTATION");

        this.updateStore({consultation}, "FETCH CONSULTATION")
        this.updateStore({patient : consultation["patient"]}, "Set Patient")

        return consultation;
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchSector(): void {
    const res: Observable<Sector[]> = this.http.get<Sector[]>("/api/secteurs");

    res.subscribe({
      next: (sectors: Sector[]) => {
        // const state = this.getState()
        // const updatedState = Object.assign(state, sectors)
        // this.setState(updatedState, "FETCH SECTORS");

        this.updateStore({sectors}, "FETCH SECTORS")
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchChambres(): void {
    const res: Observable<Chambre[]> = this.http
      .get<ChambreResponse[]>("/api/chambres")
      .pipe(
        map((chambres) =>
          chambres.map((chambre) => Chambre.fromResponse(chambre))
        )
      );

    res.subscribe({
      next: (chambres: Chambre[]) => {
        // const state = this.getState()
        // const updatedState = Object.assign(state, chambres)
        // this.setState(updatedState, "FETCH CHAMBRES");

        this.updateStore({chambres}, "FETCH CHAMBRES")
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchTabs(): void {
    const res: Observable<any> = this.http.get<LitResponse[]>(tabsEndpoint);

    res.subscribe({
      next: (tabs: any) => {
        // const state = this.getState()
        // const updatedState = Object.assign(state, tabs)
        // this.setState(updatedState, "FETCH TABS");

        this.updateStore({tabs}, "FETCH TABS")
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  //   getTabs(): Observable<any> {
  //     return this.tabs.asObservable();
  //   }

  post(data: any): Observable<any> {
    return this.http.post(hospitalisationEndpoint, data);
  }
}
