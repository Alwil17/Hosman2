import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientAddressFormComponent } from './patient-address-form.component';

describe('PatientAddressFormComponent', () => {
  let component: PatientAddressFormComponent;
  let fixture: ComponentFixture<PatientAddressFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientAddressFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientAddressFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
