import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlasgowComponent } from './glasgow.component';

describe('GlasgowComponent', () => {
  let component: GlasgowComponent;
  let fixture: ComponentFixture<GlasgowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlasgowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GlasgowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
