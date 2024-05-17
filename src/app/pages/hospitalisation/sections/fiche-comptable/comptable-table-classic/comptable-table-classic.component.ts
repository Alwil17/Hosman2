import {
  Component,
  Input,
  OnInit,
  TemplateRef,
  ViewChild,
} from "@angular/core";
import { Subscription, pairwise } from "rxjs";
import { HospitalisationStore } from "@stores/hospitalisation";
import { FormControl, FormGroup } from "@angular/forms";
import * as moment from "moment";
import * as Yup from "yup";
import { formatDate, hasStateChanges, slugify, validateYupSchema } from "src/app/helpers/utils";
import { ErrorMessages, WarningMessages } from "src/app/helpers/messages";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Chart, ChartOptions, registerables } from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { WATCHES } from "./suivis-list";
import { ToastrService } from "ngx-toastr";
import { ElementRef } from "@angular/core";
import { MessageService } from "@services/messages/message.service";

var timer: any, // timer required to reset
  timeout = 200;

@Component({
  selector: "app-comptable-table-classic",
  templateUrl: "./comptable-table-classic.component.html",
  styleUrls: ["./comptable-table-classic.component.scss"],
})
export class ComptableTableClassicComponent implements OnInit {
  subscription: Subscription | undefined;
  @ViewChild("evolutionEdition") evolutionEdition!: TemplateRef<any>;
  @ViewChild("watchesEdition") watchesEdition!: TemplateRef<any>;
  @ViewChild("watchesValueEdition") watchesValueEdition!: TemplateRef<any>;
  @ViewChild("dismissWatchValueModal") dismissWatchValueModal!: ElementRef;

  modalReference: any;

  @Input() typeData: string | null | undefined;
  @Input() tableData: any[] = [];
  @Input() headers: any[] = [];

  applyFilter: string = "nom_officiel";

  todayTime = formatDate(new Date(), "HH:mm");

  patient: any;
  hospitalisation: any;
  days: any[] = [];
  watches: any[] = WATCHES;
  charts: any[] = WATCHES;
  search = new FormControl();

  list: any[] = []; // full list
  filtered_list: any[] = []; // filtered list
  numberOfPages: number = 0;
  searchResults: any[] = [];
  rowsPerPage: number = 15;
  currentPage: number = 1;
  suivis: any[] = [];
  chartsLabels: any[] = [];
  chartsDatas: any[] = [];
  last_room: any = null;

  currentEvolution = new FormControl(null, []);
  currentEvolutionDay: moment.Moment = moment();

  currentWatch: any = null;
  currentWatchList: any[] = [];

  currentWatchValueId: any = null;

