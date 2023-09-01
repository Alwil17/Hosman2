import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { PersonToContactRequest } from "src/app/models/secretariat/patients/requests/person-to-contact-request.model";

@Component({
  selector: "app-person-to-contact-form",
  templateUrl: "./person-to-contact-form.component.html",
  styleUrls: ["./person-to-contact-form.component.scss"],
})
export class PersonToContactFormComponent implements OnInit {
  @Input()
  data!: PersonToContactRequest;

  @Output()
  formData = new EventEmitter<PersonToContactRequest>();

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
    this.ptcTelControl.setValue(this.data.tel);
    this.ptcAddressControl.setValue(this.data.adresse);
  }

  submit() {
    this.isPersonToContactFormSubmitted = true;

    if (this.personToContactForm.valid) {
      this.formData.emit(
        new PersonToContactRequest(
          this.ptcLastnameControl.value,
          this.ptcFirstnameControl.value,
          this.ptcTelControl.value,
          this.ptcAddressControl.value
        )
      );
    }
  }
}
