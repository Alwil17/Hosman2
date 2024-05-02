import { Directive, HostBinding } from '@angular/core';

@Directive({
  selector: '[appIncreaseFontSize]'
})
export class AppIncreaseFontSizeDirective {
  @HostBinding('style.font-size') fontSize: string;

  constructor() {
    const currentFontSize = parseFloat(window.getComputedStyle(document.body).fontSize);
    const newFontSize = isNaN(currentFontSize) ? 16 : currentFontSize * 1.3;
    this.fontSize = newFontSize + 'px';
  }
}