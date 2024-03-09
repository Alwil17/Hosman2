import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrescriptionsModalComponent } from './prescriptions-modal.component';

describe('PrescriptionsModalComponent', () => {
  let component: PrescriptionsModalComponent;
  let fixture: ComponentFixture<PrescriptionsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrescriptionsModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrescriptionsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
