import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { CITIES } from "src/app/data/secretariat/cities.data";
import { NEIGHBORHOODS } from "src/app/data/secretariat/neighborhoods.data";
import { AddressRequest } from "src/app/models/secretariat/patients/requests/address-request.model";
import { CityService } from "src/app/services/secretariat/patients/city.service";
import { NeighborhoodService } from "src/app/services/secretariat/patients/neighborhood.service";
import { SelectModel } from "src/app/shared/form-inputs/select/select.model";

@Component({
  selector: "app-patient-address-form",
  templateUrl: "./patient-address-form.component.html",
  styleUrls: ["./patient-address-form.component.scss"],
})
export class PatientAddressFormComponent implements OnInit {
  @Input()
  data!: AddressRequest;

  @Output()
  formData = new EventEmitter<AddressRequest>();

  patientAddressForm = new FormGroup({});

  isPatientAddressFormSubmitted = false;

  // Patient address controls
  paCityControl = new FormControl("", [Validators.required]);
  paNeighborhoodControl = new FormControl("", [Validators.required]);
  paStreetControl = new FormControl("");
  paPOBoxControl = new FormControl("");
  paDistrictControl = new FormControl("");
  paHomeNumberControl = new FormControl("");

  //Form selects data

  cities!: SelectModel[];
  neighborhoods!: SelectModel[];

  constructor(
    public modal: NgbActiveModal,
    private cityService: CityService,
    private neighborhoodService: NeighborhoodService
  ) {}

  ngOnInit(): void {
    this.patientAddressForm = new FormGroup({
      paCityControl: this.paCityControl,
      paNeighborhoodControl: this.paNeighborhoodControl,
      paStreetControl: this.paStreetControl,
      paPOBoxControl: this.paPOBoxControl,
      paDistrictControl: this.paDistrictControl,
      paHomeNumberControl: this.paHomeNumberControl,
    });

    this.paCityControl.setValue(
      this.data.ville_id < 0 ? "" : this.data.ville_id
    );
    this.paNeighborhoodControl.setValue(
      this.data.quartier_id < 0 ? "" : this.data.quartier_id
    );
    this.paStreetControl.setValue(this.data.rue);
    this.paPOBoxControl.setValue(this.data.bp);
    this.paDistrictControl.setValue(this.data.arrondissement);
    this.paHomeNumberControl.setValue(this.data.no_maison);

    this.fetchSelectData();
  }

  fetchSelectData() {
    this.cityService.getAll().subscribe({
      next: (data) => {
        this.cities = data.map((city) => ({
          id: city.id,
          text: city.nom,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    });

    this.neighborhoodService.getAll().subscribe({
      next: (data) => {
        this.neighborhoods = data.map((neighborhood) => ({
          id: neighborhood.id,
          text: neighborhood.nom,
        }));
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  submit() {
    this.isPatientAddressFormSubmitted = true;

    if (this.patientAddressForm.valid) {
      this.formData.emit(
        new AddressRequest(
          this.paCityControl.value,
          this.paNeighborhoodControl.value,
          this.paStreetControl.value,
          this.paPOBoxControl.value,
          this.paDistrictControl.value,
          this.paHomeNumberControl.value
        )
      );
    }
  }
}
