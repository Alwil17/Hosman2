import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FicheDecedeComponent } from './fiche-decede.component';

describe('FicheDecedeComponent', () => {
  let component: FicheDecedeComponent;
  let fixture: ComponentFixture<FicheDecedeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FicheDecedeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FicheDecedeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
