// dynamic-loader.service.ts
import {Injectable, ComponentFactoryResolver, ViewContainerRef, Type, ComponentRef} from '@angular/core';
import {SilvermanComponent} from "../../components/silverman/silverman.component";
import {GlasgowOnflyComponent} from "../../components/glasgow-onfly/glasgow-onfly.component";

@Injectable({
  providedIn: 'root'
})
export class DynamicLoaderService {
  constructor(private componentFactoryResolver: ComponentFactoryResolver) {}

  loadComponent(componentName: string, viewContainerRef: ViewContainerRef): ComponentRef<any> | null {
    const component = this.getComponentType(componentName);
    if (component) {
      const componentFactory = this.componentFactoryResolver.resolveComponentFactory(component);
      viewContainerRef.clear();
      return viewContainerRef.createComponent(componentFactory);
    }
    return null
  }

  private getComponentType(componentName: string): Type<any> | null {
    switch (componentName) {
      case 'silverman':
        return SilvermanComponent;
      case 'glasgow' :
        return GlasgowOnflyComponent
      default:
        return null;
    }
  }
}
