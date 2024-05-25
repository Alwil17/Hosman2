import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequetesJaunesPageComponent } from './requetes-jaunes-page.component';

describe('RequetesJaunesPageComponent', () => {
  let component: RequetesJaunesPageComponent;
  let fixture: ComponentFixture<RequetesJaunesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequetesJaunesPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequetesJaunesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
