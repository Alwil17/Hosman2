import { Injectable } from "@angular/core";
import { ObservableStore } from "@codewithdan/observable-store";
import { HttpClient } from "@angular/common/http";
import { Observable, firstValueFrom, of } from "rxjs";
import { LitResponse } from "../models/hospitalisation/lit";
import { map } from "rxjs/operators";
import { Sector } from "../models/secretariat/shared/sector.model";
import { Chambre, ChambreResponse } from "../models/hospitalisation/chambre";
import { slugify } from "../helpers/utils";
import { FASTABS } from "./suivis-tabs";
import { environment } from "src/environments/environment";

class Timer {
  private startTime: number;

  constructor() {
    this.startTime = 0;
  }

  start() {
    this.startTime = new Date().getTime();
  }

  getElapsedMilliseconds(): number {
    if (this.startTime === 0) {
      throw new Error("Timer has not been started.");
    }
    return new Date().getTime() - this.startTime;
  }

  processingState(lastUpdated: number) {
    return this.getElapsedMilliseconds() - lastUpdated <= 500
  }
}

const consultationEndpoint = environment.hospitalisation_base + "consultations";
const hospitalisationEndpoint = environment.hospitalisation_base + "hospits";
const tabsEndpoint = environment.hospitalisation_base + "produits";
const tarifsEndpoint = environment.hospitalisation_base + "tarifs";
const secteursEndpoint = environment.hospitalisation_base + "secteurs";
const chambresEndpoint = environment.hospitalisation_base + "chambres";
const chambresEmptyEndpoint = environment.hospitalisation_base + "chambres?vue=UNTAKEN";
const suiviEndpoint = environment.hospitalisation_base + "suivis";
const medExterneEndpoint = environment.hospitalisation_base + "med-externes";
const chirurgieEndpoint = environment.hospitalisation_base + "chirurgies";
const adressedEndpoint = environment.hospitalisation_base + "addressed";
const scamsEndpoint = environment.hospitalisation_base + "scams";
const transfusedEndpoint = environment.hospitalisation_base + "transfused";
const decededEndpoint = environment.hospitalisation_base + "deceded";
const sortieEndpoint = environment.hospitalisation_base + "sorties";
const medecinsListEndpoint = environment.hospitalisation_base + "medecins";
const patientsListEndpoint = environment.hospitalisation_base + "patients";
const freeBedsEndPoint = environment.hospitalisation_base + "lits?vue=UNTAKEN"

const timer = new Timer();
timer.start();

@Injectable({ providedIn: "root" })
export class HospitalisationStore extends ObservableStore<any> {
  init_state = {
    pageName: "",
    list: null,
    hospitalisation: null,
    hospitalisation_id: null,
    patient: null,
    consultation: null,
    sectors: null,
    chambres: null,
    freeBeds: null,
    tabs: null,
    full_tabs: null,
    suivis: null,
    medecins: null,
    externes: null,
    interventions: null,
    selectedElement: null,
    cim11token: null,
    lastUpdated: 0,
    processing: true
  };

  constructor(private http: HttpClient) {
    super({
      logStateChanges: true,
      trackStateHistory: true,
    });
    this.setState(this.init_state, "Hospitalisation Init");
    this.startTrackingProcessing()
  }

  startTrackingProcessing(){
    setInterval(() => {
      const state = this.getState();
      if (state['processing'] === true)
      this.setState(Object.assign(state, {processing : timer.processingState(state['lastUpdated'])}), "PROCESSING")
    }, 1000);
  }

