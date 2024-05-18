import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule } from "@angular/common";

import { FlatpickrModule } from "angularx-flatpickr";
import { CountToModule } from "angular-count-to";
import { NgApexchartsModule } from "ng-apexcharts";
import { LeafletModule } from "@asymmetrik/ngx-leaflet";
import { NgbDropdownModule } from "@ng-bootstrap/ng-bootstrap";
import { SimplebarAngularModule } from "simplebar-angular";

// Swiper Slider
import { SwiperModule } from "ngx-swiper-wrapper";
import { SWIPER_CONFIG } from "ngx-swiper-wrapper";
import { SwiperConfigInterface } from "ngx-swiper-wrapper";

import { LightboxModule } from "ngx-lightbox";

// Load Icons
import { defineLordIconElement } from "lord-icon-element";
import lottie from "lottie-web";

// Pages Routing
import { PagesRoutingModule } from "./pages-routing.module";
import { SharedModule } from "../shared/shared.module";
import { SecretariatModule } from "./secretariat/secretariat.module";
import { MedicalBaseModule } from './medical-base/medical-base.module';
import { HospitalisationModule } from "./hospitalisation/hospitalisation.module";

import { AppsComponent } from './apps.component';

const DEFAULT_SWIPER_CONFIG: SwiperConfigInterface = {
  direction: "horizontal",
  slidesPerView: "auto",
};

@NgModule({
  declarations: [
    AppsComponent
  ],
  imports: [
    CommonModule,
    // FlatpickrModule.forRoot(),
    // CountToModule,
    // NgApexchartsModule,
    // LeafletModule,
    // NgbDropdownModule,
    // SimplebarAngularModule,
    PagesRoutingModule,
    // SharedModule,
    // SwiperModule,
    // LightboxModule,
    SecretariatModule,
    MedicalBaseModule,
    HospitalisationModule,
  ],
  providers: [
    {
      provide: SWIPER_CONFIG,
      useValue: DEFAULT_SWIPER_CONFIG,
    },
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PagesModule {
  constructor() {
    defineLordIconElement(lottie.loadAnimation);
  }
}
