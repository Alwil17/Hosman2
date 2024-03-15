import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalisationFormModalComponent } from './hospitalisation-form-modal.component';

describe('HospitalisationFormModalComponent', () => {
  let component: HospitalisationFormModalComponent;
  let fixture: ComponentFixture<HospitalisationFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalisationFormModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HospitalisationFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
