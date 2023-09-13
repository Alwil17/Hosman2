import { NgModule } from "@angular/core";
import { CommonModule, TitleCasePipe } from "@angular/common";
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
import { SimpleModalComponent } from "./modals/simple-modal/simple-modal.component";
import { NgxExtendedPdfViewerModule } from "ngx-extended-pdf-viewer";
import { PdfModalComponent } from './modals/pdf-modal/pdf-modal.component';
import { PdfViewerComponent } from './pdf-viewer/pdf-viewer.component';

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
    SimpleModalComponent,
    PdfModalComponent,
    PdfViewerComponent,
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
    NgxExtendedPdfViewerModule,
  ],
  exports: [
    BreadcrumbsComponent,
    ScrollspyDirective, // Used ??
    InputComponent,
    SelectComponent,
  ],
  providers: [TitleCasePipe],
})
export class SharedModule {}
