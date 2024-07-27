import {
  AfterViewInit,
  Component,
  ComponentRef,
  ElementRef,
  Input,
  OnInit,
  ViewChild,
  ViewContainerRef,
  ViewRef
} from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import {DynamicLoaderService} from "@services/shared/dynamic-loader.service";

@Component({
  selector: "multi-input",
  templateUrl: "./multi-input.component.html",
  styleUrls: ["./multi-input.component.scss"],
})
export class MultiInputComponent implements OnInit, AfterViewInit {
  @ViewChild('dynamicComponentContainer', { read: ViewContainerRef })
  dynamicComponentContainer!: ViewContainerRef;
  dynamicComponentRef!: ComponentRef<any> | null

  @Input() config: any = false;
  @Input() group = new FormGroup({});

  name: string = "";
  control: FormControl = new FormControl();

  constructor(private dynamicLoaderService: DynamicLoaderService) {}

  ngOnInit(): void {
    this.name = this.config.name;

    if (this.name !== null && this.name !== undefined) {
      if (this.group?.contains(this.name!)) {
        this.control = this.group?.get(this.name!) as FormControl
        // if (this.config.default !== undefined) this.control.setValue(this.config.default)
      } else {
        this.group?.addControl(this.name!, this.control);
      }
     
    }

    // check ifs
    const ifs = this.config.if;
    if (ifs !== undefined) {
      ifs.forEach((fi: any) => {
        // console.log(typeof fi.value)
        if ((typeof fi.value === 'object' ? !fi.value.includes(this.group.get(fi.name)!.value) : this.group.get(fi.name)!.value !== fi.value)) this.config.show = false;

        this.group.get(fi.name)!.valueChanges.subscribe((value) => {
          // console.log(value)
          this.config.show = !(value === null ||(typeof fi.value === 'object' ? !fi.value.includes(value) : value !== fi.value));
        });
      });
    }
  }

  ngAfterViewInit() {
    if (this.config.type === 'component' && this.dynamicComponentContainer) {
      this.loadComponent(this.config.component)
    }

    // set default value
    if (this.control.value === undefined || this.control.value === null && this.config.default !== undefined) {
      this.control.setValue(this.config.default)
    }
    
  }

  loadComponent(componentName: string) {
    this.dynamicComponentRef = this.dynamicLoaderService.loadComponent(componentName, this.dynamicComponentContainer);
    const dynamicComponent = this.dynamicComponentRef?.instance
    dynamicComponent.valueEmitter.subscribe((data: any) => {
      this.control.setValue(data);
    });
  }
}
