import { Component, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { SelectOption } from "src/app/models/extras/select.model";
import { ToastType } from "src/app/models/extras/toast-type.model";
import { PhoneBook } from "src/app/models/secretariat/informations/phone-book.model";
import { PhoneBookService } from "src/app/services/secretariat/informations/phone-book.service";
import { ToastService } from "src/app/services/secretariat/shared/toast.service";
import { PhoneBookFormModalComponent } from "./phone-book-form-modal/phone-book-form-modal.component";
import { ConfirmModalComponent } from "src/app/shared/modals/confirm-modal/confirm-modal.component";
import { firstValueFrom } from "rxjs";

@Component({
  selector: "app-phone-book-page",
  templateUrl: "./phone-book-page.component.html",
  styleUrls: ["./phone-book-page.component.scss"],
})
export class PhoneBookPageComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  phoneGroupFirstOption = { id: -1, text: "Tous les groupes" };

  searchControl = new FormControl("");
  phoneBooksGroupsControl = new FormControl(this.phoneGroupFirstOption);

  phoneBooksGroups: SelectOption[] = [];

  phoneBooksList: PhoneBook[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.phoneBooksList.length;
  phoneBooksListCut: PhoneBook[] = [];

  constructor(
    private phoneBookService: PhoneBookService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Informations" },
      { label: "Annuaire", active: true },
    ];

    this.refreshPhoneBooksList();

    this.fetchSelectData();

    this.searchControl.valueChanges.subscribe((value) => {
      if (value != null) {
        this.refreshPhoneBooksList();
      }
    });

    this.phoneBooksGroupsControl.valueChanges.subscribe((value) => {
      if (value != null && value != "") {
        this.refreshPhoneBooksList();
      }
    });
  }

  fetchSelectData() {
    this.phoneBookService.getAllPhoneBookGroups().subscribe({
      next: (data) => {
        this.phoneBooksGroups = [
          this.phoneGroupFirstOption,
          ...data.map((phoneBookGroup) => ({
            id: phoneBookGroup.slug,
            text: phoneBookGroup.nom,
          })),
        ];

        // this.setFieldsInitialValues();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  refreshPhoneBooksList() {
    const searchTerm = this.searchControl.value
      ? String(this.searchControl.value)
      : "";

    const group =
      this.phoneBooksGroupsControl.value.id == -1
        ? null
        : this.phoneBooksGroupsControl.value.id;

    console.log(searchTerm + " - " + group);

    this.phoneBookService
      .searchBy({
        q: searchTerm,
        categorie: group,
      })
      .subscribe({
        next: (data) => {
          this.phoneBooksList = data;

          // this.toastService.show({
          //   messages: ["Rafraîchissement de la liste."],
          //   type: ToastType.Success,
          // });

          this.refreshPhoneBooks();
        },
        error: (error) => {
          console.error(error);

          // COMMENTED BECAUSE SENDING WEIRD ERRORS. CHECK LATER
          // this.toastService.show({
          //   messages: [
          //     "Une erreur s'est produite lors du rafraîchissment de la liste.",
          //   ],
          //   delay: 10000,
          //   type: ToastType.Error,
          // });
        },
      });
  }

  refreshPhoneBooks() {
    this.collectionSize = this.phoneBooksList.length;

    this.phoneBooksListCut = this.phoneBooksList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  openPhoneBookFormModal(contact?: PhoneBook) {
    const phoneBookFormModal = this.modalService.open(
      PhoneBookFormModalComponent,
      {
        size: "xl",
        centered: true,
        scrollable: true,
        backdrop: "static",
        keyboard: false,
      }
    );

    if (contact) {
      phoneBookFormModal.componentInstance.phoneBookInfos = contact;
    }

    if (contact) {
      phoneBookFormModal.componentInstance.isContactModified.subscribe(
        (isContactModified: boolean) => {
          console.log("Contact modified : " + isContactModified);

          if (isContactModified) {
            phoneBookFormModal.close();

            this.refreshPhoneBooksList();
          }
        }
      );
    } else {
      phoneBookFormModal.componentInstance.isContactCreated.subscribe(
        (isContactCreated: boolean) => {
          console.log("Contact created : " + isContactCreated);

          if (isContactCreated) {
            phoneBookFormModal.close();

            this.refreshPhoneBooksList();
          }
        }
      );
    }
  }

  async deleteContact(contact: PhoneBook) {
    const contactId = contact.id;

    // OPEN CONFIRMATION MODAL
    const confirmModalRef = this.modalService.open(ConfirmModalComponent, {
      size: "md",
      centered: true,
      keyboard: false,
      backdrop: "static",
      // scrollable: true,
    });

    confirmModalRef.componentInstance.message =
      "Êtes-vous sûr de vouloir supprimer le contact : " +
      (contact.nom ?? "") +
      " " +
      (contact.prenom ?? "") +
      " ?";
    confirmModalRef.componentInstance.subMessage =
      "Cette opération est irréversible.";
    confirmModalRef.componentInstance.isDangerButton = true;

    const isConfirmed = await firstValueFrom(
      confirmModalRef.componentInstance.isConfirmed.asObservable()
    );

    // CLOSE CONFIRMATION MODAL
    confirmModalRef.close();

    // CHECK IF USER CONFIRMED OR NOT
    if (!isConfirmed) {
      return;
    }

    this.phoneBookService.delete(contactId).subscribe({
      next: (data) => {
        this.toastService.show({
          messages: ["Contact supprimé avec succès."],
          type: ToastType.Success,
        });

        this.refreshPhoneBooksList();
      },
      error: (error) => {
        console.error(error);

        this.toastService.show({
          messages: [
            "Une erreur s'est produite lors de la suppression du contact.",
          ],
          delay: 10000,
          type: ToastType.Error,
        });
      },
    });
  }
}
