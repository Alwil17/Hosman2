import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicinesPageComponent } from './medicines-page.component';

describe('MedicinesPageComponent', () => {
  let component: MedicinesPageComponent;
  let fixture: ComponentFixture<MedicinesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicinesPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicinesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
