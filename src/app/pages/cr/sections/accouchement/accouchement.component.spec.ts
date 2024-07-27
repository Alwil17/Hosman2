import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccouchementComponent } from './accouchement.component';

describe('AccouchementComponent', () => {
  let component: AccouchementComponent;
  let fixture: ComponentFixture<AccouchementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccouchementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccouchementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
