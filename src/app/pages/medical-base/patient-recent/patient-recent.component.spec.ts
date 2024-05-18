import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientRecentComponent } from './patient-recent.component';

describe('PatientRecentComponent', () => {
  let component: PatientRecentComponent;
  let fixture: ComponentFixture<PatientRecentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientRecentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientRecentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
