import { Injectable } from "@angular/core";
import { ObservableStore } from "@codewithdan/observable-store";
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {Lit, LitResponse} from "../models/hospitalisation/lit";
import {map} from "rxjs/operators";

const hospitalisationEndpoint = "/api/consultations";

@Injectable({ providedIn: "root" })
export class HospitalisationStore extends ObservableStore<any> {
    public consultation : any = new Subject<null>();
    initial_state = {
        consultation : null,
    }

    constructor(private http: HttpClient) {
        super({
            logStateChanges: true,
        });
        this.setState(this.initial_state, "IT_Hospit");
    }

    getConsultation(): Observable<any> {
        return this.consultation.asObservable();
    }

    fetchConsultation(id: number): void {
        const res: Observable<any> = this.http
            .get<LitResponse[]>(hospitalisationEndpoint + "/" + id)

        res.subscribe({
            next: (data: any) => {
                const updatedState = {
                    ...this.initial_state,
                    data,
                };
                this.setState(updatedState, "FETCHED_DATA");
                this.consultation.next(data);
            },
            error: (response) => {
                console.log("Error: " + response);
            },
        });
    }

}
