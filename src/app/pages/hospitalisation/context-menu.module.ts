import { NgModule } from '@angular/core';
import { ContextMenuDirective } from './context-menu.directive';

@NgModule({
  declarations: [ContextMenuDirective],
  exports: [ContextMenuDirective], // Export the directive to use it in other modules
})
export class ContextMenuModule {}