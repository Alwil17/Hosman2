import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HInputComponent } from './input.component';

describe('InputComponent', () => {
  let component: HInputComponent;
  let fixture: ComponentFixture<HInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HInputComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
