import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PhoneBookFormModalComponent } from './phone-book-form-modal.component';

describe('PhoneBookFormModalComponent', () => {
  let component: PhoneBookFormModalComponent;
  let fixture: ComponentFixture<PhoneBookFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PhoneBookFormModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PhoneBookFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
