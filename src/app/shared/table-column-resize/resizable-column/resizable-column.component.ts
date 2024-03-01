import { Component, HostBinding, OnInit } from "@angular/core";

@Component({
  selector: "th[appColumnResizer]",
  template: `
    <div class="wrapper">
      <div class="content">
        <ng-content></ng-content>
      </div>
      <div class="bar" (appColumnResizer)="onResize($event)"></div>
    </div>
  `,
  styles: [
    `
      :host {
        &:last-child .bar {
          display: none;
        }
      }

      .wrapper {
        display: flex;
        justify-content: flex-end;
      }

      .content {
        flex: 1;
      }

      .bar {
        position: absolute;
        top: 0;
        bottom: 0;
        width: 3px;
        // margin: 0 -16px 0 16px;
        margin-right: -6px;
        justify-self: flex-end;
        // border-left: 2px solid transparent;
        // border-right: 2px solid transparent;
        background: blueviolet;
        background-clip: content-box;
        cursor: ew-resize;
        opacity: 0;
        transition: opacity 0.3s;

        &:hover,
        &:active {
          opacity: 1;
        }
      }
    `,
  ],
})
export class ResizableColumnComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  @HostBinding("style.min-width.px")
  minWidth: number | null = null;

  onResize(width: any) {
    this.minWidth = width;

    console.log("Resizing");
  }
}
