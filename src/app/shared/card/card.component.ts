import { Component, Input, OnInit } from "@angular/core";

@Component({
  selector: "app-card",
  templateUrl: "./card.component.html",
  styleUrls: ["./card.component.scss"],
})
export class CardComponent implements OnInit {
  @Input()
  title = "";

  @Input()
  separator = false;

  @Input()
  hasHeader = false;

  @Input()
  hasBody = false;

  @Input()
  hasFooter = false;

  constructor() {}

  ngOnInit(): void {}
}
