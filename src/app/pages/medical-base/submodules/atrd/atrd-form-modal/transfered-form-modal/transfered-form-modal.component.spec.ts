import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferedFormModalComponent } from './transfered-form-modal.component';

describe('TransferedFormModalComponent', () => {
  let component: TransferedFormModalComponent;
  let fixture: ComponentFixture<TransferedFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransferedFormModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferedFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
