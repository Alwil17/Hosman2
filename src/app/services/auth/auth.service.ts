import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private _isAuthenticated = false;

  constructor() {}

  login(username: string, password: string) {
    if (username === "PISJO" && password === "PISJO1234") {
      this._isAuthenticated = true;
    } else {
      this._isAuthenticated = false;
    }
  }

  logout() {
    this._isAuthenticated = false;
  }

  isAuthenticated(): boolean {
    return this._isAuthenticated;
  }
}
