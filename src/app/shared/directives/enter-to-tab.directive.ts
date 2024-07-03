import { Directive, HostListener } from '@angular/core';

@Directive({
    selector: '[enterToTab]'
})
export class EnterToTabDirective {

    @HostListener('keydown', ['$event'])
    handleKeyDown(event: KeyboardEvent) {
        if (event.key === 'Enter') {
            event.preventDefault();
            this.focusNextElement(event.target as HTMLElement);
        }
    }

    private focusNextElement(currentElement: HTMLElement) {
        const inputList = currentElement.closest('.enterToTab')!.querySelectorAll('input');
        const currentIndex = (Array.from(inputList) as HTMLElement[]).indexOf(currentElement)
        const nextElement = inputList[currentIndex + 1] || inputList[0]
        nextElement.focus();
    }
}
