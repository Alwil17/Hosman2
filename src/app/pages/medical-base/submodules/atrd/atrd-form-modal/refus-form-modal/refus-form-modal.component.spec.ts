import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefusFormModalComponent } from './refus-form-modal.component';

describe('RefusFormModalComponent', () => {
  let component: RefusFormModalComponent;
  let fixture: ComponentFixture<RefusFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefusFormModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefusFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
