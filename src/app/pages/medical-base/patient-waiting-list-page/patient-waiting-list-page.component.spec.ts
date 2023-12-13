import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientWaitingListPageComponent } from './patient-waiting-list-page.component';

describe('PatientWaitingListPageComponent', () => {
  let component: PatientWaitingListPageComponent;
  let fixture: ComponentFixture<PatientWaitingListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientWaitingListPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientWaitingListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
