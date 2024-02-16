import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdultPatientBackgroundsModalComponent } from './adult-patient-backgrounds-modal.component';

describe('AdultPatientBackgroundsModalComponent', () => {
  let component: AdultPatientBackgroundsModalComponent;
  let fixture: ComponentFixture<AdultPatientBackgroundsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdultPatientBackgroundsModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdultPatientBackgroundsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
