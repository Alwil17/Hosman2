import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientInfosFormComponent } from './patient-infos-form.component';

describe('PatientInfosFormComponent', () => {
  let component: PatientInfosFormComponent;
  let fixture: ComponentFixture<PatientInfosFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientInfosFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientInfosFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
