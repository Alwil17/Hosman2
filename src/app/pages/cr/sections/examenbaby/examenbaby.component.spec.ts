import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExamenbabyComponent } from './examenbaby.component';

describe('ExamenbabyComponent', () => {
  let component: ExamenbabyComponent;
  let fixture: ComponentFixture<ExamenbabyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExamenbabyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExamenbabyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
