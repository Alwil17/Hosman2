import { Injectable } from "@angular/core";
import { ObservableStore } from "@codewithdan/observable-store";
import { HttpClient } from "@angular/common/http";
import { Observable, Subject, of } from "rxjs";
import { Lit, LitResponse } from "../models/hospitalisation/lit";
import { map } from "rxjs/operators";
import { Sector } from "../models/secretariat/shared/sector.model";
import { Chambre, ChambreResponse } from "../models/hospitalisation/chambre";

const consultationEndpoint = "/api/consultations";
const hospitalisationEndpoint = "/api/hospits";
// const getHospitalisationEndpoint = "/api/hospits";
const tabsEndpoint = "/api/produits";
const suiviEndpoint = "/api/suivis";

@Injectable({ providedIn: "root" })
export class HospitalisationStore extends ObservableStore<any> {
  init_state = {
    list: null,
    hospitalisation: null,
    hospitalisation_id: null,
    patient: null,
    consultation: null,
    sectors: null,
    chambres: null,
    tabs: null,
    suivi: null
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

  /* API CALLS */
  fetchHospitalisationList(): void {
    const res: Observable<any> = this.http.get<any>(
      hospitalisationEndpoint
    );

    res.subscribe({
      next: (list: any) => {
        this.updateStore({list}, "LIST")
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchHospitalisation(id: number): void {
    const res: Observable<any> = this.http.get<any>(
      hospitalisationEndpoint  + "/" + id
    );

    res.subscribe({
      next: (hospitalisation: any) =>  {

        this.updateStore({hospitalisation}, "FETCH HOSPITALISATION")
        this.updateStore({consultation : hospitalisation['consultation']}, "SET CONSULTATION")
        this.updateStore({patient : hospitalisation["patient"]}, "SET Patient")

        // get suivis
        this.fetchHospitalisationSuivi(id)

        return hospitalisation;
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchHospitalisationSuivi(id: number): void {
    const res: Observable<any> = this.http.get<any>(
      hospitalisationEndpoint  + "/" + id + "/suivis"
    );

    res.subscribe({
      next: (suivis: any) => {

        this.updateStore({suivis}, "FETCH SUIVIS")

        return suivis;
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchConsultation(id: number): void {
    const res: Observable<any> = this.http.get<any>(
      consultationEndpoint + "/" + id
    );

    res.subscribe({
      next: (consultation: any) => {

        this.updateStore({consultation}, "FETCH CONSULTATION")
        this.updateStore({patient : consultation["patient"]}, "SET Patient")

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
        
        this.updateStore({tabs}, "FETCH TABS")
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }


  saveHospitalisation(data: any, id : any): Observable<any> {
    // console.log(id)
    if (id == null) {
      return this.http.post(hospitalisationEndpoint+ "?repeat=1", data);
    } else {
      return this.http.put(hospitalisationEndpoint + "/" + id, data);
    }
    
  }

  commitSuivi(data: any){
    this.http.post(suiviEndpoint, data).subscribe({
      next: (v) => console.log(v),
      error: (e) => console.error(e),
    })
  }
}
