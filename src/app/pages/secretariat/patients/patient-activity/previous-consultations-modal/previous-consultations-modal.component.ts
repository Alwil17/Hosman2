import { Component, Input, OnInit } from "@angular/core";
import { Consultation } from "src/app/models/medical-base/consultation.model";

@Component({
  selector: "app-previous-consultations-modal",
  template: `
    <app-simple-modal
      title="Dernièeres consultations"
      [isFullscreenControlDisplayed]="false"
    >
      <div class="row">
        <div class="col-12">
          <div class="table-responsive table-card">
            <table
              class="table table-one-liner table-hover table-striped table-bordered align-middle mb-0"
            >
              <thead class="table-light text-muted">
                <tr>
                  <th scope="col">{{ "Date" }}</th>
                  <th scope="col">{{ "Heure" }}</th>
                  <th scope="col">{{ "Nom médécin" }}</th>
                  <th scope="col">{{ "Acte" }}</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  class="user-select-none"
                  *ngFor="let consultation of consultations"
                >
                  <td>
                    {{ consultation.date_consultation | date : "dd/MM/yyyy" }}
                  </td>

                  <td>
                    {{ consultation.date_consultation | date : "HH:mm:ss" }}
                  </td>

                  <td>-</td>

                  <td>
                    <ng-container
                      *ngFor="
                        let act of consultation.actes;
                        index as i;
                        count as c
                      "
                    >
                      <span>
                        {{ act.libelle }}
                      </span>
                      <b *ngIf="i !== c - 1"> * </b>
                    </ng-container>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </app-simple-modal>
  `,
  styles: [],
})
export class PreviousConsultationsModalComponent implements OnInit {
  @Input()
  consultations!: Consultation[];

  constructor() {}

  ngOnInit(): void {}
}
