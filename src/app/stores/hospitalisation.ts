import { Injectable } from "@angular/core";
import { ObservableStore } from "@codewithdan/observable-store";
import { HttpClient } from "@angular/common/http";
import { Observable, Subject, of } from "rxjs";
import { LitResponse } from "../models/hospitalisation/lit";
import { map } from "rxjs/operators";
import { Sector } from "../models/secretariat/shared/sector.model";
import { Chambre, ChambreResponse } from "../models/hospitalisation/chambre";
import { slugify } from "../helpers/utils";

const consultationEndpoint = "/api/consultations";
const hospitalisationEndpoint = "/api/hospits";
// const getHospitalisationEndpoint = "/api/hospits";
const tabsEndpoint = "/api/produits";
const suiviEndpoint = "/api/suivis";
const medExterneEndpoint = "/api/med-externes";
const chirurgieEndpoint = "/api/chirurgies";
const adressedEndpoint = "/api/addressed";
const scamsEndpoint = "/api/scams";
const transfusedEndpoint = "/api/transfused";
const decededEndpoint = "/api/deceded";
const sortieEndpoint = "/api/sorties";
const medecinsListEndpoint = "/api/medecins";
const patientsListEndpoint = "/api/patients";

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
    full_tabs: null,
    suivi: null,
    medecins: null,
    externes: null,
    interventions: null,
    selectedElement: null,
  };

  constructor(private http: HttpClient) {
    super({
      logStateChanges: true,
      trackStateHistory: true,
    });
    this.setState(this.init_state, "Hospitalisation Init");
  }

  updateStore(data: any, tag: string) {
    const state = this.getState();
    const updatedState = Object.assign(state, data);
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
    const res: Observable<Sector[]> = this.http.get<Sector[]>("/api/secteurs");

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
    const res: Observable<Chambre[]> = this.http
      .get<ChambreResponse[]>("/api/chambres")
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

  fetchTabs(): void {
    let fData: any = [];
    const res: Observable<any> = this.http.get<LitResponse[]>(tabsEndpoint);

    res.subscribe({
      next: (tabs: any) => {
        // this.updateStore({tabs}, "FETCH TABS")
        fData = tabs;

        // adding tarifs
        const tarifs: Observable<any> =
          this.http.get<LitResponse[]>("/api/tarifs");
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

            let sorted_tabs = fData.sort((a: any, b: any) => {
              const nameA = a.name.toLowerCase();
              const nameB = b.name.toLowerCase();

              // Compare the names
              if (nameA < nameB) {
                return -1;
              } else if (nameA > nameB) {
                return 1;
              } else {
                return 0; // Names are equal
              }
            });

            this.updateStore({ tabs: sorted_tabs }, "FETCH TABS");
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
            ['libelle', 'nom_officiel', 'nom'].some((field) =>
              data[field] &&
              data[field].toLowerCase().includes(text.trim().toLowerCase())
            )
          )
        )
        .map((values : any) => ({
          ...values,
          data: values.data.filter((d: any) =>
            ['libelle', 'nom_officiel', 'nom'].some((field) =>
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

  clearTabsFilter() {
    const state = this.getState();
    this.updateStore({ tabs: state.full_tabs }, "FETCH TABS");
  }

  saveHospitalisation(data: any, id: any): Observable<any> {
    if (id == null) {
      return this.http.post(hospitalisationEndpoint + "?repeat=1", data);
    } else {
      return this.http.put(hospitalisationEndpoint + "/" + id, data);
    }
  }

  selectContextMenuElement(payload: any){
    this.updateStore({ selectedElement: payload }, "LIST");
  }

  commitSuivi(data: any) {
    this.http.post(suiviEndpoint, data).subscribe({
      next: (v) => {
        data.id = v;
        const state = this.getState();
        const list = JSON.parse(JSON.stringify(state.suivis))
        list.push(data)
        this.setState({suivis: list}, "HOSPITALISATION : COMMIT SUIVI");
      },
      error: (e) => console.error(e),
    });
  }

  updateSuivi(data: any) {
    this.http.put(suiviEndpoint + "/" + data.id, data).subscribe({
      next: (v) => {
        const state = this.getState();
        const list = state.suivis
        list.find((d: any) => d.id === data.id).qte = data.qte
        this.setState({suivis: list}, "HOSPITALISATION : UPDATE SUIVI");
      },
      error: (e) => console.error(e),
    });
  }

  removeSuivi(id: any) {
    this.http.delete(suiviEndpoint + "/" + id).subscribe({
      next: (v) => {
        const state = this.getState();
        const list = state.suivis
        const remains = list.filter((data: any) => data.id !== parseInt(id))
        this.setState({suivis: remains}, "HOSPITALISATION : REMOVE SUIVI");
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

  sortieEndpoint(data: any): Observable<any> {
    if (!("id" in data)) {
      return this.http.post(sortieEndpoint, data);
    } else {
      return this.http.put(sortieEndpoint + "/" + data.id, data);
    }
  }
}
