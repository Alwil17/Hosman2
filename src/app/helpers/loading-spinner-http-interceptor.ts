import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpResponse,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, delay, finalize, tap } from "rxjs";
import { LoadingSpinnerService } from "../services/secretariat/shared/loading-spinner.service";

@Injectable()
export class LoadingSpinnerHttpInterceptor implements HttpInterceptor {
  constructor(private loadingSpinnerService: LoadingSpinnerService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // First check if loading spinner is being forcibly/manually hidden or not.

    // Executed if loading spinner has been forcibly/manually displayed
    if (this.loadingSpinnerService.isLoadingSpinnerDisplayed) {
      // Executed on request start
      setTimeout(() => {
        // Will display global loading spinner on UI
        this.loadingSpinnerService.show();
      });
      console.log(
        "LOADING... - " + this.loadingSpinnerService.pendingRequestsCount
      );

      // Executed on request completion (success, fail or unsubscription from an observable)
      return next.handle(req).pipe(
        finalize(() => {
          // Will hide global loading spinner on UI
          this.loadingSpinnerService.hide();
          console.log(
            "FINALIZED - " + this.loadingSpinnerService.pendingRequestsCount
          );

          // this.loadingSpinnerService.showLoadingSpinner();
        })
      );

      // Executed if loading spinner has been forcibly/manually hidden
    } else {
      // Make loading spinner forcibly visible (for the next request)
      this.loadingSpinnerService.showLoadingSpinner();

      // Executed on request completion (success, fail or unsubscription from an observable)
      return next.handle(req);
    }
  }
}
