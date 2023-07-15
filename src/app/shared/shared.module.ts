import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { NgbNavModule, NgbAccordionModule } from "@ng-bootstrap/ng-bootstrap";

// Swiper Slider
import { SwiperModule } from "ngx-swiper-wrapper";
import { SWIPER_CONFIG } from "ngx-swiper-wrapper";
import { SwiperConfigInterface } from "ngx-swiper-wrapper";

// Counter
import { CountToModule } from "angular-count-to";

import { BreadcrumbsComponent } from "./breadcrumbs/breadcrumbs.component";
import { ScrollspyDirective } from "./scrollspy.directive";
import { InputComponent } from "./form-inputs/input/input.component";
import { SelectComponent } from "./form-inputs/select/select.component";
import { ReactiveFormsModule } from "@angular/forms";
import { NgxMaskModule } from "ngx-mask";
import { NgSelectModule } from "@ng-select/ng-select";

const DEFAULT_SWIPER_CONFIG: SwiperConfigInterface = {
  direction: "horizontal",
  slidesPerView: "auto",
};

@NgModule({
  declarations: [
    BreadcrumbsComponent,
    ScrollspyDirective, // Used ???
    InputComponent,
    SelectComponent,
  ],
  imports: [
    CommonModule,
    NgbNavModule, // Used ?
    NgbAccordionModule, // Used ?
    SwiperModule, // used ?
    CountToModule, // Used ?
    ReactiveFormsModule,
    NgxMaskModule,
    NgSelectModule,
  ],
  exports: [
    BreadcrumbsComponent,
    ScrollspyDirective, // Used ??
    InputComponent,
    SelectComponent,
  ],
})
export class SharedModule {}
