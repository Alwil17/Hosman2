import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { CITIES } from "src/app/data/secretariat/cities.data";
import { NEIGHBORHOODS } from "src/app/data/secretariat/neighborhoods.data";
import { AddressRequest } from "src/app/models/secretariat/patients/requests/address-request.model";
import { CityService } from "src/app/services/secretariat/patients/city.service";
import { NeighborhoodService } from "src/app/services/secretariat/patients/neighborhood.service";
import { SelectOption } from "src/app/models/extras/select.model";

@Component({
  selector: "app-patient-address-form",
  templateUrl: "./patient-address-form.component.html",
  styleUrls: ["./patient-address-form.component.scss"],
})
export class PatientAddressFormComponent implements OnInit {
  @Input()
  address!: AddressRequest;

  @Output()
  formData = new EventEmitter<AddressRequest>();

  @Output()
  cityAndNeighborhood = new EventEmitter<{
    city: string;
    neighborhood: string;
  }>();

  patientAddressForm = new FormGroup({});

  isPatientAddressFormSubmitted = false;

  // Patient address controls
  paCityControl = new FormControl(null, [Validators.required]);
  paNeighborhoodControl = new FormControl(null, [Validators.required]);
  paStreetControl = new FormControl("");
  paPOBoxControl = new FormControl("");
  paDistrictControl = new FormControl("");
  paHomeNumberControl = new FormControl("");

  //Form selects data

  cities!: SelectOption[];
  neighborhoods!: SelectOption[];

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

    // this.paCityControl.setValue(
    //   this.address.ville_id < 0 ? null : this.address.ville_id
    // );
    // this.paNeighborhoodControl.setValue(
    //   this.address.quartier_id < 0 ? null : this.address.quartier_id
    // );
    this.paStreetControl.setValue(this.address.rue);
    this.paPOBoxControl.setValue(this.address.bp);
    this.paDistrictControl.setValue(this.address.arrondissement);
    this.paHomeNumberControl.setValue(this.address.no_maison);

    this.fetchSelectData();
  }

  fetchSelectData() {
    this.cityService.getAll().subscribe({
      next: (data) => {
        this.cities = data.map((city) => ({
          id: city.id,
          text: city.nom,
        }));

        const selectedCity =
          this.address.ville_id < 0
            ? null
            : this.cities.find((value) => this.address.ville_id == value.id);
        this.paCityControl.setValue(selectedCity);
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

        const selectedNeighborhood =
          this.address.quartier_id < 0
            ? null
            : this.neighborhoods.find(
                (value) => this.address.quartier_id == value.id
              );
        this.paNeighborhoodControl.setValue(selectedNeighborhood);
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
          this.paCityControl.value.id,
          this.paNeighborhoodControl.value.id,
          this.paStreetControl.value,
          this.paPOBoxControl.value,
          this.paDistrictControl.value,
          this.paHomeNumberControl.value
        )
      );

      this.cityAndNeighborhood.emit({
        city: this.paCityControl.value.text,
        neighborhood: this.paNeighborhoodControl.value.text,
      });
    }
  }
}
