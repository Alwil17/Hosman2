import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientVisitFormModalComponent } from './patient-visit-form-modal.component';

describe('PatientVisitFormModalComponent', () => {
  let component: PatientVisitFormModalComponent;
  let fixture: ComponentFixture<PatientVisitFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientVisitFormModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientVisitFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
