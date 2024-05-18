import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsurancesDebtsModalComponent } from './insurances-debts-modal.component';

describe('InsurancesDebtsModalComponent', () => {
  let component: InsurancesDebtsModalComponent;
  let fixture: ComponentFixture<InsurancesDebtsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InsurancesDebtsModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InsurancesDebtsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
