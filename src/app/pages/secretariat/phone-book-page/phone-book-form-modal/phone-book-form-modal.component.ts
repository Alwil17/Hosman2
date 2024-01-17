import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  QueryList,
  ViewChild,
  ViewChildren,
} from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { WarningMessages } from "src/app/helpers/messages";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { PhoneBook } from "src/app/models/secretariat/informations/phone-book.model";
import { PhoneBookRequest } from "src/app/models/secretariat/informations/requests/phone-book-request.model";
import { PhoneBookService } from "src/app/services/secretariat/informations/phone-book.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { InputComponent } from "src/app/shared/form-inputs/input/input.component";
import { SelectComponent } from "src/app/shared/form-inputs/select/select.component";

@Component({
  selector: "app-phone-book-form-modal",
  templateUrl: "./phone-book-form-modal.component.html",
  styleUrls: ["./phone-book-form-modal.component.scss"],
})
export class PhoneBookFormModalComponent implements OnInit, AfterViewInit {
  @ViewChildren(InputComponent)
  inputFields!: QueryList<InputComponent>;

  @ViewChildren(SelectComponent)
  selectFields!: QueryList<SelectComponent>;

  @ViewChild("firstField", { read: ElementRef })
  firstField!: ElementRef;

  @Input()
  phoneBookInfos?: PhoneBook;

  @Output()
  isContactCreated = new EventEmitter<boolean>();

  @Output()
  isContactModified = new EventEmitter<boolean>();

  phoneBookGroups: SelectOption[] = [];
  // @Input()
  // showSimpleCreateButtons = false;

  groupControl = new FormControl(null, Validators.required);
  lastnameControl = new FormControl(null);
  firstnameControl = new FormControl(null);
  professionControl = new FormControl(null);
  officeTelControl = new FormControl(null);
  tel1Control = new FormControl(null);
  tel2Control = new FormControl(null);
  homeTelControl = new FormControl(null);
  emailControl = new FormControl(null, Validators.email);
  postControl = new FormControl(null);
  bipControl = new FormControl(null);

  // Form groups
  phoneBookForm!: FormGroup;
  isPhoneBookFormSubmitted = false;

  constructor(
    public modal: NgbActiveModal,
    private toastService: ToastService,
    private phoneBookService: PhoneBookService
  ) {}

  ngOnInit(): void {
    this.phoneBookForm = new FormGroup({
      groupControl: this.groupControl,
      lastnameControl: this.lastnameControl,
      firstnameControl: this.firstnameControl,
      professionControl: this.professionControl,
      officeTelControl: this.officeTelControl,
      tel1Control: this.tel1Control,
      tel2Control: this.tel2Control,
      homeTelControl: this.homeTelControl,
      emailControl: this.emailControl,
      postControl: this.postControl,
      bipControl: this.bipControl,
    });

    this.fetchSelectData();
  }

  ngAfterViewInit(): void {
    this.firstField.nativeElement.querySelector("input").focus();
  }

