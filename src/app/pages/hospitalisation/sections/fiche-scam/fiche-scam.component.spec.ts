import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FicheScamComponent } from './fiche-scam.component';

describe('FicheScamComponent', () => {
  let component: FicheScamComponent;
  let fixture: ComponentFixture<FicheScamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FicheScamComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FicheScamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
