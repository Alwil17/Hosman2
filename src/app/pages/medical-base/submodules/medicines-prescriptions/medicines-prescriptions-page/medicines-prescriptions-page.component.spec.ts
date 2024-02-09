import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicinesPrescriptionsPageComponent } from './medicines-prescriptions-page.component';

describe('MedicinesPrescriptionsPageComponent', () => {
  let component: MedicinesPrescriptionsPageComponent;
  let fixture: ComponentFixture<MedicinesPrescriptionsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicinesPrescriptionsPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicinesPrescriptionsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
