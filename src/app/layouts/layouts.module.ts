import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";

import {
  NgbDropdownModule,
  NgbNavModule,
  NgbTooltipModule,
} from "@ng-bootstrap/ng-bootstrap";
import { SimplebarAngularModule } from "simplebar-angular";
import { LanguageService } from "../core/services/language.service";
import { TranslateModule } from "@ngx-translate/core";

// Component pages
import { LayoutComponent } from "./layout.component";
import { VerticalComponent } from "./vertical/vertical.component";
import { TopbarComponent } from "./topbar/topbar.component";
import { HospTopbarComponent } from "./hospitalisation/topbar/topbar.component";
import { SidebarComponent } from "./sidebar/sidebar.component";
import { FooterComponent } from "./footer/footer.component";
import { RightsidebarComponent } from "./rightsidebar/rightsidebar.component";
import { HorizontalComponent } from "./horizontal/horizontal.component";
import { HorizontalTopbarComponent } from "./horizontal-topbar/horizontal-topbar.component";
import { TwoColumnComponent } from "./two-column/two-column.component";
import { TwoColumnSidebarComponent } from "./two-column-sidebar/two-column-sidebar.component";
import { HospitalisationComponent } from './hospitalisation/hospitalisation.component';
import { HospSidebarComponent } from './hospitalisation/sidebar/sidebar.component';
import { CrComponent } from './cr/cr.component';

@NgModule({
  declarations: [
    LayoutComponent,
    VerticalComponent,
    TopbarComponent,
    HospTopbarComponent,
    SidebarComponent,
    FooterComponent,
    RightsidebarComponent,
    HorizontalComponent,
    HorizontalTopbarComponent,
    TwoColumnComponent,
    TwoColumnSidebarComponent,
    HospitalisationComponent,
    HospSidebarComponent,
    CrComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    NgbDropdownModule,
    NgbNavModule,
    SimplebarAngularModule,
    TranslateModule,
    NgbTooltipModule,
  ],
  providers: [LanguageService],
})
export class LayoutsModule {}
