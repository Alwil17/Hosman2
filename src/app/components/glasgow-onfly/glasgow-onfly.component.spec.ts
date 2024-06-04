import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlasgowOnflyComponent } from './glasgow-onfly.component';

describe('GlasgowOnflyComponent', () => {
  let component: GlasgowOnflyComponent;
  let fixture: ComponentFixture<GlasgowOnflyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlasgowOnflyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GlasgowOnflyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
