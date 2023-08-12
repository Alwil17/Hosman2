import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientInvoiceFormComponent } from './patient-invoice-form.component';

describe('PatientInvoiceFormComponent', () => {
  let component: PatientInvoiceFormComponent;
  let fixture: ComponentFixture<PatientInvoiceFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientInvoiceFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientInvoiceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
