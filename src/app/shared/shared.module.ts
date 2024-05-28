import { NgModule } from "@angular/core";
import { CommonModule, TitleCasePipe } from "@angular/common";
import {
  NgbNavModule,
  NgbAccordionModule,
  NgbToastModule,
  NgbTooltipModule,
} from "@ng-bootstrap/ng-bootstrap";

// Swiper Slider
import { SwiperModule } from "ngx-swiper-wrapper";
import { SWIPER_CONFIG } from "ngx-swiper-wrapper";
import { SwiperConfigInterface } from "ngx-swiper-wrapper";

// Counter
import { CountToModule } from "angular-count-to";

import { BreadcrumbsComponent } from "./breadcrumbs/breadcrumbs.component";
import { ScrollspyDirective } from "./scrollspy.directive";
import { InputComponent } from "./form-inputs/input/input.component";
import { HInputComponent } from "./form-inputs/h-input/input.component";
import { SelectComponent } from "./form-inputs/select/select.component";
import { HSelectComponent } from "./form-inputs/h-select/select.component";
import { ReactiveFormsModule } from "@angular/forms";
import { NgxMaskModule } from "ngx-mask";
import { NgSelectModule } from "@ng-select/ng-select";
import { SimpleModalComponent } from "./modals/simple-modal/simple-modal.component";
import { NgxExtendedPdfViewerModule } from "ngx-extended-pdf-viewer";
import { PdfModalComponent } from "./modals/pdf-modal/pdf-modal.component";
import { PdfViewerComponent } from "./pdf-viewer/pdf-viewer.component";
import { ToastsContainerComponent } from "./toasts-container/toasts-container.component";
import { CardComponent } from "./card/card.component";
import { ConfirmModalComponent } from "./modals/confirm-modal/confirm-modal.component";
import { TextComponent } from './form-inputs/text/text.component';
import { ResizableColumnComponent } from "./table-column-resize/resizable-column/resizable-column.component";
import { ColumnResizerDirective } from "./directives/column-resizer.directive";
import { MultiChoicesModalComponent } from "./modals/multi-choices-modal/multi-choices-modal.component";
import { HTextComponent } from "./form-inputs/h-text/text.component";
import { HCheckboxComponent } from './form-inputs/h-checkbox/h-checkbox.component';
import { HRadioComponent } from './form-inputs/h-radio/h-radio.component';

const DEFAULT_SWIPER_CONFIG: SwiperConfigInterface = {
  direction: "horizontal",
  slidesPerView: "auto",
};

@NgModule({
  declarations: [
    BreadcrumbsComponent,
    ScrollspyDirective, // Used ???
    InputComponent,
    HInputComponent,
    HTextComponent,
    SelectComponent,
    HSelectComponent,
    SimpleModalComponent,
    PdfModalComponent,
    PdfViewerComponent,
    ToastsContainerComponent,
    CardComponent,
    ConfirmModalComponent,
    TextComponent,
    ResizableColumnComponent,
    ColumnResizerDirective,
    MultiChoicesModalComponent,
    HCheckboxComponent,
    HRadioComponent,
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
    NgbToastModule,
    NgbTooltipModule,
  ],
  exports: [
    BreadcrumbsComponent,
    ScrollspyDirective, // Used ??
    InputComponent,
    HInputComponent,
    HTextComponent,
    SelectComponent,
    HSelectComponent,
    HRadioComponent,
    HCheckboxComponent,
    ToastsContainerComponent,
    SimpleModalComponent,
    CardComponent,
    TextComponent,
    ConfirmModalComponent,
    ResizableColumnComponent,
    ColumnResizerDirective,
    MultiChoicesModalComponent,
  ],
  providers: [TitleCasePipe],
})
export class SharedModule {}
