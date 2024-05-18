import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { FormControl } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { HospitalisationStore } from "@stores/hospitalisation";
import * as moment from "moment";
import { ToastrService } from "ngx-toastr";
import { Subject, Subscription, pairwise, takeUntil } from "rxjs";
import { hasStateChanges } from "src/app/helpers/utils";

@Component({
  selector: "app-fiche-comptable",
  templateUrl: "./fiche-comptable.component.html",
  styleUrls: ["./fiche-comptable.component.scss"],
})
export class FicheComptableComponent implements OnInit {
  @ViewChild("suiviEdition") suiviEdition!: TemplateRef<any>;

  subscription: Subscription | undefined;
  tabs: any[] = [];
  search = new FormControl();
  qte = new FormControl();
  suiviList: any[] = [];
  hospitalisation: any = null
  currentSuivi = "null"
  contextMenuItems = [
    {
      content: "Ajouter/Modifier",
      events: {
        click: (e: Event) => {
          this.open(this.suiviEdition);
        },
      },
    },
    {
      content: "Supprimer",
      divider: "top",
      events: {
        click: (e: Event) => {
          this.removeSuivi();
        },
      },
    },
  ];

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private modalService: NgbModal,
    private toast: ToastrService,
  ) {}

  ngOnInit(): void {
    this.subscription = this.hospitalisationStore.stateChanged
      .pipe(pairwise())
      .subscribe(([p, c]) => {
          if (this.tabs === null || this.tabs.length === 0) {
          this.tabs = c.tabs;
          if (this.tabs && this.tabs.length > 0)
           this.selectSuivi(this.tabs[0].type)
        }

        if (hasStateChanges(this.suiviList, p.suivis, c.suivis)) {
          this.suiviList = c.suivis;
        }

          this.hospitalisation = c.hospitalisation;
      });


  }

  
  selectSuivi(name: string) {
    // this.hospitalisationStore.changeSuiviCategory(name)
    this.currentSuivi = name
  }

  filterInAll() {
    this.hospitalisationStore.doFilterTabs(this.search.value);
  }

  clearSearch() {
    this.search.setValue("");
    this.hospitalisationStore.clearTabsFilter();
  }

  removeSuivi() {
    this.hospitalisationStore.getValue("selectedElement")?.subscribe({
      next: (v) => {
        console.log(v)
        if (v.id.toString().includes("/")) {
          const arr = v.id.toString().split("/");
          this.hospitalisationStore.removeSuivi(arr[0])
          this.hospitalisationStore.removeSuivi(arr[1])
        } else 
        this.hospitalisationStore.removeSuivi(v.id);
      },
    });
  }

  open(content: any) {
    this.hospitalisationStore.getValue("selectedElement")?.subscribe({
      next: (v) => {
        if(v.typeData === 'evolution' || v.typeData === 'chambres'){
          this.toast.error("Hospitalisation", "Veuillez double-cliquez sur le jour pour Ajouter ou Modifier");
        } else {
          this.qte.setValue(v.qte)
          this.modalService.open(content, {
            size: "sm",
            centered: true,
            keyboard: true,
            backdrop: "static",
          });
        }

      }
    })
  }

  updateSuivi() {
    if (this.qte.value !== "") {
      this.hospitalisationStore.getValue("selectedElement")?.subscribe({
        next: (v) => {
          const suivi = this.suiviList.find((d) => d.id === parseInt(v.id));
          if(suivi !== undefined) {
             suivi.qte = parseInt(this.qte.value);
              suivi.id = parseInt(v.id)
              this.hospitalisationStore.updateSuivi(suivi);
          } else {

            const data = {
              type: v.typeData,
              type_id: parseInt(v.type_id),
              qte: parseInt(this.qte.value),
              apply_date: moment(v.day).format("YYYY-MM-DD[T]HH:mm:ss"),
              hospit_id: this.hospitalisation.id,
              id: Date.now()
            };
            this.hospitalisationStore.commitSuivi(data);
          }

          this.modalService.dismissAll()
        },
      });
    }
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
