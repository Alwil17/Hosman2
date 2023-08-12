import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientWaitingListComponent } from './patient-waiting-list.component';

describe('PatientWaitingListComponent', () => {
  let component: PatientWaitingListComponent;
  let fixture: ComponentFixture<PatientWaitingListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientWaitingListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientWaitingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
