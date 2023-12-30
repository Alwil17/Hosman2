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
  constructor(private spinnerService: LoadingSpinnerService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    setTimeout(() => {
      this.spinnerService.show();
    });
    console.log("LOADING... - " + this.spinnerService.count);

    return next.handle(req).pipe(
      finalize(() => {
        this.spinnerService.hide();
          console.log("FINALIZED - " + this.spinnerService.count);

          this.spinnerService.showLoadingSpinner();
      }),
      // delay(5000),
      // tap({
      //   next: (event) => {
      //     if (event instanceof HttpResponse) {
      //       this.spinnerService.hide();
      //       console.log("LOADED - " + this.spinnerService.count);

      //       this.spinnerService.showLoadingSpinner();
      //     }
      //   },
      //   error: (error) => {
      //     this.spinnerService.hide();
      //     console.log("FAILED - " + this.spinnerService.count);

      //     this.spinnerService.showLoadingSpinner();
      //   },
      //   // complete: () => {
      //   //   this.spinnerService.hide();
      //   //   console.log("COMPLETE - " + this.spinnerService.count);
      //   //     this.spinnerService.showLoadingSpinner();

      //   // }
      // })
    );
  }
}
