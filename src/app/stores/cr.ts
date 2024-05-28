import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ObservableStore } from "@codewithdan/observable-store";
import { Observable, of } from "rxjs";
import { Sector } from "../models/secretariat/shared/sector.model";
import { environment } from "src/environments/environment";

const secteursEndpoint = environment.hospitalisation_base + "secteurs";

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
    return this.getElapsedMilliseconds() - lastUpdated <= 1000;
  }
}

const timer = new Timer();
timer.start();

@Injectable({ providedIn: "root" })
export class CrStore extends ObservableStore<any> {
  state = {
    lastUpdated: 0,
    processing: true,
    statuts: [
      { id: 1, text: "Nouveau né" },
      { id: 2, text: "Nourrisson" },
      { id: 3, text: "Enfant" },
      { id: 4, text: "Adulte" },
    ],
    genders: [
      { id: 1, text: "Masculin", short: "M" },
      { id: 2, text: "Féminin", short: "F" },
      { id: 3, text: "Indéterminé", short: "I" },
    ],
    patient: {},
  };

  constructor(private http: HttpClient) {
    super({
      logStateChanges: true,
      trackStateHistory: true,
    });
    this.setState(this.state, "CR Init");
    this.startTrackingProcessing();
  }

  startTrackingProcessing() {
    setInterval(() => {
      const state = this.getState();
      if (state["processing"] === true)
        this.setState(
          Object.assign(state, {
            processing: timer.processingState(state["lastUpdated"]),
          }),
          "PROCESSING"
        );
    }, 1000);
  }

  updateStore(data: any, tag: string) {
    const state = this.getState();
    const updatedState = Object.assign(state, data);
    this.setState(updatedState, "HOSPITALISATION : " + tag);
    this.setState(
      Object.assign(state, {
        lastUpdated: timer.getElapsedMilliseconds(),
      }),
      "LAST UPDATED"
    );
  }

  getValue(name: string): Observable<any> | null {
    const state = this.getState();
    if (state && state[name]) {
      return of(state[name]);
    } else {
      return null;
    }
  }

  fetchSector(): void {
    this.updateStore({ processing: true }, "PROCESSING");

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

  theMixer(controls: any, fields: any, phrase: string) {
    const formula = /{{\s*([^}]+)\s*}}/g;
    const matches = [...phrase.matchAll(formula)].map((match) =>
      match[1].trim()
    );
    const values = matches.map((name) => {
      const field = fields.find((f: any) => f.name === name);

      if (field) {
        let selectedValue = '';

        if (field.type === "select") {
          const option = field.options.find(
            (o: any) => o.id === controls[name]?.value
          );
          if (option !== undefined) {
            selectedValue =
              field.bindlabel !== undefined
                ? option[field.bindlabel]
                : option.text;
          }
        } else {
          selectedValue = controls[name]?.value;
        }

        // step 2 to select best wording
        let wording = selectedValue;
        if (field.conditions) {
          for (const condition of field.conditions) {
            const v = selectedValue;
            if (v && eval(condition.eval)) {
              wording = condition.text.toString().replace("%v%", v);
              break;
            }
          }
        } else {
          wording = selectedValue;
        }

        return wording;
      } else {
        // get patient data
        const arr = name.split(".");
        const state = this.getState();
        if (arr[0] === "patient" && state) {
          switch (arr[1]) {
            case "statut":
              return state["statuts"].find(
                (s: any) => s.id === state[arr[0]][arr[1]]
              )?.text;
            case "gender":
              return state["genders"].find(
                (s: any) => s.short === state[arr[0]][arr[1]]
              )?.text;
            default:
              return state[arr[0]][arr[1]];
          }

          // console.log('66666666666666666666666')
          // console.log(state[arr[0]][arr[1]])
        }
      }
    });

    // replace string in sentence
    let mixed = phrase;
    matches.forEach((match, index) => {
      mixed = mixed.replace("{{ " + match + " }}", values[index] ?? '');
    });

    // console.log(matches)
    // console.log(values)  

    return mixed.includes("undefined") ? "" : mixed;
  }

  // saves
  async saveUserData(patient: any) {
    this.updateStore({ patient }, "SAVE USERDATA");
  }
}
