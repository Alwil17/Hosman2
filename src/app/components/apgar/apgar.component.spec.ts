import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApgarComponent } from './apgar.component';

describe('ApgarComponent', () => {
  let component: ApgarComponent;
  let fixture: ComponentFixture<ApgarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApgarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApgarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
