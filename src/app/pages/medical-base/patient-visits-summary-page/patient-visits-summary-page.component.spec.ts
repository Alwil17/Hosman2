import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientVisitsSummaryPageComponent } from './patient-visits-summary-page.component';

describe('PatientVisitsSummaryPageComponent', () => {
  let component: PatientVisitsSummaryPageComponent;
  let fixture: ComponentFixture<PatientVisitsSummaryPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientVisitsSummaryPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientVisitsSummaryPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
