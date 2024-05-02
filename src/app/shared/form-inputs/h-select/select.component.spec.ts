import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HSelectComponent } from './select.component';

describe('SelectComponent', () => {
  let component: HSelectComponent;
  let fixture: ComponentFixture<HSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HSelectComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
