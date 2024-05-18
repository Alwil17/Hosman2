import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllAppointmentsModalComponent } from './all-appointments-modal.component';

describe('AllAppointmentsModalComponent', () => {
  let component: AllAppointmentsModalComponent;
  let fixture: ComponentFixture<AllAppointmentsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllAppointmentsModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllAppointmentsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
