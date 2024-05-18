import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChildPatientBackgroundsModalComponent } from './child-patient-backgrounds-modal.component';

describe('ChildPatientBackgroundsModalComponent', () => {
  let component: ChildPatientBackgroundsModalComponent;
  let fixture: ComponentFixture<ChildPatientBackgroundsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChildPatientBackgroundsModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChildPatientBackgroundsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
