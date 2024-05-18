import { Component, OnInit } from "@angular/core";
import { LoadingSpinnerService } from "./services/secretariat/shared/loading-spinner.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
})
export class AppComponent {
  // title = "hosman";

  constructor(public loadingSpinnerService: LoadingSpinnerService) {}
}