  watchDay: moment.Moment = moment();
  watchTime = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );
  watchValue = new FormControl(
    null,
    [],
    [validateYupSchema(Yup.string().required(ErrorMessages.REQUIRED))]
  );

  watchValueOptions: any[] = [];

  watchFg: FormGroup = new FormGroup({});

  constructor(
    private hospitalisationStore: HospitalisationStore,
    private modalService: NgbModal,
    private toast: ToastrService,
    private message: MessageService
  ) {
    this.search.valueChanges.subscribe((value) => {
      this.doFilter(value);
    });

    Chart.register(...registerables);
    Chart.register(ChartDataLabels);
  }

  ngOnInit(): void {
    this.watchFg = new FormGroup({
      watchTime: this.watchTime,
      watchValue: this.watchValue,
    });

    // get days
    this.hospitalisationStore.getValue("hospitalisation")?.subscribe({
      next: (v) => {
          //get hospitalisation
          this.hospitalisation = v

          const res = [];
            let date = new Date(v["date_hospit"]);
            let currentDate = new Date();
            let days = Math.floor(
              (currentDate.getTime() - date.getTime()) / 1000 / 60 / 60 / 24
            );
            // let r = moment(this.hospitalisation["date_hospit"]);
            for (let i = 0; i <= days; i++) {
              let currentDate = moment(date).add(i, "days");
              res.push({
                o: currentDate.format("yyyy-MM-DD"),
                i: i,
              });
            }

            this.days = res
      },
    });

    this.hospitalisationStore.getValue("suivis")?.subscribe({
      next: (v) => {
        this.suivis = v
      },
    });

    this.subscription = this.hospitalisationStore.stateChanged.pipe(pairwise())
    .subscribe(([p, c]) => {
        if (hasStateChanges(this.patient, p.patient, c.patient)) {
          this.patient = c.patient;
        }

        if (hasStateChanges(this.suivis, p.suivis, c.suivis)) {
          this.suivis = c.suivis;
        }


        // if (hasStateChanges(this.hospitalisation, p.hospitalisation, c.hospitalisation)) {
        //   this.hospitalisation = c.hospitalisation;          
        // } else {
        // }
      }
    );

    this.list = this.tableData;

    /* reformat chambres data */
    if (this.typeData == "chambres") {
      let d: object[] = [];
      for (const chambreKey in this.tableData) {
        if (this.tableData.hasOwnProperty(chambreKey)) {
          const chambre = this.tableData[chambreKey];

          for (const litKey in chambre.lits) {
            const litValue = chambre.lits[litKey];

            if (litValue !== null && litValue !== undefined) {
              d.push({
                id: litValue.id,
                chambre: chambre.id,
                libelle: `${chambre.nom} - ${litValue.nom}`,
              });
            }
          }
        }
      }

      this.list = d;

      this.setCurrentPage(1);
    }

    switch (this.typeData) {
      case "medic":
      case "conso":
        this.applyFilter = "nom_officiel";
        break;

      case "examens":
      case "chambres":
        this.applyFilter = "libelle";
        break;
    }

    this.calculateNumberOfPages();
    this.setCurrentPage(1);
  }

  get numberOfPagesArray(): number[] {
    return new Array(this.numberOfPages).fill(0).map((_, index) => index + 1);
  }

  ngAfterViewInit() {
    // this.initLineChart();
  }

  initLineChart() {
    let re_watch: any = [];

    WATCHES.forEach((w: any) => {
      const watchname = w.name;

      const list = this.suivis
        .filter(
          (d) =>
            d["type"] === "watches" &&
            d.extras &&
            "name" in JSON.parse(d.extras)
        )
        .filter((s: any) => JSON.parse(s.extras).name === watchname)
        .map((r) => {
          const [hours, minutes] = JSON.parse(r.extras).data.hour.split(":");

          const watchMoment = moment(r.apply_date).set({
            hour: hours,
            minute: minutes,
          });

          return {
            date: r.apply_date,
            milli: watchMoment.valueOf(),
            value: Number(JSON.parse(r.extras).data.value),
            hour: JSON.parse(r.extras).data.hour,
          };
        })
        .sort((a, b) => a.milli - b.milli);

      if (list.length === 0) {
        w.empty = true;
      } else {
        re_watch.push(w);
      }

      const lineCanvasEle: any = document.getElementById(w.name);

      if (lineCanvasEle) {
        if (list.length === 0) {
          lineCanvasEle.height = 50;
          const ctx = lineCanvasEle.getContext("2d");
          const centerX = lineCanvasEle.width / 2;
          const centerY = lineCanvasEle.height / 2;
          const width = 30;

          ctx.fillStyle = "#3577f1";
          ctx.fillRect(centerX - width / 2, centerY - 1, width, 2);
          ctx.fillRect(centerX - 1, centerY - width / 2, 2, width);
        } else {
          const lineChar = new Chart(lineCanvasEle.getContext("2d"), {
            type: "line",
            data: {
              labels: list.map(
                (m) =>
                  "J" +
                  parseInt(
                    this.days.find((d) =>
                      moment(d.o).isSame(moment(m.date).format("yyyy-MM-DD"))
                    ).i + 1
                  )
              ),
              datasets: [
                {
                  data: list.map((m) => m.value),
                  borderColor: "#8A1776",
                  borderWidth: 3,
                  pointBackgroundColor: "#8A1776",
                  pointBorderWidth: 2,
                  pointStyle: "circle",
                  pointRadius: 7,
                  pointHoverRadius: 9,
                  tension: 0.5,
                  datalabels: {
                    labels: {
                      hour: {
                        font: {
                          weight: "bold",
                          family: "Inter",
                          size: 12,
                        },
                        align: "end",
                        anchor: "end",
                        offset: 1,
                        color: "green",
                        formatter: (value: any, context: any) => {
                          return `${list[context.dataIndex].hour}`;
                        },
                      },
                      title: {
                        font: {
                          weight: "bold",
                          family: "Inter",
                          size: 15,
                        },
                        align: "start",
                        anchor: "start",
                        offset: 1,
                        color: "#8A1776",
                      },
                    },
                  },
                },
              ],
            },
            options: {
              layout: {
                padding: {
                  left: 0,
                  right: 0,
                  top: 20,
                  bottom: 30,
                },
              },
              indexAxis: "x",
              maintainAspectRatio: false,
              // aspectRatio: 1,

              responsive: false,
              scales: {
                x: {
                  grid: {
                    display: false,
                  },
                  offset: true,
                  position: w.xPosition as
                    | "left"
                    | "right"
                    | "bottom"
                    | "top"
                    | "center",
                },
                y: {
                  ticks: {
                    stepSize: w.stepSize,
                    suggestedMin: 0,
                  } as { [key: string]: any },
                },
              },
              plugins: {
                tooltip: {
                  enabled: true,
                },
                legend: {
                  display: false,
                },
                // title: {
                //   display: true,
                //   fullSize: false,
                //   position: "left",
                //   padding: {
                //     bottom: 10,
                //   },
                //   text: w.label,
                //   font: {
                //     family: "Inter",
                //     size: 14,
                //     style: "normal",
                //     weight: "normal",
                //   },
                // },
              },
            },
          });
        }
      }
    });

    re_watch = [
      ...re_watch,
      ...WATCHES.filter(
        (w: any) => re_watch.find((r: any) => r.name === w.name) === undefined
      ),
    ];

    this.charts = re_watch;
  }

  private calculateNumberOfPages(): void {
    if (this.list !== null) {
      const itemsToPaginate =
        this.search.value === null || this.search.value === ""
          ? this.list
          : this.searchResults;

      this.numberOfPages = Math.ceil(
        (itemsToPaginate.length || 0) / this.rowsPerPage
      );
    } else {
      this.numberOfPages = 0;
    }
  }

  get visiblePageNumbers(): number[] {
    const surroundingPages = 2;
    const visiblePages: number[] = [];

    if (this.numberOfPages <= 7) {
      // If there are 7 or fewer pages, display all pages
      for (let i = 1; i <= this.numberOfPages; i++) {
        visiblePages.push(i);
      }
    } else {
      // Display a subset of pages with surrounding pages
      const start = Math.max(1, this.currentPage - surroundingPages);
      const end = Math.min(
        this.numberOfPages,
        this.currentPage + surroundingPages
      );

      // Display "Prev" button if needed
      if (start > 1) {
        visiblePages.push(1);
        if (start > 2) {
          visiblePages.push(-1); // Ellipsis
        }
      }

      // Display visible pages
      for (let i = start; i <= end; i++) {
        visiblePages.push(i);
      }

      // Display "Next" button if needed
      if (end < this.numberOfPages) {
        if (end < this.numberOfPages - 1) {
          visiblePages.push(-1); // Ellipsis
        }
        visiblePages.push(this.numberOfPages);
      }
    }

    return visiblePages;
  }

  previous(): void {
    this.currentPage--;
    this.setCurrentPage(this.currentPage);
  }

  next(): void {
    this.currentPage++;
    this.setCurrentPage(this.currentPage);
  }

  setCurrentPage(page: number): void {
    if (this.list == null) {
      this.filtered_list = [];
    } else {
      this.currentPage = page;
      this.filtered_list = (
        this.search.value === null || this.search.value === ""
          ? this.list
          : this.searchResults
      ).slice(
        (this.currentPage - 1) * this.rowsPerPage,
        this.currentPage * this.rowsPerPage
      );
    }

  }


  doFilter(text: string): void {
    try {
      if (text.length === 0) {
        this.filtered_list = this.list;
      } else {
        this.searchResults = this.list.filter((object: string | null) => {
          let result = "";
          if (object === null) return null;
          if (
            this.applyFilter === null ||
            this.applyFilter.split(",").length === 0
          )
            return null;
          for (const key of this.applyFilter.split(",")) {
            let rowValue = "";
            rowValue =
              object[key as keyof typeof object] !== null &&
              object[key.trim() as keyof typeof object] !== undefined
                ? object[key.trim() as keyof typeof object].toString()
                : "";
            // }
            if (rowValue.toLowerCase().includes(text.trim().toLowerCase())) {
              result = object;
              break;
            }
          }
          return result;
        });
        this.filtered_list = this.searchResults;
      }

      this.calculateNumberOfPages();
      this.setCurrentPage(1);
    } catch (e) {
      console.log("filtering : " + e);
    }
  }

  getRowId(day: moment.Moment, type_id: number, sub_id?: number) {
    if (this.suivis !== null && this.suivis !== undefined) {
      if (this.typeData == "chambres") {
        const ch = this.suivis.find(
          (t) =>
            t["type"] === "chambres" &&
            t["type_id"] === sub_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );
        const li = this.suivis.find(
          (t) =>
            t["type"] === "lits" &&
            t["type_id"] === type_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );

        return (
          (ch !== null && ch !== undefined ? ch.id : null) +
          "/" +
          (li !== null && li !== undefined ? li.id : null)
        );
      } else {
        const res = this.suivis.find(
          (t) =>
            t["type"] === this.typeData &&
            t["type_id"] === type_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );

        // console.log(res)

        return res !== null && res !== undefined ? res.id : null;
      }
    } else {
      return null;
    }
  }

  setActive(day: moment.Moment, type_id: number, sub_id?: number) {
    if (this.suivis !== null && this.suivis !== undefined) {
      if (this.typeData == "chambres") {
        const ch = this.suivis.find(
          (t) =>
            t["type"] === "chambres" &&
            t["type_id"] === sub_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );
        const li = this.suivis.find(
          (t) =>
            t["type"] === "lits" &&
            t["type_id"] === type_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );

        return (
          ch !== null && ch !== undefined && li !== null && li !== undefined
        );
      } else {
        const res = this.suivis.find(
          (t) =>
            t["type"] === this.typeData &&
            t["type_id"] === type_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );
        return res !== null && res !== undefined;
      }
    } else {
      return false;
    }
  }

  getTotal(type_id: number, sub_id?: any) {
    let res = 0;

    if (this.suivis !== null && this.suivis !== undefined) {
      if (this.typeData == "chambres") {
        const li = this.suivis.filter(
          (t) => t["type"] === "lits" && t["type_id"] === type_id
        );

        res = li.reduce((total: any, item: any) => total + item.qte, 0);
      } else {
        const arr = this.suivis.filter(
          (t) => t["type"] === this.typeData && t["type_id"] === type_id
        );

        const v = arr.reduce((total, item) => total + item.qte, 0);
        res = v;
      }
    }

    return res !== 0 ? res : "";
  }

  getRowQte(day: moment.Moment, type_id: number, sub_id?: number) {
    if (this.suivis !== null && this.suivis !== undefined) {
      if (this.typeData !== "chambres") {
        const res = this.suivis.find(
          (t) =>
            t["type"] === this.typeData &&
            t["type_id"] === type_id &&
            moment(day).isSame(moment(t["apply_date"]))
        );

        return res?.qte;

        // return res !== null && res !== undefined ? res.qte : '';
      }
    } else {
      return "";
    }
  }

  selectItem(day: moment.Moment, type_id: number, extra?: any) {
    if (this.typeData === "chambres") {
      const row = this.getRowId(day, type_id, extra);

      // We need to remove all room / bed for that day
      this.suivis
        .filter((s) => moment(s.apply_date).isSame(day) && s.type === "lits")
        .forEach((b : any) => {
          this.hospitalisationStore.removeSuivi(b.id);
        });
      this.suivis
        .filter((s) => moment(s.apply_date).isSame(day) && s.type === "chambres")
        .forEach((r: any) => {
          this.hospitalisationStore.removeSuivi(r.id);
        });

      if (row.toString().includes("null")) {
        const c = {
          type: "chambres",
          type_id: extra,
          qte: 1,
          apply_date: moment(day).format("YYYY-MM-DD[T]HH:mm:ss"),
          hospit_id: this.hospitalisation.id,
        };

        const l = {
          type: "lits",
          type_id: type_id,
          qte: 1,
          apply_date: moment(day).format("YYYY-MM-DD[T]HH:mm:ss"),
          hospit_id: this.hospitalisation.id,
        };

        this.hospitalisationStore.commitSuivi(c);
        this.suivis.push(c);
        this.hospitalisationStore.commitSuivi(l);
        this.suivis.push(l);
      }
    } else {
      const row = this.getRowId(day, type_id, extra);
      if (row === null || row === undefined) {
        const data = {
          type: this.typeData,
          type_id: type_id,
          qte: 1,
          apply_date: moment(day).format("YYYY-MM-DD[T]HH:mm:ss"),
          hospit_id: this.hospitalisation.id,
          id: Date.now(),
        };
        this.hospitalisationStore.commitSuivi(data);
        this.suivis.push(data)
      } else {
        let rowValue = this.suivis.find((s) => s.id === row);
        rowValue.qte++;

        delete rowValue.created_at;
        delete rowValue.updated_at;

        rowValue.hospit_id = this.hospitalisation.id;
        this.hospitalisationStore.updateSuivi(rowValue);
      }

      // console.log(this.suivis.length)
    }
  }

  removeItem(day: moment.Moment, type_id: number, extra?: any) {
    const row = this.getRowId(day, type_id, extra);

    if (row !== null && row !== undefined) {
      if (row.toString().includes("/")) {
        const arr = row.split("/");
        const ch = arr[0];
        const li = arr[1];

        if (ch !== "null" && li !== "null") {
          this.hospitalisationStore.removeSuivi(ch);
          this.hospitalisationStore.removeSuivi(li);
          this.suivis = this.suivis.filter(
            (s) => s.id.toString() !== li && s.id.toString() !== ch
          );
          // console.log(this.suivis);
        }
      } else {
        // means its not room suivi
        let rowValue = this.suivis.find((s) => s.id === row);
        // console.log(rowValue)
        if (rowValue.qte > 1) {
          rowValue.qte--;

          delete rowValue.created_at;
          delete rowValue.updated_at;
          // delete rowValue.id

          rowValue.hospit_id = this.hospitalisation.id;
          this.hospitalisationStore.updateSuivi(rowValue);
        } else {
          this.hospitalisationStore.removeSuivi(row);
          this.suivis = this.suivis.filter((s) => s.id !== row);
        }
      }
    }
  }

  dbClick(day: moment.Moment, type_id: number, extra?: any) {
    let app = this;
    timer = setTimeout(function () {
      timer = null;
      app.selectItem(day, type_id, extra);
    }, timeout);
  }

  sClick(day: moment.Moment, type_id: number, extra?: any) {
    if (timer) {
      clearTimeout(timer);
      timer = null;
      this.removeItem(day, type_id, extra);
    }
  }

  showEvolution(day: moment.Moment) {
    let evolution = null;
    if (this.suivis !== null && this.suivis !== undefined) {
      const res = this.suivis.find(
        (t) =>
          t["type"] === "evolution" &&
          moment(day).isSame(moment(t["apply_date"]))
      );

      evolution = res;

      if (
        evolution !== undefined &&
        "extras" in evolution &&
        "comments" in JSON.parse(evolution.extras)
      ) {
        this.currentEvolution.setValue(JSON.parse(evolution.extras).comments);
      } else {
        this.currentEvolution.setValue("");
      }

      this.currentEvolutionDay = day;
      this.modalService.open(this.evolutionEdition, {
        size: "lg",
        centered: true,
        keyboard: true,
        backdrop: "static",
      });
    }
  }

  getEvolutionData(day: moment.Moment) {
    if (this.suivis !== null && this.suivis !== undefined) {
      const res = this.suivis.find(
        (t) =>
          t["type"] === "evolution" &&
          moment(day).isSame(moment(t["apply_date"]))
      );

      if (
        res !== undefined &&
        "extras" in res &&
        "comments" in JSON.parse(res.extras)
      ) {
        return JSON.parse(res.extras).comments;
      } else {
        return "";
      }
    }
  }

  getEvolutionDataId(day: moment.Moment) {
    if (this.suivis !== null && this.suivis !== undefined) {
      const res = this.suivis.find(
        (t) =>
          t["type"] === "evolution" &&
          moment(day).isSame(moment(t["apply_date"]))
      );

      if (
        res !== undefined &&
        "extras" in res &&
        "comments" in JSON.parse(res.extras)
      ) {
        return res.id;
      } else {
        return "";
      }
    }
  }

  SaveEvolution() {
    if (this.currentEvolution.value.trim() === "") {
      this.toast.error(
        "Hospitalisation",
        "Veuillez saisir l'évolution du patient"
      );
    } else {
      const res = this.suivis.find(
        (t) =>
          t["type"] === "evolution" &&
          moment(this.currentEvolutionDay).isSame(moment(t["apply_date"]))
      );

      if (res !== undefined) {
        res.extras = JSON.stringify({
          comments: this.currentEvolution.value,
        });
        this.hospitalisationStore.updateSuivi(res);
      } else {
        const data = {
          type: this.typeData,
          type_id: null,
          qte: 0,
          apply_date: moment(this.currentEvolutionDay).format(
            "YYYY-MM-DD[T]HH:mm:ss"
          ),
          hospit_id: this.hospitalisation.id,
          id: Date.now(),
          extras: JSON.stringify({
            comments: this.currentEvolution.value,
          }),
        };
        this.hospitalisationStore.commitSuivi(data);

        // this.suivis.push(data);
      }

      this.modalService.dismissAll();
    }
  }

  showWatch(watch: any) {
    if (watch.type === "chart") {
      if (this.suivis !== null && this.suivis !== undefined) {
        const list = this.suivis.filter(
          (d) =>
            d["type"] === "watches" &&
            d.extras &&
            "name" in JSON.parse(d.extras)
        );

        const res = list.filter(
          (s: any) => JSON.parse(s.extras).name === watch.name
        );

        this.currentWatchList = res;
        this.currentWatch = watch;
        this.modalService.open(this.watchesEdition, {
          size: "lg",
          centered: true,
          keyboard: false,
          backdrop: "static",
        });
      }
    } else {
      if (watch.empty === undefined) {
        watch.empty = false;
      } else {
        watch.empty = !watch.empty;
      }
    }
  }

  getWatchData(day: moment.Moment, watch: any = null): any[] {
    let res = [];

    if (this.currentWatchList !== null && this.currentWatchList !== undefined) {
      if (watch === null) {
        const watches = this.currentWatchList;
        res = watches.filter(
          (t) =>
            t["type"] === "watches" &&
            moment(day).isSame(moment(t["apply_date"]))
        );
      } else {
        const list = this.suivis.filter(
          (d) =>
            d["type"] === "watches" &&
            moment(day).isSame(moment(d["apply_date"])) &&
            d.extras &&
            "name" in JSON.parse(d.extras)
        );

        res = list.filter((s: any) => JSON.parse(s.extras).name === watch.name);
      }

      return res
        .map((m) => {
          const [hours, minutes] = JSON.parse(m.extras).data.hour.split(":");
          const totalMilliseconds =
            Number(hours) * 60 * 60 * 1000 + Number(minutes) * 60 * 1000;

          const watchMoment = moment(m.apply_date).set({
            hour: hours,
            minute: minutes,
          });

          return {
            id: m.id,
            hour: JSON.parse(m.extras).data.hour,
            value: JSON.parse(m.extras).data.value,
            time: watchMoment.valueOf(),
            watch_id: JSON.parse(m.extras).data.id,
          };
        })
        .sort((a, b) => a.time - b.time);
    } else return [];
  }

  showWatchValueSelector(
    day: moment.Moment,
    event: MouseEvent,
    watch: any = null
  ) {
    this.watchDay = day;

    if (watch !== null) {
      this.currentWatch = watch;
      if (watch.type === "value" && watch.options !== undefined) {
        this.generateOptions(watch.options);
      }
    }

    const t = formatDate(new Date(), "HH:mm");
    this.watchTime.setValue(t);
    this.watchValue.setValue("");
    this.currentWatchValueId = null;

    this.modalReference = this.modalService.open(this.watchesValueEdition, {
      size: "sm",
      centered: true,
      keyboard: true,
      backdrop: "static",
    });
  }

  async saveWatch(watch: any = null) {
    if (this.watchValue.value === "" || this.watchValue.value === null) {
      this.toast.error("Vous devez saisir la valeur");
      return;
    }

    const [hours, minutes] = this.watchTime.value.split(":");
    const totalMilliseconds =
      Number(hours) * 60 * 60 * 1000 + Number(minutes) * 60 * 1000;

    console.log(this.watchTime.value);
    console.log(totalMilliseconds);

    let value = null;
    if (this.currentWatch.type === "value") {
      value = this.watchValue.value;
    } else if (this.currentWatch.type === "chart") {
      value = parseFloat(
        this.watchValue.value.toString().includes(",")
          ? this.watchValue.value.toString().replace(",", ".")
          : (value = this.watchValue.value)
      );
    }

    const data = {
      type: this.typeData,
      type_id: null,
      qte: 0,
      apply_date: moment(this.watchDay).format("YYYY-MM-DD[T]HH:mm:ss"),
      hospit_id: this.hospitalisation.id,
      id: Date.now(),
      extras: JSON.stringify({
        name: this.currentWatch.name,
        data: {
          hour: this.watchTime.value,
          value: value,
          time: totalMilliseconds,
        },
      }),
    };

    if (watch === null) {
      this.currentWatchList.push(data);

      this.currentWatchList.sort((a, b) => {
        const timeA = JSON.parse(a.extras).data.time;
        const timeB = JSON.parse(b.extras).data.time;
        return timeA - timeB;
      });
    } else {
      let curr = this.suivis
        .filter(
          (d) =>
            d["type"] === "watches" &&
            d.extras &&
            "name" in JSON.parse(d.extras)
        )
        .filter((s: any) => JSON.parse(s.extras).name === watch.name);

      curr.push(data);
      curr.sort((a, b) => {
        const timeA = JSON.parse(a.extras).data.time;
        const timeB = JSON.parse(b.extras).data.time;
        return timeA - timeB;
      });
    }

    this.watchFg.reset();
    this.modalReference.close();
    const result = await this.hospitalisationStore.commitSuivi(data);

    if (result) this.refreshCharts();
  }

  refreshCharts() {
    this.charts.forEach((f) => {
      // document.getElementById(f.name);
      let chartStatus = Chart.getChart(f.name); // <canvas> id
      if (chartStatus != undefined) {
        chartStatus.destroy();
      }
    });

    this.initLineChart();

    //-- End of chart destroy

    // const tmp = JSON.parse(JSON.stringify(this.watches));
    // this.watches = [];
    // const app = this;
    // setTimeout(function () {
    //   app.watches = tmp;

    //   setTimeout(function () {
    //     app.initLineChart();
    //   }, 1000);
    // }, 500);
  }

  async removeWatchData(data: any, watch: any = null) {
    const confirm = await this.message.confirmDialog(
      WarningMessages.SURE_TO_DELETE
    );
    if (!confirm) return;

    this.hospitalisationStore.removeSuivi(data.id);
    if (watch === null) {
      this.currentWatchList = this.currentWatchList.filter(
        (c) => JSON.parse(c.extras).data.id !== data.watch_id
      );
      this.refreshCharts();
    } else {
      this.suivis = this.suivis
        .filter(
          (d) =>
            d["type"] === "watches" &&
            d.extras &&
            "name" in JSON.parse(d.extras)
        )
        .filter(
          (s: any) =>
            JSON.parse(s.extras).name === watch.name &&
            JSON.parse(s.extras).data.id !== data.watch_id
        );
    }
  }

  generateOptions(list: any[]) {
    this.watchValueOptions = list.map((o) => {
      return {
        id: slugify(o),
        text: o,
      };
    });

    console.log(this.watchValueOptions);
  }

  ngOnChanges(): void {
    this.calculateNumberOfPages();
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
