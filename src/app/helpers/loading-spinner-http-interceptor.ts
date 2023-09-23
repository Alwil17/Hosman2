import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpResponse,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { LoadingSpinnerService } from "../services/secretariat/shared/loading-spinner.service";

@Injectable()
export class LoadingSpinnerHttpInterceptor implements HttpInterceptor {
  constructor(private spinnerService: LoadingSpinnerService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    this.spinnerService.show();
    console.log("LOADING...");
    

    return next.handle(req).pipe(
      tap({
        next: (event) => {
          if (event instanceof HttpResponse) {
            this.spinnerService.hide();
    console.log("LOADED");

          }
        },
        error: (error) => {
          this.spinnerService.hide();
    console.log("FAILED");

        },
      })
    );
  }
}
