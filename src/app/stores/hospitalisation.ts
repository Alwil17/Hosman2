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
const medExterneEndpoint = "/api/med-externes";
const chirurgieEndpoint = "/api/chirurgies";
const adressedEndpoint = "/api/addressed";
const medecinsListEndpoint = "/api/medecins";

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
    suivi: null,
    medecins: null,
    externes: null,
    interventions: null
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

  fetchMedecins(): void {
    const res: Observable<any> = this.http.get<any[]>(medecinsListEndpoint);

    res.subscribe({
      next: (medecins: any) => {
        const r = medecins.map((m : any) => {
          m.fullname = m.nom + ' ' + m.prenoms
          return m
        })
        this.updateStore({medecins : r}, "FETCH MEDECINS")
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchExternes(id: number): void {
    const res: Observable<any> = this.http.get<any[]>(hospitalisationEndpoint  + "/" + id + "/med-externes");

    res.subscribe({
      next: (externes: any) => {
        
        this.updateStore({externes}, "FETCH MEDECINS EXTERNES")
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }  
  
  
  fetchInterventions(id: number): void {
    const res: Observable<any> = this.http.get<any[]>(hospitalisationEndpoint  + "/" + id + "/chirurgies");

    res.subscribe({
      next: (interventions: any) => {
        
        this.updateStore({interventions}, "FETCH CHIRIGIES")
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchAdressed(id: number) {
    this.http.get<any[]>(hospitalisationEndpoint  + "/" + id + "/addressed").subscribe({
      next: (addressed: any) => {
        this.updateStore({addressed}, "FETCH ADRESSED")
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });;

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
      next: (v) => { data.id = v ; console.log(data) ; },
      error: (e) => console.error(e),
    })
  }
  
  updateSuivi(data: any){
    this.http.put(suiviEndpoint + "/" + data.id, data).subscribe({
      next: (v) => { data.id = v ; console.log(data) ; },
      error: (e) => console.error(e),
    })
  }

  removeSuivi(id:any) {
    this.http.delete(suiviEndpoint + "/" + id).subscribe({
      next: (v) => console.log(v),
      error: (e) => console.error(e),
    })
  }

  saveMedExterne(data: any, id? : any): Observable<any> {
    if (id == null) {
      return this.http.post(medExterneEndpoint, data);
    } else {
      return this.http.put(medExterneEndpoint + "/" + id, data);
    }
    
  }
  
  saveIntervention(data: any, id? : any): Observable<any> {
    if (id == null) {
      return this.http.post(chirurgieEndpoint, data);
    } else {
      return this.http.put(chirurgieEndpoint + "/" + id, data);
    }
  }

  saveAdressed(data: any): Observable<any> {
    if (!('id' in data)) {
      return this.http.post(adressedEndpoint, data);
    } else {
      return this.http.put(adressedEndpoint + "/" + data.id, data);
    }
  }
}
