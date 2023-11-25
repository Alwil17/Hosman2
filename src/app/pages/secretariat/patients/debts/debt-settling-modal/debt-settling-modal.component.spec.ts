import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DebtSettlingModalComponent } from './debt-settling-modal.component';

describe('DebtSettlingModalComponent', () => {
  let component: DebtSettlingModalComponent;
  let fixture: ComponentFixture<DebtSettlingModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DebtSettlingModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DebtSettlingModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
