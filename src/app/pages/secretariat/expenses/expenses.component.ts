import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Expense } from "src/app/models/secretariat/activities/expense.model";
import { ExpenseRequest } from "src/app/models/secretariat/activities/requests/expense-request.model";
import { PersonRequest } from "src/app/models/secretariat/activities/requests/person-request";
import { ExpenseService } from "src/app/services/secretariat/activities/expense.service";
import { SelectOption } from "src/app/models/extras/select.model";
import { PersonFormComponent } from "./person-form/person-form.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { parseIntOrZero } from "src/app/helpers/parsers";
import { ExpenseRubricRequest } from "src/app/models/secretariat/activities/requests/expense-rubric-request.model";

@Component({
  selector: "app-expenses",
  templateUrl: "./expenses.component.html",
  styleUrls: ["./expenses.component.scss"],
})
export class ExpensesComponent implements OnInit {
  // bread crumb items
  breadCrumbItems!: Array<{}>;

  expenseForm = new FormGroup({});

  isExpenseFormSubmitted = false;

  timeOfExpenseControl = new FormControl("", [Validators.required]);
  receiverControl = new FormControl("", [Validators.required]);
  amountControl = new FormControl("0", [Validators.required]);
  designationControl = new FormControl("", [Validators.required]);
  rubricControl = new FormControl(null, [Validators.required]);
  allowedByControl = new FormControl(null, [Validators.required]);
  checkoutReceiptControl = new FormControl(null, [Validators.required]);

  // To set date max
  today = new Date().toLocaleDateString("fr-ca");

  rubrics: SelectOption[] = [
    // {
    //   id: 1,
    //   text: "Bloc",
    // },
    // {
    //   id: 2,
    //   text: "Comptabilité",
    // },
    // {
    //   id: 3,
    //   text: "Consultation gratuite",
    // },
    // {
    //   id: 4,
    //   text: "Dépenses WAPCO",
    // },
  ];

  allowedBys: SelectOption[] = [
    {
      id: 1,
      text: "Dr. Chef",
    },
    {
      id: 2,
      text: "Dr. Chef Adjoint",
    },
    {
      id: 3,
      text: "Chef Comptable",
    },
  ];

  checkoutReceipts: SelectOption[] = [
    {
      id: 1,
      text: "NON",
    },
    {
      id: 2,
      text: "OUI",
    },
  ];

  expenseList: Expense[] = [];

  // Pagination handling variables
  page = 1;
  pageSize = 10;
  collectionSize = this.expenseList.length;
  expenseListCut: Expense[] = [];

  constructor(
    private expenseService: ExpenseService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [
      { label: "Activités" },
      { label: "Dépenses", active: true },
    ];

    this.expenseForm = new FormGroup({
      timeOfExpenseControl: this.timeOfExpenseControl,
      receiverControl: this.receiverControl,
      amountControl: this.amountControl,
      designationControl: this.designationControl,
      rubricControl: this.rubricControl,
      allowedByControl: this.allowedByControl,
      checkoutReceiptControl: this.checkoutReceiptControl,
    });

    this.expenseService.getExpenseRubrics().subscribe({
      next: (data) => {
        this.rubrics = data.map((rubric) => ({
          id: rubric.id,
          text: rubric.nom,
        }));
      },
      error: (error) => {
        console.error(error);
      },
    });

    this.refreshExpenseList();
    // this.refreshExpenses();
  }

  personToContactData = new PersonRequest({
    nom: "",
    prenoms: "",
    tel1: "",
    adresse: "",
  });

  onPersonClick() {
    const personToContactModal = this.modalService.open(PersonFormComponent, {
      size: "lg",
      centered: true,
      scrollable: true,
      backdrop: "static",
    });
    console.log(this.personToContactData);

    personToContactModal.componentInstance.data = this.personToContactData;

    personToContactModal.componentInstance.formData.subscribe(
      (formData: PersonRequest) => {
        this.personToContactData = new PersonRequest({
          nom: formData.nom,
          prenoms: formData.prenoms,
          tel1: formData.tel1,
          adresse: formData.adresse,
        });

        this.receiverControl.setValue(
          formData.nom +
            ", " +
            formData.prenoms +
            ", " +
            formData.tel1 +
            ", " +
            formData.adresse
        );

        personToContactModal.close();
      }
    );
  }

  refreshExpenseList() {
    this.expenseService.getAll().subscribe({
      next: (data) => {
        this.expenseList = data;

        this.refreshExpenses();
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  refreshExpenses() {
    this.collectionSize = this.expenseList.length;

    this.expenseListCut = this.expenseList
      // .map((item, i) => ({ id: i + 1, ...item }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  registerExpense() {
    this.isExpenseFormSubmitted = true;

    console.log(JSON.stringify(this.expenseForm.value, null, 2));
    const date = new Date();
    const time = (this.timeOfExpenseControl.value as string).split(":");
    date.setHours(parseInt(time[0]));
    date.setMinutes(parseInt(time[1]));
    console.log(date);

    if (this.expenseForm.valid) {
      const expense = new ExpenseRequest({
        date_depense: date,
        beneficiaire: this.personToContactData,
        montant: parseIntOrZero(this.amountControl.value),
        motif: this.designationControl.value,
        rubrique: new ExpenseRubricRequest({
          nom: this.rubricControl.value.text,
        }),
        recu: parseIntOrZero(this.checkoutReceiptControl.value.id) == 1 ? 0 : 1,
      });

      this.expenseService.create(expense).subscribe({
        next: (data) => {
          console.log(data);

          this.refreshExpenseList();
        },
        error: (e) => console.error(e),
      });
    }
  }

  deleteExpense(id: any) {
    this.expenseService.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted expense");

        this.refreshExpenseList();
      },
      error: (e) => console.error(e),
    });
  }
}
