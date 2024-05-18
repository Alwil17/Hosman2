import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewBedComponent } from './new.component';

describe('NewBedComponent', () => {
  let component: NewBedComponent;
  let fixture: ComponentFixture<NewBedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewBedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewBedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