  fetchSelectData() {
    this.phoneBookService.getAllPhoneBookGroups().subscribe({
      next: (data) => {
        (this.phoneBookGroups = data.map((phoneBookGroup) => ({
          id: phoneBookGroup.slug,
          text: phoneBookGroup.nom,
        }))),
          this.setFieldsInitialValues();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  setFieldsInitialValues() {
    if (this.phoneBookInfos) {
      if (this.phoneBookInfos.categorie) {
        this.groupControl.setValue({
          id: this.phoneBookInfos.categorie.slug,
          text: this.phoneBookInfos.categorie.nom,
        });
      }

      this.lastnameControl.setValue(this.phoneBookInfos.nom);
      this.firstnameControl.setValue(this.phoneBookInfos.prenom);
      this.professionControl.setValue(this.phoneBookInfos.profession);
      this.officeTelControl.setValue(this.phoneBookInfos.bureau);
      this.tel1Control.setValue(this.phoneBookInfos.tel1);
      this.tel2Control.setValue(this.phoneBookInfos.tel2);
      this.homeTelControl.setValue(this.phoneBookInfos.domicile);
      this.emailControl.setValue(this.phoneBookInfos.email);
      this.postControl.setValue(this.phoneBookInfos.no_poste);
      this.bipControl.setValue(this.phoneBookInfos.bip);
    }
  }

  getPhoneBookFormData() {
    return new PhoneBookRequest({
      categorie: this.groupControl.value?.text,
      nom: this.lastnameControl.value,
      prenom: this.firstnameControl.value,
      profession: this.professionControl.value,
      bureau: this.officeTelControl.value,
      tel1: this.tel1Control.value,
      tel2: this.tel2Control.value,
      domicile: this.homeTelControl.value,
      email: this.emailControl.value,
      no_poste: this.postControl.value,
      bip: this.bipControl.value,
    });
  }

  getInvalidFields() {
    // Get all invalid inputs (in the form of a text)
    const invalidInputs: string[] = [];
    this.inputFields.forEach((input) => {
      if (input.control.invalid) {
        invalidInputs.push("- " + input.label);
      }
    });

    // Get all invalid selects (in the form of a text)
    const invalidSelects: string[] = [];
    this.selectFields.forEach((select) => {
      if (select.control.invalid) {
        invalidSelects.push("- " + select.label);
      }
    });

    let notificationMessages: string[] = [];

    // If there are invalid inputs, adds them to "notificationMessages"
    if (invalidInputs.length !== 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_INPUT_FIELDS,
        ...invalidInputs
      );
    }

    // If "invalidInputs" is not empty, adds an empty string
    if (invalidInputs.length !== 0) {
      notificationMessages.push("");
    }

    // If there are invalid selects, add them to "notificationMessages"
    if (invalidSelects.length !== 0) {
      notificationMessages.push(
        WarningMessages.MANDATORY_SELECT_FIELDS,
        ...invalidSelects
      );
    }

    // If "invalidSelects" is not empty, adds an empty string
    if (invalidSelects.length !== 0) {
      notificationMessages.push("");
    }

    if (!this.lastnameControl.value && !this.firstnameControl.value) {
      notificationMessages.push(
        "Veuillez renseigner au moins le nom / raison sociale OU le prénom / dénomination"
      );
    }

    // If no value given to lastname and firstname, adds an empty string
    if (!this.lastnameControl.value && !this.firstnameControl.value) {
      notificationMessages.push("");
    }

    if (
      !this.officeTelControl.value &&
      !this.tel1Control.value &&
      !this.tel2Control.value &&
      !this.homeTelControl.value
    ) {
      notificationMessages.push(
        "Veuillez renseigner au moins un numéro (bureau, cellulaire 1 ou 2, domicile)"
      );
    }

    return notificationMessages;
  }

  createContact() {
    this.isPhoneBookFormSubmitted = true;

    const notificationMessages = this.getInvalidFields();
    if (this.phoneBookForm.invalid || notificationMessages.length !== 0) {
      this.toastService.show({
        messages: notificationMessages,
        type: ToastType.Warning,
      });

      return;
    }

    const phoneBookData = this.getPhoneBookFormData();

    console.log(JSON.stringify(phoneBookData, null, 2));

    this.phoneBookService.create(phoneBookData).subscribe({
      next: async (data) => {
        console.log(data, "\nHere");

        this.toastService.show({
          messages: ["Le contact a été enregistré avec succès."],
          type: ToastType.Success,
        });

        this.isContactCreated.emit(true);
      },
      error: (e) => {
        console.log(e);

        this.toastService.show({
          delay: 10000,
          type: ToastType.Error,
          messages: [
            "Désolé, une erreur s'est produite lors de l'enregistrement du contact",
            "Le contact n'a pas été enregistré.",
          ],
        });

        this.isContactCreated.emit(false);
      },
    });
  }

  modifyContact() {
    this.isPhoneBookFormSubmitted = true;

    if (this.phoneBookForm.invalid) {
      // const notificationMessages = this.getInvalidFields();

      // this.toastService.show({
      //   messages: notificationMessages,
      //   type: ToastType.Warning,
      // });

      return;
    }

    const phoneBookData = this.getPhoneBookFormData();

    console.log(JSON.stringify(phoneBookData, null, 2));

    this.phoneBookService
      .update(this.phoneBookInfos?.id, phoneBookData)
      .subscribe({
        next: (data) => {
          console.log(data, "\nHere");

          this.toastService.show({
            messages: ["Le contact a été modifié avec succès."],
            type: ToastType.Success,
          });

          this.isContactModified.emit(true);
        },
        error: (e) => {
          console.log(e);

          this.toastService.show({
            delay: 10000,
            type: ToastType.Error,
          });

          this.isContactModified.emit(false);
        },
      });
  }
}
