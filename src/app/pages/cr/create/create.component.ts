import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { MessageService } from "@services/messages/message.service";
import { CrStore } from "@stores/cr";
import { ToastrService } from "ngx-toastr";
import { pairwise } from "rxjs";
import { calculateExactAge } from "src/app/helpers/age-calculator";
import { ErrorMessages, WarningMessages } from "src/app/helpers/messages";
import {
  formatDate,
  getCurrentStateValue,
  hasStateChanges,
  markAllControlsAsTouched,
  validateYupSchema,
} from "src/app/helpers/utils";
import { Sector } from "src/app/models/secretariat/shared/sector.model";
import * as Yup from "yup";

@Component({
  selector: "app-create",
  templateUrl: "./create.component.html",
  styleUrls: ["./create.component.scss"],
})
export class CreateComponent implements OnInit {
  sectors: Sector[] = [];
  statuts =  [];
  today = formatDate(new Date(), "yyyy-MM-dd HH:mm");
  genders = [];

  // FG
  fg: FormGroup = new FormGroup({});
  sector = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  statut = new FormControl(
    1,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  firstname = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  lastname = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  birth_date = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.date().required(ErrorMessages.REQUIRED).typeError(ErrorMessages.INVALID))]
  );
  hospit_date = new FormControl(
    this.today,
    [],
    [validateYupSchema(Yup.date().required(ErrorMessages.REQUIRED))]
  );
  cr_date = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.date().required(ErrorMessages.REQUIRED))]
  );
  gender = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  age = new FormControl(null, []);
  sortie_date = new FormControl(null, []);

  patient : any = null

  /* fields controls */
  accouchement = new FormControl(null, []);

  constructor(
    private store: CrStore,
    private message: MessageService,
    private toast: ToastrService,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    private modalService: NgbModal,
    private routerService: Router
  ) {}

  ngOnInit(): void {
    this.store.stateChanged.pipe(pairwise()).subscribe(([p, c]) => {
      this.genders = getCurrentStateValue(c, "genders");
      this.statuts = getCurrentStateValue(c, "statuts");
      this.sectors = getCurrentStateValue(c, "sectors");
      this.patient = getCurrentStateValue(c, 'patient')
    });

    // watches fields
    this.birth_date.valueChanges.subscribe((n) => { this.age.setValue(calculateExactAge(new Date(n)) ) });
  }

  async savePatientData() {
    if (!this.fg.valid) {
      markAllControlsAsTouched(this.fg);
    } else {
      // const confirm = await this.message.confirmDialog(
      //   WarningMessages.SURE_TO_CONTINUE
      // );
      // if (confirm) {
        const data: any = {
          sector: this.fg.value.sector,
          statut: this.fg.value.statut,
          gender: this.fg.value.gender,
          firstname: this.fg.value.firstname,
          lastname: this.fg.value.lastname,
          birth_date: new Date(this.fg.value.birth_date),
          hospit_date: new Date(this.fg.value.hospit_date),
          sortie_date: new Date(this.fg.value.sortie_date),
          cr_date: new Date(this.fg.value.cr_date),
        };

        await this.store.saveUserData(data)

        this.openCR()
      // }
    }
  }

  openCR() {

  }

  saveCR(){
    console.log(this.accouchement.value)
  }
}
