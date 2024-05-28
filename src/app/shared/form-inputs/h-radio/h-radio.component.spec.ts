import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HRadioComponent } from './h-radio.component';

describe('HRadioComponent', () => {
  let component: HRadioComponent;
  let fixture: ComponentFixture<HRadioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HRadioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HRadioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
