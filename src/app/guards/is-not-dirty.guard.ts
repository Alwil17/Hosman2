import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  CanDeactivate,
  RouterStateSnapshot,
  UrlTree,
} from "@angular/router";
import { Observable } from "rxjs";

export interface IsNotDirty {
  isNotDirty(): boolean | Promise<boolean> | Observable<boolean>;
}

@Injectable({
  providedIn: "root",
})
export class IsNotDirtyGuard implements CanDeactivate<IsNotDirty> {
  canDeactivate(
    component: IsNotDirty,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return component.isNotDirty();
  }
}
