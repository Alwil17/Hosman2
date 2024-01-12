import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { PhoneBook } from "src/app/models/secretariat/informations/phone-book.model";
import { PhoneBookRequest } from "src/app/models/secretariat/informations/requests/phone-book-request.model";
import { PhoneBookService } from "src/app/services/secretariat/informations/phone-book.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";

@Component({
  selector: "app-phone-book-form-modal",
  templateUrl: "./phone-book-form-modal.component.html",
  styleUrls: ["./phone-book-form-modal.component.scss"],
})
export class PhoneBookFormModalComponent implements OnInit, AfterViewInit {
  @ViewChild("firstField", { read: ElementRef })
  firstField!: ElementRef;

  @Input()
  phoneBookInfos?: PhoneBook;

  @Output()
  isContactCreated = new EventEmitter<boolean>();

  @Output()
  isContactModified = new EventEmitter<boolean>();

  phoneBookGroups: SelectOption[] = [
    {
      id: -1,
      text: "Tout",
    },
    {
      id: "etc",
      text: "Etc.",
    },
  ];
  // @Input()
  // showSimpleCreateButtons = false;

  groupControl = new FormControl(this.phoneBookGroups[0]);
  lastnameControl = new FormControl(null);
  firstnameControl = new FormControl(null);
  professionControl = new FormControl(null);
  officeTelControl = new FormControl(null);
  tel1Control = new FormControl(null);
  tel2Control = new FormControl(null);
  homeTelControl = new FormControl(null);
  emailControl = new FormControl(null);
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
    this.setFieldsInitialValue();
  }

  setFieldsInitialValue() {
    if (this.phoneBookInfos) {
      this.groupControl.setValue({
        id: this.phoneBookInfos.categorie.slug,
        text: this.phoneBookInfos.categorie.nom,
      });
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

  createContact() {
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

    this.phoneBookService.create(phoneBookData).subscribe({
      next: async (data) => {
        console.log(data, "\nHere");

        this.toastService.show({
          messages: ["Le patient a été enregistré avec succès."],
          type: ToastType.Success,
        });

        this.isContactCreated.emit(true);
      },
      error: (e) => {
        console.error(e);

        this.toastService.show({
          delay: 10000,
          type: ToastType.Error,
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
            messages: ["Le patient a été modifié avec succès."],
            type: ToastType.Success,
          });

          this.isContactModified.emit(true);
        },
        error: (e) => {
          console.error(e);

          this.toastService.show({
            delay: 10000,
            type: ToastType.Error,
          });

          this.isContactModified.emit(false);
        },
      });
  }
}
