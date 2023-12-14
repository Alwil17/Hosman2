import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { AuthService } from "src/app/services/auth/auth.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  isLoginFormSubmitted = false;

  usernameControl = new FormControl('PISJO', [Validators.required]);
  passwordControl = new FormControl('PISJO1234', [Validators.required]);

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      usernameControl: this.usernameControl,
      passwordControl: this.passwordControl,
    });
  }

  login() {
    this.isLoginFormSubmitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.authService.login(
      this.usernameControl.value,
      this.passwordControl.value
    );

    if (this.authService.isAuthenticated()) {
      this.router.navigateByUrl("/apps")
    }
  }
}
