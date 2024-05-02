import { HttpClient } from "@angular/common/http";
import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewEncapsulation,
} from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import * as ECT from "@whoicd/icd11ect";
import { HospitalisationStore } from "@stores/hospitalisation";
import { firstValueFrom } from "rxjs";
import { environment } from "src/environments/environment";

@Component({
  selector: "app-cim11",
  templateUrl: "./cim11.component.html",
  styleUrls: ["./cim11.component.scss"],
})
export class Cim11Component implements OnInit {
  @Input() modal: any = null;
  @Output() closeModal: EventEmitter<any> = new EventEmitter();
  constructor(
    public activeModal: NgbActiveModal,
  ) {}

  ngOnInit(): void {

    const mySettings = {
      apiServerUrl: "https://id.who.int"   ,
      language: "fr",
      autoBind: false,
      apiSecured: true,
      wordsAvailable: true,
      chaptersAvailable: true,
      flexisearchAvailable: true,
    };

    const myCallbacks = {
      async getNewTokenFunction() {
        try {
            const response = await fetch(environment.hospitalisation_base + "diagnostics/token");
            if (response.ok) {
                const token = await response.text();
                return token;
            } else {
                throw new Error(`Failed to fetch token. Status: ${response.status}`);
            }
        } catch (e) {
            console.error("Error during the request:", e);
            throw e; 
        }
    },
      selectedEntityFunction: (selectedEntity: any) => {
        this.submit(selectedEntity);
        ECT.Handler.clear("1");
      },
    };

    ECT.Handler.configure(mySettings, myCallbacks);
    ECT.Handler.bind("1");
  }

  submit(data: any) {
    this.closeModal.emit(data);
    this.activeModal.close();
  }

  close() {
    this.activeModal.close();
  }
}
