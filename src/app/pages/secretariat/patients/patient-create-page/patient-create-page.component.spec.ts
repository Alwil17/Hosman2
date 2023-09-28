import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientCreatePageComponent } from './patient-create-page.component';

describe('PatientCreatePageComponent', () => {
  let component: PatientCreatePageComponent;
  let fixture: ComponentFixture<PatientCreatePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientCreatePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientCreatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
