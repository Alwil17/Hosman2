import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceiptsSummaryComponent } from './receipts-summary.component';

describe('ReceiptsSummaryComponent', () => {
  let component: ReceiptsSummaryComponent;
  let fixture: ComponentFixture<ReceiptsSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReceiptsSummaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceiptsSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
