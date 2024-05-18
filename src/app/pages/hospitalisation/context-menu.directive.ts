// context-menu.directive.ts

import { Directive, ElementRef, Input, OnInit, Renderer2 } from "@angular/core";
import { HospitalisationStore } from "@stores/hospitalisation";

@Directive({
  selector: "[hosContextMenu]",
})
export class ContextMenuDirective implements OnInit {
  @Input() contextMenuItems: any[] = [];
  @Input() contextMenuMode: string = "light";
  @Input() mandatoryClass: string = "ctxmenu";

  constructor(
    private el: ElementRef,
    private renderer: Renderer2,
    private hospitalisationStore: HospitalisationStore
  ) {}

  ngOnInit() {
    const contextMenu = this.renderMenu();

    this.renderer.listen("document", "click", () =>
      this.closeMenu(contextMenu)
    );
    this.renderer.listen("window", "blur", () => this.closeMenu(contextMenu));
    this.renderer.listen(this.el.nativeElement, "contextmenu", (e) =>
      this.handleContextMenu(e, contextMenu)
    );

    this.renderer.appendChild(this.el.nativeElement, contextMenu);
  }

  private renderMenu() {
    const menuContainer = this.renderer.createElement("ul");
    this.renderer.addClass(menuContainer, "contextMenu");
    this.renderer.setAttribute(
      menuContainer,
      "data-theme",
      this.contextMenuMode
    );

    this.contextMenuItems.forEach((item, index) => {
      const menuItem = this.createItemMarkup(item, index);
      this.renderer.appendChild(menuContainer, menuItem);
    });

    return menuContainer;
  }

  private createItemMarkup(data: any, index: number) {
    const button = this.renderer.createElement("button");
    const menuItem = this.renderer.createElement("li");

    button.innerHTML = data.content;
    this.renderer.addClass(button, "contextMenu-button");
    this.renderer.addClass(menuItem, "contextMenu-item");

    if (data.divider) {
      this.renderer.setAttribute(menuItem, "data-divider", data.divider);
    }

    this.renderer.appendChild(menuItem, button);

    if (data.events && Object.keys(data.events).length !== 0) {
      Object.entries(data.events).forEach((event) => {
        const [key, value]: any = event;
        this.renderer.listen(button, key, value);
      });
    }

    this.renderer.setStyle(button, "animation-delay", `${index * 0.08}s`);

    return menuItem;
  }

  private closeMenu(menu: any) {
    if (menu.parentNode) {
      this.renderer.removeChild(document.body, menu);
    }
  }

  private handleContextMenu(event: MouseEvent, menu: any) {
    if (
      event.target &&
      (event.target as Element).classList.contains("t-content")
    ) {
      event.preventDefault();

      // add element to store
      this.hospitalisationStore.selectContextMenuElement({
        id: (event.target as Element).getAttribute("data-item-id"),
        day: (event.target as Element).getAttribute("data-item-day"),
        typeData: (event.target as Element).getAttribute("data-item-typedata"),
        qte: (event.target as Element).getAttribute("data-item-qte"),
        type_id: (event.target as Element).getAttribute("data-item-type-id"),
      });

      const { clientX, clientY } = event;
      const positionY =
        clientY + menu.scrollHeight >= window.innerHeight
          ? window.innerHeight - menu.scrollHeight - 20
          : clientY;

      const positionX =
        clientX + menu.scrollWidth >= window.innerWidth
          ? window.innerWidth - menu.scrollWidth - 20
          : clientX;

      this.renderer.setStyle(menu, "width", "150px");
      // this.renderer.setStyle(menu, 'height', `${menu.scrollHeight}px`);
      this.renderer.setStyle(menu, "top", `${positionY}px`);
      this.renderer.setStyle(menu, "left", `${positionX}px`);
      this.renderer.setStyle(menu, "display", "block");

      this.closeMenu(menu);

      document.body.appendChild(menu);
    }
  }
}
