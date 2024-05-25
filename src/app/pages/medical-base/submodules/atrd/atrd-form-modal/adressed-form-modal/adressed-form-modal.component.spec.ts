import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdressedFormModalComponent } from './adressed-form-modal.component';

describe('AdressedFormModalComponent', () => {
  let component: AdressedFormModalComponent;
  let fixture: ComponentFixture<AdressedFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdressedFormModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdressedFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