  updateStore(data: any, tag: string) {
    const state = this.getState();
    const updatedState = Object.assign(state, data);
    this.setState(updatedState, "HOSPITALISATION : " + tag);
    this.setState(Object.assign(state, {lastUpdated : timer.getElapsedMilliseconds()}), "LAST UPDATED");
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
    this.updateStore({ processing : true }, "PROCESSING");
    const res: Observable<any> = this.http.get<any>(hospitalisationEndpoint);

    res.subscribe({
      next: (list: any) => {
        this.updateStore({ list }, "LIST");
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchHospitalisation(id: number): void {
    this.updateStore({ processing : true }, "PROCESSING");

    const res: Observable<any> = this.http.get<any>(
      hospitalisationEndpoint + "/" + id
    );

    res.subscribe({
      next: (hospitalisation: any) => {
        this.updateStore({ hospitalisation }, "FETCH HOSPITALISATION");
        this.updateStore(
          { consultation: hospitalisation["consultation"] },
          "SET CONSULTATION"
        );
        this.updateStore(
          { patient: hospitalisation["patient"] },
          "SET Patient"
        );

        // get suivis
        this.fetchHospitalisationSuivi(id);

        return hospitalisation;
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchHospitalisationSuivi(id: number): void {
    this.updateStore({ processing : true }, "PROCESSING");

  
    const res: Observable<any> = this.http.get<any>(
      hospitalisationEndpoint + "/" + id + "/suivis"
    );

    res.subscribe({
      next: (suivis: any) => {
        this.updateStore({ suivis }, "FETCH SUIVIS");

        return suivis;
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchConsultation(id: number): void {
    this.updateStore({ processing : true }, "PROCESSING");

    const res: Observable<any> = this.http.get<any>(
      consultationEndpoint + "/" + id
    );

    res.subscribe({
      next: (consultation: any) => {
        this.updateStore({ consultation }, "FETCH CONSULTATION");
        this.updateStore({ patient: consultation["patient"] }, "SET Patient");

        return consultation;
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchSector(): void {
    this.updateStore({ processing : true }, "PROCESSING");


    const res: Observable<Sector[]> = this.http.get<Sector[]>(secteursEndpoint);

    res.subscribe({
      next: (sectors: Sector[]) => {
        this.updateStore({ sectors }, "FETCH SECTORS");
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchChambres(): void {
    this.updateStore({ processing : true }, "PROCESSING");

    const res: Observable<Chambre[]> = this.http
      .get<ChambreResponse[]>(chambresEndpoint)
      .pipe(
        map((chambres) =>
          chambres.map((chambre) => Chambre.fromResponse(chambre))
        )
      );

    res.subscribe({
      next: (chambres: Chambre[]) => {
        this.updateStore({ chambres }, "FETCH CHAMBRES");
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchFreeLits(): void {
    this.updateStore({ processing : true }, "PROCESSING");

    const res: Observable<any> = this.http.get<any>(freeBedsEndPoint);

    res.subscribe({
      next: (freeBeds: any) => {
        this.updateStore({ freeBeds }, "FETCH FREE BEDS");
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchTabs(): void {
    this.updateStore({ processing : true }, "PROCESSING");

    let fData: any = [];
    const res: Observable<any> = this.http.get<any[]>(tabsEndpoint);

    res.subscribe({
      next: (tabs: any) => {
        // this.updateStore({tabs}, "FETCH TABS")
        //tabs === tarifs
        fData = tabs;

        // adding tarifs
        const tarifs: Observable<any> =
          this.http.get<LitResponse[]>(tarifsEndpoint);
        tarifs.subscribe({
          next: (t: any) => {
            let actes: any = Object.entries(t).map((group: any) => {
              return {
                type: slugify(group[0]),
                name: group[0],
                data: group[1],
              };
            });

            fData = [...actes, ...fData];

            // add evolution tab
            fData.push({
              color: "red",
              name: "Evolution",
              type: "evolution",
              data: [],
            });

            // add suivi tab
            fData.push({
              color: "red",
              name: "Suivis",
              type: "watches",
              data: [],
            });

            let sorted_tabs : any = []

            FASTABS.forEach((el) => {
              const r = fData.find((d:any) => d.type === el)
              if (r !== undefined) sorted_tabs.push(r)
            })

            // rename examens => Imagerie
            const k = sorted_tabs.find((e:any) => e.type === 'examens')
            k.name = "Imagerie"

            this.updateStore(
              {
                tabs: sorted_tabs.filter((t: any) => FASTABS.includes(t.type)),
              },
              "FETCH TABS"
            );
            
            this.updateStore({ full_tabs: sorted_tabs }, "FETCH FULL TABS");
          },
          error: (response) => {
            console.log("Error: " + response);
          },
        });
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchMedecins(): void {
    this.updateStore({ processing : true }, "PROCESSING");

    const res: Observable<any> = this.http.get<any[]>(medecinsListEndpoint);

    res.subscribe({
      next: (medecins: any) => {
        const r = medecins.map((m: any) => {
          m.fullname = m.nom + " " + m.prenoms;
          return m;
        });
        this.updateStore({ medecins: r }, "FETCH MEDECINS");
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchPatients(): void {
    this.updateStore({ processing : true }, "PROCESSING");

    const res: Observable<any> = this.http.get<any[]>(patientsListEndpoint);

    res.subscribe({
      next: (patients: any) => {
        const r = patients.map((m: any) => {
          m.fullname = m.nom + " " + m.prenoms;
          return m;
        });
        this.updateStore({ patients: r }, "FETCH PATIENTS");
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchExternes(id: number): void {
    this.updateStore({ processing : true }, "PROCESSING");

    const res: Observable<any> = this.http.get<any[]>(
      hospitalisationEndpoint + "/" + id + "/med-externes"
    );

    res.subscribe({
      next: (externes: any) => {
        this.updateStore({ externes }, "FETCH MEDECINS EXTERNES");
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchInterventions(id: number): void {
    this.updateStore({ processing : true }, "PROCESSING");

    const res: Observable<any> = this.http.get<any[]>(
      hospitalisationEndpoint + "/" + id + "/chirurgies"
    );

    res.subscribe({
      next: (interventions: any) => {
        this.updateStore({ interventions }, "FETCH CHIRIGIES");
      },
      error: (response) => {
        console.log("Error: " + response);
      },
    });
  }

  fetchAdressed(id: number) {
    this.updateStore({ processing : true }, "PROCESSING");

    this.http
      .get<any[]>(hospitalisationEndpoint + "/" + id + "/addressed")
      .subscribe({
        next: (addressed: any) => {
          this.updateStore({ addressed }, "FETCH ADRESSED");
        },
        error: (response) => {
          console.log("Error: " + response);
        },
      });
  }

  fetchTransfused(id: number) {
    this.updateStore({ processing : true }, "PROCESSING");

    this.http
      .get<any[]>(hospitalisationEndpoint + "/" + id + "/transfused")
      .subscribe({
        next: (transfused: any) => {
          this.updateStore({ transfused }, "FETCH ADRESSED");
        },
        error: (response) => {
          console.log("Error: " + response);
        },
      });
  }

  fetchScam(id: number) {
    this.updateStore({ processing : true }, "PROCESSING");

    this.http
      .get<any[]>(hospitalisationEndpoint + "/" + id + "/scams")
      .subscribe({
        next: (scams: any) => {
          this.updateStore({ scams }, "FETCH ADRESSED");
        },
        error: (response) => {
          console.log("Error: " + response);
        },
      });
  }

  fetchDedeced(id: number) {
    this.updateStore({ processing : true }, "PROCESSING");

    this.http
      .get<any[]>(hospitalisationEndpoint + "/" + id + "/deceded")
      .subscribe({
        next: (deceded: any) => {
          this.updateStore({ deceded }, "FETCH ADRESSED");
        },
        error: (response) => {
          console.log("Error: " + response);
        },
      });
  }

  fetchSortie(id: number) {
    this.updateStore({ processing : true }, "PROCESSING");

    this.http
      .get<any[]>(hospitalisationEndpoint + "/" + id + "/sorties")
      .subscribe({
        next: (sorties: any) => {
          this.updateStore({ sorties }, "FETCH ADRESSED");
        },
        error: (response) => {
          console.log("Error: " + response);
        },
      });
  }

  // FUNCTIONS
  doFilterTabs(text: String) {
    const state = this.getState();
    let tabs = state.full_tabs;

    try {
      {
        let searchResults = tabs
          .filter((object: any | null) => object !== null)
          .filter((object: any) =>
            object.data.some((data: any) =>
              ["libelle", "nom_officiel", "nom"].some(
                (field) =>
                  data[field] &&
                  data[field].toLowerCase().includes(text.trim().toLowerCase())
              )
            )
          )
          .map((values: any) => ({
            ...values,
            data: values.data.filter((d: any) =>
              ["libelle", "nom_officiel", "nom"].some(
                (field) =>
                  d[field] &&
                  d[field].toLowerCase().includes(text.trim().toLowerCase())
              )
            ),
          }));
        console.log(searchResults);
        this.updateStore({ tabs: searchResults }, "FILTER TABS");
      }
    } catch (e) {
      console.log("filtering : " + e);
    }
  }

  changePage(pageName: string){
    this.updateStore(
      { currentPage: pageName },
      "CHANGE PAGE"
    );
  }

  clearTabsFilter() {
    const state = this.getState();
    this.updateStore(
      { tabs: state.full_tabs.filter((t: any) => FASTABS.includes(t.type)) },
      "FETCH TABS"
    );
  }

  clearHospitalisation() {
    const state = this.getState();
    this.updateStore(
      { hospitalisation: null },
      "CLEAR HOSPITALISATION"
    );
    
    this.updateStore(
      { hospitalisation_id: null },
      "CLEAR HOSPITALISATION ID"
    );

    this.updateStore(
      { patient: null },
      "CLEAR PATIENT"
    );

    this.updateStore(
      { consultation: null },
      "CLEAR CONSULTATION"
    );

     this.updateStore(
      { suivis: null },
      "CLEAR SUIVIS"
    );
  }

  saveHospitalisation(data: any, id: any): Observable<any> {
    if (id == null) {
      // return this.http.post(hospitalisationEndpoint + "?repeat=1", data);
      return this.http.post(hospitalisationEndpoint, data);
    } else {
      return this.http.put(hospitalisationEndpoint + "/" + id, data);
    }
  }

  selectContextMenuElement(payload: any) {
    this.updateStore({ selectedElement: payload }, "LIST");
  }

  async commitSuivi(data: any): Promise<boolean> {
    return firstValueFrom(
      new Observable<boolean>((observer) => {
        this.http.post(suiviEndpoint, data).subscribe({
          next: (v) => {
            data.id = v;
            const state = this.getState();
            const list = JSON.parse(JSON.stringify(state.suivis));
            list.push(data);
            this.setState({ suivis: list }, "HOSPITALISATION : COMMIT SUIVI");
            observer.next(true);
            observer.complete();
          },
          error: (e) => {
            console.error(e);
            observer.next(false);
            observer.complete();
          },
        });
      })
    );
  }

  updateSuivi(data: any) {
    this.http.put(suiviEndpoint + "/" + data.id, data).subscribe({
      next: (v) => {
        const state = this.getState();
        const list = state.suivis;
        // list.find((d: any) => d.id === data.id).qte = data.qte
        const i = list.findIndex((d: any) => d.id === data.id);
        list[i] = data;
        this.setState({ suivis: list }, "HOSPITALISATION : UPDATE SUIVI");
      },
      error: (e) => console.error(e),
    });
  }

  removeSuivi(id: any) {
    this.http.delete(suiviEndpoint + "/" + id).subscribe({
      next: (v) => {
        const state = this.getState();
        const list = state.suivis;
        const remains = list.filter((data: any) => data.id !== parseInt(id));
        this.setState({ suivis: remains }, "HOSPITALISATION : REMOVE SUIVI");
      },
      error: (e) => console.error(e),
    });
  }

  saveMedExterne(data: any, id?: any): Observable<any> {
    if (id == null) {
      return this.http.post(medExterneEndpoint, data);
    } else {
      return this.http.put(medExterneEndpoint + "/" + id, data);
    }
  }

  deleteMedExterne(id?: any): Observable<any> {
    return this.http.delete(medExterneEndpoint + "/" + id);
  }

  saveIntervention(data: any, id?: any): Observable<any> {
    if (id == null) {
      return this.http.post(chirurgieEndpoint, data);
    } else {
      return this.http.put(chirurgieEndpoint + "/" + id, data);
    }
  }

  deleteIntervention(id?: any): Observable<any> {
    return this.http.delete(chirurgieEndpoint + "/" + id);
  }

  saveAdressed(data: any): Observable<any> {
    if (!("id" in data)) {
      return this.http.post(adressedEndpoint, data);
    } else {
      return this.http.put(adressedEndpoint + "/" + data.id, data);
    }
  }

  saveTransfused(data: any): Observable<any> {
    if (!("id" in data)) {
      return this.http.post(transfusedEndpoint, data);
    } else {
      return this.http.put(transfusedEndpoint + "/" + data.id, data);
    }
  }

  saveScams(data: any): Observable<any> {
    if (!("id" in data)) {
      return this.http.post(scamsEndpoint, data);
    } else {
      return this.http.put(scamsEndpoint + "/" + data.id, data);
    }
  }

  saveDeceded(data: any): Observable<any> {
    if (!("id" in data)) {
      return this.http.post(decededEndpoint, data);
    } else {
      return this.http.put(decededEndpoint + "/" + data.id, data);
    }
  }

  saveSortie(data: any): Observable<any> {
    if (!("id" in data)) {
      return this.http.post(sortieEndpoint, data);
    } else {
      return this.http.put(sortieEndpoint + "/" + data.id, data);
    }
  }
}
