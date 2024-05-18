import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { PersonRequest } from "src/app/models/secretariat/activities/requests/person-request";

@Component({
  selector: "app-person-form",
  templateUrl: "./person-form.component.html",
  styleUrls: ["./person-form.component.scss"],
})
export class PersonFormComponent implements OnInit {
  @Input()
  data!: PersonRequest;

  @Output()
  formData = new EventEmitter<PersonRequest>();

  personToContactForm = new FormGroup({});

  isPersonToContactFormSubmitted = false;

  // Person to contact controls
  ptcLastnameControl = new FormControl("", [Validators.required]);
  ptcFirstnameControl = new FormControl("", [Validators.required]);
  ptcTelControl = new FormControl("", [Validators.required]);
  ptcAddressControl = new FormControl("", [Validators.required]);

  constructor(public modal: NgbActiveModal) {}

  ngOnInit(): void {
    this.personToContactForm = new FormGroup({
      ptcLastnameControl: this.ptcLastnameControl,
      ptcFirstnameControl: this.ptcFirstnameControl,
      ptcTelControl: this.ptcTelControl,
      ptcAddressControl: this.ptcAddressControl,
    });

    this.ptcLastnameControl.setValue(this.data.nom);
    this.ptcFirstnameControl.setValue(this.data.prenoms);
    this.ptcTelControl.setValue(this.data.tel1);
    this.ptcAddressControl.setValue(this.data.adresse);
  }

  submit() {
    this.isPersonToContactFormSubmitted = true;

    if (this.personToContactForm.valid) {
      this.formData.emit(
        new PersonRequest({
          nom: this.ptcLastnameControl.value,
          prenoms: this.ptcFirstnameControl.value,
          tel1: this.ptcTelControl.value,
          adresse: this.ptcAddressControl.value,
        })
      );
    }
  }
}
