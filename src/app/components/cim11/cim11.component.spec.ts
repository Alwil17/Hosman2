import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Cim11Component } from './cim11.component';

describe('Cim11Component', () => {
  let component: Cim11Component;
  let fixture: ComponentFixture<Cim11Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Cim11Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Cim11Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
