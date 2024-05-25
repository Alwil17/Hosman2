import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Addressed } from 'src/app/models/medical-base/submodules/atrd/adressed.model';
import { Transfered } from 'src/app/models/medical-base/submodules/atrd/transfered.model';
import { Refused } from 'src/app/models/medical-base/submodules/atrd/refused.model';
import { Deceded } from 'src/app/models/medical-base/submodules/atrd/deceded.model';
import { environment } from 'src/environments/environment';
import { ObservableStore } from '@codewithdan/observable-store';

const apiEndpoint = environment.medical_base + "consultations";
const addressedEndpoint = environment.medical_base + "addressed";
const transferedEndpoint = environment.medical_base + "transfered";
const refusedEndpoint = environment.medical_base + "refused";
const decededEndpoint = environment.medical_base + "deceded";

@Injectable({
  providedIn: 'root'
})
export class AtrdService extends ObservableStore<any>{
  init_state = {
    consultation_id: null
  };

  constructor(private http: HttpClient) {
    super({
      logStateChanges: true,
      trackStateHistory: true,
    });
    this.setState(this.init_state, "Atrd Init");
  }

  updateStore(data: any, tag: string) {
    const state = this.getState();
    const updatedState = Object.assign(state, data);
    this.setState(updatedState, "ATRD : " + tag);
  }

  getValue(name: string): Observable<any> | null {
    const state = this.getState();
    if (state && state[name]) {
      return of(state[name]);
    } else {
      return null;
    }
  }
  
  fetchDeceded(id: number): Observable<Deceded> {
    return this.http.get<Deceded>(`${apiEndpoint}/${id}/deceded`);
  }
  
  fetchTransfered(id: number): Observable<Transfered> {
    return this.http.get<Transfered>(`${apiEndpoint}/${id}/transfered`);
  }
  
  fetchRefused(id: number): Observable<Refused> {
    return this.http.get<Refused>(`${apiEndpoint}/${id}/refused`);
  }

  fetchAdressed(id: number): Observable<Addressed> {
    return this.http.get<Addressed>(`${apiEndpoint}/${id}/addressed`);
  }

  saveAdressed(data: Addressed): Observable<any> {
    if (!data.id) {
      return this.http.post(addressedEndpoint, data);
    } else {
      return this.http.put(addressedEndpoint + "/" + data.id, data);
    }
  }

  saveRefused(data: Refused): Observable<any> {
    if (!data.id) {
      return this.http.post(refusedEndpoint, data);
    } else {
      return this.http.put(refusedEndpoint + "/" + data.id, data);
    }
  }

  saveTransfered(data: Transfered): Observable<any> {
    if (!data.id) {
      return this.http.post(transferedEndpoint, data);
    } else {
      return this.http.put(transferedEndpoint + "/" + data.id, data);
    }
  }

  saveDeceded(data: Deceded): Observable<any> {
    if (!data.id) {
      return this.http.post(decededEndpoint, data);
    } else {
      return this.http.put(decededEndpoint + "/" + data.id, data);
    }
  }

}
