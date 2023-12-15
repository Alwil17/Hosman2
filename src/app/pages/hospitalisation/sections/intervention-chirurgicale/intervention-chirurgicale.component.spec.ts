import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventionChirurgicaleComponent } from './intervention-chirurgicale.component';

describe('InterventionChirurgicaleComponent', () => {
  let component: InterventionChirurgicaleComponent;
  let fixture: ComponentFixture<InterventionChirurgicaleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InterventionChirurgicaleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InterventionChirurgicaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
