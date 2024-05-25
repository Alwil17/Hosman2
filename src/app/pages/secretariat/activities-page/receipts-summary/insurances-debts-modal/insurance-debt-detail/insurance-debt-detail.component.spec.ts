import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceDebtDetailComponent } from './insurance-debt-detail.component';

describe('InsuranceDebtDetailComponent', () => {
  let component: InsuranceDebtDetailComponent;
  let fixture: ComponentFixture<InsuranceDebtDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InsuranceDebtDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceDebtDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
